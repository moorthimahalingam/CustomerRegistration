package com.gogenie.customer.fullregistration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.customer.fullregistration.util.CustomerDetailsExtractor;
import com.gogenie.customer.fullregistration.util.CustomerRegistrationConstants;
import com.gogenie.util.constants.CustomerConstants;
import com.gogenie.util.exceptiom.GoGenieUtilityServiceException;
import com.gogenie.util.service.EncryptionService;
import com.gogenie.util.service.impl.EncryptionServiceImpl;

@Repository
public class FullRegistrationDAOImpl implements FullRegistrationDAO {

	Logger logger = LoggerFactory.getLogger(FullRegistrationDAOImpl.class);

	@Resource
	private DataSource gogenieDataSource;

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@PostConstruct
	public void initialize() {
		this.jdbcTemplate = new JdbcTemplate(gogenieDataSource);
		this.simpleJdbcCall = new SimpleJdbcCall(gogenieDataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(gogenieDataSource);
	}

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {

		logger.debug("Entering into registerCustomer()");
		RegistrationResponse response = null;
		try {
			String emailId = registrationRequest.getEmail();
			logger.debug("Email id passed by the customer is {}", emailId);
			if (emailId != null) {
				logger.debug("validate the customer's email id is exist before proceed to insert");
				CustomerDetails customerDetails = existingCustomer(emailId);
				if (customerDetails != null) {
					response = new RegistrationResponse();
					logger.debug("Email id is already registered");
					response.setResponseText(CustomerRegistrationConstants.CUSTOMER_EXIST);
					response.setRegistrationSuccess(false);
					response.setCustomerExist(true);
					customerDetails.setPassword(null);
					response.setCustomerDetails(customerDetails);
					return response;
				}
			}
			response = registerCustomerBasicInfo(registrationRequest);
		} catch (Exception e) {
			logger.error("Exception has occcurred in register customer method");
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0001,
					CustomerRegistrationConstants.CUST_REGISTN_0001_DESC);
		}
		logger.debug("Exiting from registerCustomer()");
		return response;
	}

	private RegistrationResponse registerCustomerBasicInfo(RegistrationRequest registrationRequest) throws Exception {
		RegistrationResponse response = new RegistrationResponse();
		String password = registrationRequest.getPassword();
		EncryptionService encryption = new EncryptionServiceImpl();
		logger.debug("Hashed service execution for password");
		String encryptedPassword = encryption.hashedValue(password);
		registrationRequest.setEncryptedPassword(encryptedPassword);
		logger.debug("password hashed successfully ");
		encryption = null;
		simpleJdbcCall.withProcedureName("post_customer_detail").withoutProcedureColumnMetaDataAccess()
				.declareParameters(new SqlParameter(CustomerConstants.FIRSTNAME, Types.VARCHAR),
						new SqlParameter(CustomerConstants.LASTNAME, Types.VARCHAR),
						new SqlParameter(CustomerConstants.DATEOFBIRTH, Types.DATE),
						new SqlParameter(CustomerConstants.EMAIL, Types.VARCHAR),
						new SqlParameter(CustomerConstants.PASSWORD, Types.VARCHAR),
						new SqlParameter(CustomerConstants.WORKPHONE, Types.VARCHAR),
						new SqlParameter(CustomerConstants.MOBILEPHONE, Types.VARCHAR),
						new SqlParameter(CustomerConstants.PHONE_ISVALID, Types.VARCHAR),
						new SqlParameter(CustomerConstants.CUST_ISACTIVE, Types.VARCHAR),
						new SqlParameter(CustomerConstants.CREATEDDATE, Types.DATE),
						new SqlParameter(CustomerConstants.CREATEDBY, Types.VARCHAR),
						new SqlParameter(CustomerConstants.SECURITY_QUESTION1, Types.VARCHAR),
						new SqlParameter(CustomerConstants.SECURITY_ANSWER1, Types.VARCHAR),
						new SqlParameter(CustomerConstants.SECURITY_QUESTION2, Types.VARCHAR),
						new SqlParameter(CustomerConstants.SECURITY_ANSWER2, Types.VARCHAR),
						new SqlOutParameter("estatus", Types.VARCHAR), new SqlOutParameter("sstatus", Types.VARCHAR));

		Map<String, Object> resultSet = simpleJdbcCall.execute(customerDataMap(registrationRequest));

		logger.debug("ResultSet is {} customer registration ", resultSet.toString());

		if (resultSet.get("estatus") != null) {
			errorMessageHandler((String) resultSet.get("estatus"));
		}
		Integer customerId = (Integer) resultSet.get("sstatus");
		logger.debug("Customer basic data inserted successfully {} ", customerId);
		response.setRegistrationSuccess(true);
		response.setCustomerExist(false);
		response.setCustomerId(customerId);
		return response;
	}

