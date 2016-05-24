package com.gogenie.customer.fullregistration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
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

	@Resource
	private DataSource registrationDataSource;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void setJdbcTemplate() {
		this.jdbcTemplate = new JdbcTemplate(registrationDataSource);
	}

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {

		RegistrationResponse response = new RegistrationResponse();

		try {
			String password = registrationRequest.getPassword();
			EncryptionService encryption = new EncryptionServiceImpl();
			String encryptedPassword = encryption.encryptedValue(password);
			registrationRequest.setEncryptedPassword(encryptedPassword);
			CardInformation cardInformation = registrationRequest.getCardInformation();
			if (cardInformation != null) {
				if (cardInformation.getCreditcardnumber() != null) {
					String encryptedCardInformation = encryption.encryptedValue(cardInformation.getCreditcardnumber());
					cardInformation.setEncryptedCreditcardumber(encryptedCardInformation);
				}

				if (cardInformation.getExpirydate() != null) {
					String encryptedExpiryDate = encryption.encryptedValue(cardInformation.getExpirydate());
					cardInformation.setEncryptedExpirydate(encryptedExpiryDate);
				}
			}
			encryption = null;
			SimpleJdbcCall insertCustomerDetails = new SimpleJdbcCall(registrationDataSource);
			insertCustomerDetails.withProcedureName("post_customer_detail");
			Map<String, Object> resultSet = insertCustomerDetails.execute(customerDataMap(registrationRequest));
			Integer customerId = (Integer) resultSet.get("cust_id");
			// SimpleJdbcInsert customerDetailJdbcInsert = new
			// SimpleJdbcInsert(registrationDataSource);
			// customerDetailJdbcInsert.withTableName("customer").usingGeneratedKeyColumns("cust_id");
			// Number cust_id =
			// customerDetailJdbcInsert.executeAndReturnKey(customerDataMap(registrationRequest));

			insertCustomerDetails.withProcedureName("post_address_details");
			insertCustomerDetails.execute(customerAddresDataMap(registrationRequest.getAddress(), customerId));

			insertCustomerDetails.withProcedureName("post_cust_payment_info");
			insertCustomerDetails
					.execute(customerCardInformationDataMap(registrationRequest.getCardInformation(), customerId));

			response.setRegistrationSuccess(true);

		} catch (Exception e) {
			response.setRegistrationSuccess(false);
			throw new CustomerRegistrationException(e, "registerCustomer");
		}
		return response;
	}

	@Override
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException {
		// SimpleJdbcCall existingCustomerValidation = new
		// SimpleJdbcCall(jdbcTemplate);
		boolean isCustomerExist = false;
		try {
			Integer customer_id = jdbcTemplate.queryForObject("select cust_id from customer where email=?",
					new Object[] { emailId }, Integer.class);
			if (customer_id != null) {
				isCustomerExist = true;
			}
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "existingCustomer");
		}
		return isCustomerExist;
	}

	@Override
	public SecurityQuestions retrieveSecurityQuestion(String emailId) throws CustomerRegistrationException {

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

		return securityQuestions;
	}

	private Map<String, String> customerCardInformationDataMap(CardInformation cardInformation, Integer cust_id) {
		Map<String, String> cardDetails = new HashMap<>();
		return cardDetails;
	}

	@Override
	public boolean resetPassword(String emailId, String newPassword) throws CustomerRegistrationException {
		boolean resetPasswordflag = false;
		try {
			EncryptionService encryptionService = new EncryptionServiceImpl();
			String encryptedNewPassword = encryptionService.encryptedValue(newPassword);
			jdbcTemplate.update("update customer set password=? where email=?",
					new Object[] { encryptedNewPassword, emailId });
			resetPasswordflag = true;
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "updatePhoneVerifiedFlag");
		}
		return resetPasswordflag;
	}

	@Override
	public RegistrationResponse loginCustomer(String emailId, String password) throws CustomerRegistrationException {

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
								boolean matched = encryption.validateEncryptedValue(password, encryptedPassword);
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
			throw new CustomerRegistrationException(e, "validateSecurityQuestions");
		}
		return response;
	}

	@Override
	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException {

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
			throw new CustomerRegistrationException(e, "validateSecurityQuestions");
		}

		return validationText;
	}

	@Override
	public String updatePhoneVerifiedFlag(String customerId, String phoneVerifiedFlag)
			throws CustomerRegistrationException {
		String updateStatus = "Failed";
		try {
			jdbcTemplate.update("update customer set phone_isvalid=? where cust_id=?",
					new Object[] { phoneVerifiedFlag, customerId });
			updateStatus = "Success";
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "updatePhoneVerifiedFlag");
		}
		return updateStatus;
	}

	@Override
	public RegistrationResponse retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException {
		RegistrationResponse response = null;
		try {
			response = jdbcTemplate.queryForObject("select * from customer where email=?", new Object[] { emailId },
					new RowMapper<RegistrationResponse>() {

						@Override
						public RegistrationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
							RegistrationResponse dbResult = new RegistrationResponse();
							dbResult.setFirstName(rs.getString("first_name"));
							dbResult.setLastName(rs.getString("last_name"));
							dbResult.setCustomerId(rs.getString("cust_id"));
							return dbResult;
						}

					});
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "updatePhoneVerifiedFlag");
		}
		return response;
	}

	private Map<String, Object> customerDataMap(RegistrationRequest request) {
		Map<String, Object> customer = new HashMap<>();
		customer.put("first_name", request.getFirstname());
		customer.put("last_name", request.getLastname());
		customer.put("dob", request.getDateofbirth());
		customer.put("email", request.getEmail());
		customer.put("password", request.getEncryptedPassword());
		customer.put("workphone", request.getWorkphone());
		customer.put("mobilephone", request.getMobilephone());
		String phoneIsValidFlag = request.getPhoneValidationFlag();
		customer.put("phone_isvalid", phoneIsValidFlag);

		if (phoneIsValidFlag != null && phoneIsValidFlag.equals("Y")) {
			customer.put("cust_isactive", "Y");
		} else {
			customer.put("cust_isactive", "N");
		}
		customer.put("createddate", new Date());
		customer.put("createdby", new Integer("12312312"));
		if (request.getSecurityQuestions() != null) {
			customer.put("security_question1", request.getSecurityQuestions().getQuestion1());
			customer.put("security_answer1", request.getSecurityQuestions().getAnswer1());
			customer.put("security_question2", request.getSecurityQuestions().getQuestion2());
			customer.put("security_answer2", request.getSecurityQuestions().getAnswer2());
		}

		return customer;
	}

	private Map<String, Object> customerAddresDataMap(Address address, Integer cust_id) {
		Map<String, Object> addressDetails = new HashMap<>();
		addressDetails.put("cust_id", cust_id);
		addressDetails.put("country_id", address.getCountry());
		addressDetails.put("state_id", address.getState());
		addressDetails.put("city_id", address.getCity());
		addressDetails.put("address1", address.getAddressline1());
		addressDetails.put("address2", address.getAddressline2());
		addressDetails.put("createddate", new Date());
		addressDetails.put("createdby", new Integer("12312312"));
		addressDetails.put("zipcode", address.getPostalcode());
		addressDetails.put("isdefault_address", "Y");
		return addressDetails;
	}

}
