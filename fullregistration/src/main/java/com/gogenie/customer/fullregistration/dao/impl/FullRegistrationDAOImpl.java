package com.gogenie.customer.fullregistration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	@PostConstruct
	public void initialize() {
		this.jdbcTemplate = new JdbcTemplate(gogenieDataSource);
		simpleJdbcCall = new SimpleJdbcCall(gogenieDataSource);
	}

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {
		logger.debug("Entering into registerCustomer()");
		RegistrationResponse response = new RegistrationResponse();
		try {
			String password = registrationRequest.getPassword();
			EncryptionService encryption = new EncryptionServiceImpl();
			logger.debug("Hashed service execution for password");
			String encryptedPassword = encryption.hashedValue(password);
			registrationRequest.setEncryptedPassword(encryptedPassword);
			logger.debug("password hashed successfully ");
			CardInformation cardInformation = registrationRequest.getCardInformation();
			
			if (cardInformation != null) {
				logger.debug("Hashed service execution for Card Information ");
				if (cardInformation.getCreditcardnumber() != null) {
					String encryptedCardInformation = encryption.hashedValue(cardInformation.getCreditcardnumber());
					cardInformation.setEncryptedCreditcardumber(encryptedCardInformation);
				}

				if (cardInformation.getExpirydate() != null) {
					String encryptedExpiryDate = encryption.hashedValue(cardInformation.getExpirydate());
					cardInformation.setEncryptedExpirydate(encryptedExpiryDate);
				}
				logger.debug("CardInformation hashed successfully ");
			}
			encryption = null;
			simpleJdbcCall.withProcedureName("post_customer_detail");
			Map<String, Object> resultSet = simpleJdbcCall.execute(customerDataMap(registrationRequest));
			Integer customerId = (Integer) resultSet.get("cust_id");
			logger.debug("Customer table data has been executed successfully {} ", customerId);
			if (registrationRequest.getAddress() != null) {
				simpleJdbcCall.withProcedureName("post_address_details");
				simpleJdbcCall.execute(customerAddresDataMap(registrationRequest.getAddress(), customerId));
				logger.debug("Address table data has been executed successfully {} ", customerId);
			}
			
			if (registrationRequest.getCardInformation() != null) {
				simpleJdbcCall.withProcedureName("post_cust_payment_info");
				simpleJdbcCall
						.execute(customerCardInformationDataMap(registrationRequest.getCardInformation(), customerId));
				logger.debug("Card information	 table data has been executed successfully {} ", customerId);
			}
			response.setRegistrationSuccess(true);

		} catch (Exception e) {
			response.setRegistrationSuccess(false);
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
					new Object[] { emailId }, new ResultSetExtractor<Integer>(){
						@Override
						public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
							Integer customerId = null;
							while (rs.next()) {
								customerId= rs.getInt("cust_id");
							}
							logger.debug("Customer id {} for the email id is {} ", customerId, emailId);
							return customerId;
						}});
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
									dbResult.setCustomerId(rs.getString("cust_id"));
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
	public String updatePhoneVerifiedFlag(String customerId, String phoneVerifiedFlag)
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
							dbResult.setCustomerId(rs.getString("cust_id"));
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
		customer.put("FIRSTNAME", request.getFirstname());
		customer.put("LASTNAME", request.getLastname());
		customer.put("DATEOFBIRTH", request.getDateofbirth());
		customer.put("EMAIL", request.getEmail());
		customer.put("PASSWORD", request.getEncryptedPassword());
		customer.put("WORKPHONE", request.getWorkphone());
		customer.put("MOBILEPHONE", request.getMobilephone());
		String phoneIsValidFlag = request.getPhoneValidationFlag();
		customer.put("PHONE_ISVALID", phoneIsValidFlag);

		if (phoneIsValidFlag != null && phoneIsValidFlag.equals("Y")) {
			customer.put("CUST_ISACTIVE", "Y");
		} else {
			customer.put("CUST_ISACTIVE", "N");
		}
		customer.put("CREATEDDATE", new Date());
		customer.put("CREATEDBY", new Integer("12312312"));
		if (request.getSecurityQuestions() != null) {
			customer.put("SECURITY_QUESTION1", request.getSecurityQuestions().getQuestion1());
			customer.put("SECURITY_ANSWER1", request.getSecurityQuestions().getAnswer1());
			customer.put("SECURITY_QUESTION2", request.getSecurityQuestions().getQuestion2());
			customer.put("SECURITY_ANSWER2", request.getSecurityQuestions().getAnswer2());
		}
		logger.debug("Exiting from customerDataMap()");
		return customer;
	}

	private Map<String, Object> customerAddresDataMap(Address address, Integer custId) {
		logger.debug("Entering into customerAddresDataMap()");
		Map<String, Object> addressDetails = new HashMap<>();
		addressDetails.put("cust_id", custId);
		addressDetails.put("country_id", address.getCountry());
		addressDetails.put("state_id", address.getState());
		addressDetails.put("city_id", address.getCity());
		addressDetails.put("address1", address.getAddressline1());
		addressDetails.put("address2", address.getAddressline2());
		addressDetails.put("createddate", new Date());
		addressDetails.put("createdby", new Integer("12312312"));
		addressDetails.put("zipcode", address.getPostalcode());
		addressDetails.put("isdefault_address", "Y");
		logger.debug("Exiting from customerAddresDataMap()");
		return addressDetails;
	}

	private Map<String, Object> customerCardInformationDataMap(CardInformation cardInformation, Integer custId) {
		logger.debug("Entering into customerCardInformationDataMap()");
		Map<String, Object> cardDetails = new HashMap<>();
		cardDetails.put("cust_id", custId);
		cardDetails.put("", custId);
		cardDetails.put("paymenttype", custId);
		cardDetails.put("cardnumber", custId);
		cardDetails.put("expirydate", custId);
		cardDetails.put("cvv_number", custId);
		cardDetails.put("name_on_card", custId);
		cardDetails.put("createddate", new Date());
		cardDetails.put("createdby", new Integer("12312312"));
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