	@Override
	public CustomerDetails existingCustomer(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into existingCustomer()");
		CustomerDetails customerDetails = null;
		try {
			Integer customerId = jdbcTemplate.query("select cust_id from customer where email=?",
					new Object[] { emailId }, new ResultSetExtractor<Integer>() {
						public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
							Integer customerId = null;
							while (rs.next()) {
								customerId = rs.getInt("cust_id");
							}
							logger.debug("Customer id {} for the email id is {} ", customerId, emailId);
							return customerId;
						}
					});
			if (customerId != null) {
				customerDetails = retrieveCustomerDetails(customerId, emailId);
			}
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0012,
					CustomerRegistrationConstants.CUST_REGISTN_0012_DESC);
		}
		logger.debug("Exiting from existingCustomer()");
		return customerDetails;
	}

	@Override
	public SecurityQuestions retrieveSecurityQuestion(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveSecurityQuestion()");
		String questionCode1 = "11100";
		String questionCode2 = "11101";
		String question1 = "Whats your favorite color";
		String answer1 = "Red";

		String question2 = "Whats your favorite sport";
		String answer2 = "Cricket";

		SecurityQuestions securityQuestions = new SecurityQuestions();
		securityQuestions.setQuestion1(question1);
		securityQuestions.setAnswer1(answer1);

		securityQuestions.setQuestion2(question2);
		securityQuestions.setAnswer2(answer2);
		logger.debug("Exiting from retrieveSecurityQuestion()");
		return securityQuestions;
	}

	@Override
	public boolean resetPassword(String emailId, String newPassword) throws CustomerRegistrationException {
		logger.debug("Entering into resetPassword()");
		boolean resetPasswordflag = false;
		try {
			EncryptionService encryptionService = new EncryptionServiceImpl();
			String encryptedNewPassword = encryptionService.hashedValue(newPassword);
			jdbcTemplate.update("update customer set password=? where email=?",
					new Object[] { encryptedNewPassword, emailId });
			resetPasswordflag = true;
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0011,
					CustomerRegistrationConstants.CUST_REGISTN_0011_DESC);
		}
		logger.debug("Exiting from resetPassword()");
		return resetPasswordflag;
	}

	@Override
	public LoginDetails loginCustomer(String emailId, String password) throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		LoginDetails response = null;
		try {
			Object[] loginDetails = new Object[] { emailId };

			response = jdbcTemplate.queryForObject("select password from customer where email=? ", loginDetails,
					new RowMapper<LoginDetails>() {
						@Override
						public LoginDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
							LoginDetails loginDetails = new LoginDetails();
							String encryptedPassword = rs.getString("password");
							EncryptionService encryption = new EncryptionServiceImpl();
							boolean matched = encryption.validateHashedValue(password, encryptedPassword);
							logger.debug("Password didn't match ");
							if (matched) {
								loginDetails.setLoginStatus("Success");
								CustomerDetails customerDetails = existingCustomer(emailId);
								customerDetails.setPassword(null);
								customerDetails.setCardinformation(null);
								loginDetails.setCustomerDetails(customerDetails);
							} else {
								loginDetails = new LoginDetails();
								loginDetails.setLoginStatus("Invalid userid/password");
							}
							return loginDetails;
						}

					});
		} catch (Exception e) {
			String errorCode = "";
			String errorMessage = "";
			if (e instanceof SQLException) {
				errorCode = CustomerRegistrationConstants.CUST_REGISTN_0002;
				errorMessage = CustomerRegistrationConstants.CUST_REGISTN_0002_DESC;
			} else if (e instanceof GoGenieUtilityServiceException) {
				errorCode = CustomerRegistrationConstants.CUST_REGISTN_0003;
				errorMessage = CustomerRegistrationConstants.CUST_REGISTN_0003_DESC;
			} else if (e instanceof CustomerRegistrationException) {
				errorCode = CustomerRegistrationConstants.CUST_REGISTN_0004;
				errorMessage = CustomerRegistrationConstants.CUST_REGISTN_0004_DESC;
			} else {
				errorCode = CustomerRegistrationConstants.CUST_REGISTN_0005;
				errorMessage = CustomerRegistrationConstants.CUST_REGISTN_0005_DESC;
			}
			throw new CustomerRegistrationException(errorCode, errorMessage);
		}
		logger.debug("Exiting from loginCustomer()");
		return response;
	}

	@Override
	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException {
		logger.debug("Entering into validateSecurityQuestions()");
		String validationText = "Doesn't Match";
		try {
			Object[] validateQuesAndAns = new Object[] { request.getCustomerId(),
					request.getSecurityQuestions().getQuestion1(), request.getSecurityQuestions().getAnswer1(),
					request.getSecurityQuestions().getQuestion2(), request.getSecurityQuestions().getAnswer2() };

			Integer customer_id = jdbcTemplate.queryForObject(
					"select count(*) from customer where cust_id=? "
							+ " and security_question1=? and security_answer1=? and security_question2=? and security_answer1=?",
					validateQuesAndAns, Integer.class);

			if (customer_id != null && customer_id > 0) {
				validationText = "Matched";
			}
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0010,
					CustomerRegistrationConstants.CUST_REGISTN_0010_DESC);
		}
		logger.debug("Exiting from validateSecurityQuestions()");
		return validationText;
	}

	@Override
	public String updatePhoneVerifiedFlag(Integer customerId, String phoneVerifiedFlag)
			throws CustomerRegistrationException {
		logger.debug("Entering into updatePhoneVerifiedFlag()");
		String updateStatus = "Failed";
		try {
			jdbcTemplate.update("update customer set phone_isvalid=? where cust_id=?",
					new Object[] { phoneVerifiedFlag, customerId });
			updateStatus = "Success";
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0009,
					CustomerRegistrationConstants.CUST_REGISTN_0009_DESC);
		}
		logger.debug("Exiting from updatePhoneVerifiedFlag()");
		return updateStatus;
	}

	@Override
	public RegistrationResponse retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrievePhoneVerifiedFlag()");
		RegistrationResponse response = null;
		try {
			response = jdbcTemplate.queryForObject("select * from customer where email=?", new Object[] { emailId },
					new RowMapper<RegistrationResponse>() {

						@Override
						public RegistrationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
							RegistrationResponse dbResult = new RegistrationResponse();
							dbResult.setFirstName(rs.getString("firstname"));
							dbResult.setLastName(rs.getString("lastname"));
							dbResult.setCustomerId(rs.getInt("cust_id"));
							dbResult.setPhoneIsValid(rs.getString("PHONE_ISVALID"));
							return dbResult;
						}

					});
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0008,
					CustomerRegistrationConstants.CUST_REGISTN_0008_DESC);
		}
		logger.debug("Exiting from retrievePhoneVerifiedFlag()");
		return response;
	}

	private Map<String, Object> customerDataMap(RegistrationRequest request) {
		logger.debug("Entering into customerDataMap()");
		Map<String, Object> customer = new HashMap<>();
		customer.put(CustomerConstants.FIRSTNAME, request.getFirstname());
		customer.put(CustomerConstants.LASTNAME, request.getLastname());
		customer.put(CustomerConstants.DATEOFBIRTH, request.getDateofbirth());
		customer.put(CustomerConstants.EMAIL, request.getEmail());
		customer.put(CustomerConstants.PASSWORD, request.getEncryptedPassword());
		customer.put(CustomerConstants.WORKPHONE, request.getWorkphone());
		customer.put(CustomerConstants.MOBILEPHONE, request.getMobilephone());
		String phoneIsValidFlag = request.getPhoneValidationFlag();
		if (phoneIsValidFlag != null && phoneIsValidFlag.equals("Y")) {
			customer.put(CustomerConstants.PHONE_ISVALID, "Y");
			customer.put(CustomerConstants.CUST_ISACTIVE, "Y");
		} else {
			customer.put(CustomerConstants.PHONE_ISVALID, "N");
			customer.put(CustomerConstants.CUST_ISACTIVE, "N");
		}
		customer.put(CustomerConstants.CREATEDDATE, new java.sql.Date(new Date().getTime()));
		customer.put(CustomerConstants.CREATEDBY, "gogenie");
		if (request.getSecurityQuestions() != null) {
			customer.put(CustomerConstants.SECURITY_QUESTION1, request.getSecurityQuestions().getQuestion1());
			customer.put(CustomerConstants.SECURITY_ANSWER1, request.getSecurityQuestions().getAnswer1());
			customer.put(CustomerConstants.SECURITY_QUESTION2, request.getSecurityQuestions().getQuestion2());
			customer.put(CustomerConstants.SECURITY_ANSWER2, request.getSecurityQuestions().getAnswer2());
		}
		logger.debug("Input parameter values are {} ", customer.toString());
		logger.debug("Exiting from customerDataMap()");
		return customer;
	}

	@Override
	public String updateCustomerDetails(RegistrationRequest registrationRequest) throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDetails()");
		String response = null;
		try {
			// simpleJdbcCall = null;
			// simpleJdbcCall = new SimpleJdbcCall(gogenieDataSource);
			simpleJdbcCall.withProcedureName("put_customer_details").withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("cu_id", Types.INTEGER),
							new SqlParameter("first_name", Types.VARCHAR), new SqlParameter("last_name", Types.VARCHAR),
							new SqlParameter("dob", Types.DATE), new SqlParameter("e_mail", Types.VARCHAR),
							new SqlParameter("pswd", Types.VARCHAR), new SqlParameter("wrkphone", Types.VARCHAR),
							new SqlParameter("mobphone", Types.VARCHAR), new SqlParameter("ph_isvalid", Types.VARCHAR),
							new SqlParameter("cu_isactive", Types.VARCHAR), new SqlParameter("up_date", Types.DATE),
							new SqlParameter("up_by", Types.VARCHAR),
							new SqlParameter("security_quest1", Types.VARCHAR),
							new SqlParameter("security_ans1", Types.VARCHAR),
							new SqlParameter("security_quest2", Types.VARCHAR),
							new SqlParameter("security_ans2", Types.VARCHAR), new SqlOutParameter("result", Types.BIT),
							new SqlOutParameter("full_error", Types.VARCHAR));

			String password = registrationRequest.getPassword();
			if (password != null) {
				EncryptionService encryption = new EncryptionServiceImpl();
				logger.debug("Hashed service execution for password");
				String encryptedPassword = encryption.hashedValue(password);
				registrationRequest.setEncryptedPassword(encryptedPassword);
				logger.debug("password hashed successfully ");
			}
			Map<String, Object> resultSet = simpleJdbcCall.execute(customerUpdatedDataMap(registrationRequest));
			logger.debug("ResultSet is {} customer registration ", resultSet.toString());
			response = resultSet.toString();
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0006,
					CustomerRegistrationConstants.CUST_REGISTN_0006_DESC);
		}
		logger.debug("Exiting from updateCustomerDetails()");
		return response;
	}

	private Map<String, Object> customerUpdatedDataMap(RegistrationRequest request) {
		logger.debug("Entering into customerUpdatedDataMap()");
		Map<String, Object> customer = new HashMap<>();
		customer.put("cu_id", request.getCustomerId());
		customer.put("first_name", request.getFirstname());
		customer.put("last_name", request.getLastname());
		customer.put("dob", request.getDateofbirth());
		customer.put("e_mail", request.getEmail());
		customer.put("pswd", request.getEncryptedPassword());
		customer.put("wrkphone", request.getWorkphone());
		customer.put("mobphone", request.getMobilephone());
		String phoneIsValidFlag = request.getPhoneValidationFlag();
		if (phoneIsValidFlag != null && phoneIsValidFlag.equals("Y")) {
			customer.put("ph_isvalid", "Y");
			customer.put("cu_isactive", "Y");
		} else {
			customer.put("ph_isvalid", "N");
			customer.put("cu_isactive", "N");
		}
		customer.put("up_date", new java.sql.Date(new Date().getTime()));
		customer.put("up_by", "Customer");
		if (request.getSecurityQuestions() != null) {
			customer.put("security_quest1", request.getSecurityQuestions().getQuestion1());
			customer.put("security_ans1", request.getSecurityQuestions().getAnswer1());
			customer.put("security_quest2", request.getSecurityQuestions().getQuestion2());
			customer.put("security_ans2", request.getSecurityQuestions().getAnswer2());
		}
		logger.debug("Input parameter values are {} ", customer.toString());
		logger.debug("Exiting from customerUpdatedDataMap()");
		return customer;
	}

	@Override
	public CustomerDetails retrieveCustomerDetails(Integer customerId, String email)
			throws CustomerRegistrationException {
		logger.debug("Entering into retrieveCustomerDetails()");
		CustomerDetails customerDetails = null;
		try {
			SqlParameterSource inputParam = new MapSqlParameterSource().addValue(CustomerConstants.CUST_ID, customerId)
					.addValue(CustomerConstants.EMAIL, email);
			customerDetails = (CustomerDetails) namedParameterJdbcTemplate
					.query("{call get_customer(:cust_id, :email)}", inputParam, new CustomerDetailsExtractor());
		} catch (Exception e) {
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0007,
					CustomerRegistrationConstants.CUST_REGISTN_0007_DESC);
		}
		logger.debug("Exiting from retrieveCustomerDetails()");
		return customerDetails;
	}

	/**
	 * 
	 * @param errorMessage
	 * @return
	 */
	private void errorMessageHandler(String errorMessage) throws CustomerRegistrationException {
		logger.debug("Entering into errorMessageHandler()");
		logger.debug("Error message  from DB {} ", errorMessage);
		String errorMsg[] = errorMessage.split(":");
		CustomerRegistrationException cre = new CustomerRegistrationException(errorMsg[0], errorMsg[1]);
		logger.debug("Exiting from errorMessageHandler()");
		throw cre;
	}
}
