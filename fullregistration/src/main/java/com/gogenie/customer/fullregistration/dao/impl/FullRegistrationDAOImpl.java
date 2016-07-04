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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
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
		simpleJdbcCall = new SimpleJdbcCall(gogenieDataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(gogenieDataSource);
	}

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {
		logger.debug("Entering into registerCustomer()");
		RegistrationResponse response = new RegistrationResponse();
		Long customerId = null;
		try {
			String password = registrationRequest.getPassword();
			EncryptionService encryption = new EncryptionServiceImpl();
			logger.debug("Hashed service execution for password");
			String encryptedPassword = encryption.hashedValue(password);
			registrationRequest.setEncryptedPassword(encryptedPassword);
			logger.debug("password hashed successfully ");

			encryption = null;
			simpleJdbcCall.withProcedureName("post_customer_detail").withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("first_name", Types.VARCHAR),
							new SqlParameter("last_name", Types.VARCHAR), new SqlParameter("dob", Types.DATE),
							new SqlParameter("email", Types.VARCHAR), new SqlParameter("password", Types.VARCHAR),
							new SqlParameter("workphone", Types.VARCHAR),
							new SqlParameter("mobilephone", Types.VARCHAR),
							new SqlParameter("phone_isvalid", Types.BIT), new SqlParameter("cust_isactive", Types.BIT),
							new SqlParameter("createddate", Types.DATE), new SqlParameter("createdby", Types.BIGINT),
							new SqlParameter("security_question1", Types.VARCHAR),
							new SqlParameter("security_answer1", Types.VARCHAR),
							new SqlParameter("security_question2", Types.VARCHAR),
							new SqlParameter("security_answer2", Types.VARCHAR),
							new SqlOutParameter("error_status", Types.VARCHAR),
							new SqlOutParameter("returnCustId", Types.BIGINT));

			Map<String, Object> resultSet = simpleJdbcCall.execute(customerDataMap(registrationRequest));

			logger.debug("ResultSet is {} customer registration ", resultSet.toString());

			customerId = (Long) resultSet.get("returnCustId");

			logger.debug("Customer table data has been executed successfully {} ", customerId);

			if (registrationRequest.getAddress() != null) {
				simpleJdbcCall.withProcedureName("post_address_details").withoutProcedureColumnMetaDataAccess()
						.declareParameters(new SqlParameter("cust_id", Types.BIGINT),
								new SqlParameter("country_id", Types.BIGINT),
								new SqlParameter("state_id", Types.BIGINT), new SqlParameter("city_id", Types.BIGINT),
								new SqlParameter("address1", Types.VARCHAR),
								new SqlParameter("address2", Types.VARCHAR),
								new SqlParameter("createdby", Types.INTEGER),
								new SqlParameter("createddate", Types.DATE), new SqlParameter("zipcode", Types.VARCHAR),
								new SqlParameter("isdefault_address", Types.BIT),
								new SqlOutParameter("error_status", Types.VARCHAR));

				Map<String, Object> addressResult = simpleJdbcCall
						.execute(customerAddresDataMap(registrationRequest.getAddress(), customerId));
				logger.debug("Address details insert {} result ", addressResult.toString());
				logger.debug("Address table data has been executed successfully {} ", customerId);
			}

			if (registrationRequest.getCardInformation() != null) {
				simpleJdbcCall.withProcedureName("post_cust_payment_info").withoutProcedureColumnMetaDataAccess()
						.declareParameters(new SqlParameter("cust_id", Types.BIGINT),
								new SqlParameter("payementtype", Types.VARCHAR),
								new SqlParameter("cardnumber", Types.VARCHAR),
								new SqlParameter("expirydate", Types.VARCHAR),
								new SqlParameter("cvv_number", Types.VARCHAR),
								new SqlParameter("name_on_card", Types.VARCHAR),
								new SqlParameter("createdby", Types.BIGINT),
								new SqlParameter("createddate", Types.DATE),
								new SqlParameter("address1", Types.VARCHAR),
								new SqlParameter("address2", Types.VARCHAR), new SqlParameter("city_id", Types.BIGINT),
								new SqlParameter("state_id", Types.BIGINT),
								new SqlParameter("country_id", Types.BIGINT),
								new SqlParameter("zipcode", Types.VARCHAR),
								new SqlOutParameter("error_status", Types.VARCHAR));
				Map<String, Object> cardInsertResult = simpleJdbcCall
						.execute(customerCardInformationDataMap(registrationRequest.getCardInformation(), customerId));
				logger.debug("Card Insert Resultset is {}", cardInsertResult.toString());

				logger.debug("Card information table data has been executed successfully {} ", customerId);
			}
			response.setRegistrationSuccess(true);

		} catch (Exception e) {
			response.setRegistrationSuccess(false);
			if (customerId != null && customerId.longValue() > 0) {
				logger.debug("Rollback the current transaction ");
			}
			throw new CustomerRegistrationException(e, "111111");
		}
		logger.debug("Exiting from registerCustomer()");
		return response;
	}

	@Override
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into existingCustomer()");
		boolean isCustomerExist = false;
		try {
			Integer customer_id = jdbcTemplate.query("select cust_id from customer where email=?",
					new Object[] { emailId }, new ResultSetExtractor<Integer>() {
						@Override
						public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
							Integer customerId = null;
							while (rs.next()) {
								customerId = rs.getInt("cust_id");
							}
							logger.debug("Customer id {} for the email id is {} ", customerId, emailId);
							return customerId;
						}
					});
			if (customer_id != null) {
				isCustomerExist = true;
			}
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "111112");
		}
		logger.debug("Exiting from existingCustomer()");
		return isCustomerExist;
	}

	@Override
	public SecurityQuestions retrieveSecurityQuestion(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveSecurityQuestion()");
		// @TODO - Question codes and answers will be retrieved from Database
		// Corresponding questions will be retrieved from mapping class or from
		// mapping table
		// Both will be send to client side for customer verification..
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
			throw new CustomerRegistrationException(e, "111113");
		}
		logger.debug("Exiting from resetPassword()");
		return resetPasswordflag;
	}

	@Override
	public RegistrationResponse loginCustomer(String emailId, String password) throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		RegistrationResponse response = null;
		try {
			Object[] loginDetails = new Object[] { emailId };

			response = jdbcTemplate.queryForObject(
					"select cust_id, first_name, last_name,password from customer where email_id=? ", loginDetails,
					new RowMapper<RegistrationResponse>() {
						@Override
						public RegistrationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
							RegistrationResponse dbResult = null;
							String encryptedPassword = rs.getString("password");
							EncryptionService encryption = new EncryptionServiceImpl();
							try {
								boolean matched = encryption.validateHashedValue(password, encryptedPassword);
								if (matched) {
									dbResult = new RegistrationResponse();
									dbResult.setFirstName(rs.getString("first_name"));
									dbResult.setLastName(rs.getString("last_name"));
									dbResult.setCustomerId(rs.getLong("cust_id"));
								}
							} catch (GoGenieUtilityServiceException e) {
								dbResult = null;
								e.printStackTrace();
							}
							return dbResult;
						}

					});
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "111114");
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
			throw new CustomerRegistrationException(e, "111115");
		}
		logger.debug("Exiting from validateSecurityQuestions()");
		return validationText;
	}

	@Override
	public String updatePhoneVerifiedFlag(Long customerId, String phoneVerifiedFlag)
			throws CustomerRegistrationException {
		logger.debug("Entering into updatePhoneVerifiedFlag()");
		String updateStatus = "Failed";
		try {
			jdbcTemplate.update("update customer set phone_isvalid=? where cust_id=?",
					new Object[] { phoneVerifiedFlag, customerId });
			updateStatus = "Success";
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "111116");
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
							dbResult.setCustomerId(rs.getLong("cust_id"));
							return dbResult;
						}

					});
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "111117");
		}
		logger.debug("Exiting from retrievePhoneVerifiedFlag()");
		return response;
	}

	private Map<String, Object> customerDataMap(RegistrationRequest request) {
		logger.debug("Entering into customerDataMap()");
		Map<String, Object> customer = new HashMap<>();
		customer.put("first_name", request.getFirstname());
		customer.put("last_name", request.getLastname());
		customer.put("dob", request.getDateofbirth());
		customer.put("email", request.getEmail());
		customer.put("password", request.getEncryptedPassword());
		customer.put("workphone", request.getWorkphone());
		customer.put("mobilephone", request.getMobilephone());
		String phoneIsValidFlag = request.getPhoneValidationFlag();
		if (phoneIsValidFlag != null && phoneIsValidFlag.equals("Y")) {
			customer.put("phone_isvalid", 1);
			customer.put("cust_isactive", 1);
		} else {
			customer.put("phone_isvalid", 0);
			customer.put("cust_isactive", 0);
		}
		customer.put("createddate", new java.sql.Date(new Date().getTime()));
		customer.put("createdby", 12312312);
		if (request.getSecurityQuestions() != null) {
			customer.put("security_question1", request.getSecurityQuestions().getQuestion1());
			customer.put("security_answer1", request.getSecurityQuestions().getAnswer1());
			customer.put("security_question2", request.getSecurityQuestions().getQuestion2());
			customer.put("security_answer2", request.getSecurityQuestions().getAnswer2());
		}
		logger.debug("Input parameter values are {} ", customer.toString());
		logger.debug("Exiting from customerDataMap()");
		return customer;
	}

	private Map<String, Object> customerAddresDataMap(Address address, Long custId) {
		logger.debug("Entering into customerAddresDataMap()");
		Map<String, Object> addressDetails = new HashMap<>();
		addressDetails.put("cust_id", custId);
		addressDetails.put("country_id", address.getCountry());
		addressDetails.put("state_id", address.getState());
		addressDetails.put("city_id", address.getCity());
		addressDetails.put("address1", address.getAddressline1());
		addressDetails.put("address2", address.getAddressline2());
		addressDetails.put("createddate", new java.sql.Date(new Date().getTime()));
		addressDetails.put("createdby", 12312312);
		addressDetails.put("zipcode", address.getPostalcode());
		addressDetails.put("isdefault_address", "Y");
		logger.debug("Exiting from customerAddresDataMap()");
		return addressDetails;
	}

	private Map<String, Object> customerCardInformationDataMap(CardInformation cardInformation, Long custId) {
		logger.debug("Entering into customerCardInformationDataMap()");
		Map<String, Object> cardDetails = new HashMap<>();
		cardDetails.put("cust_id", custId);
		cardDetails.put("paymenttype", cardInformation.getPaymentType());
		cardDetails.put("cardnumber", cardInformation.getCreditcardnumber());
		cardDetails.put("expirydate", cardInformation.getExpirydate());
		cardDetails.put("cvv_number", cardInformation.getCvvNumber());
		cardDetails.put("name_on_card", cardInformation.getNameOnCard());
		cardDetails.put("createddate", new java.sql.Date(new Date().getTime()));
		cardDetails.put("createdby", 12312312);
		Address billingAddress = cardInformation.getAddress();
		if (billingAddress != null) {
			cardDetails.put("address1", billingAddress.getAddressline1());
			cardDetails.put("address2", billingAddress.getAddressline2());
			cardDetails.put("city_id", billingAddress.getCity());
			cardDetails.put("state_id", billingAddress.getState());
			cardDetails.put("country_id", billingAddress.getCountry());
			cardDetails.put("zipcode", billingAddress.getPostalcode());
			cardDetails.put("isDefault", billingAddress.getDefaultAddressFlag());
		}
		logger.debug("Exiting from customerCardInformationDataMap()");
		return cardDetails;
	}

}
