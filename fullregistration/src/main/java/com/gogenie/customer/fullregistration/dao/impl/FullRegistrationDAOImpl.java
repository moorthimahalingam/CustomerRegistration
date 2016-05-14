package com.gogenie.customer.fullregistration.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

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
			SimpleJdbcInsert customerDetailJdbcInsert = new SimpleJdbcInsert(registrationDataSource);
			customerDetailJdbcInsert.withTableName("customer").usingGeneratedKeyColumns("cust_id");
			Number cust_id = customerDetailJdbcInsert.executeAndReturnKey(customerDataMap(registrationRequest));

			customerDetailJdbcInsert = new SimpleJdbcInsert(registrationDataSource).withTableName("address_details");
			customerDetailJdbcInsert
					.execute(customerAddresDataMap(registrationRequest.getAddress(), cust_id.intValue()));

			customerDetailJdbcInsert = new SimpleJdbcInsert(registrationDataSource).withTableName("card_details");

			customerDetailJdbcInsert.execute(
					customerCardInformationDataMap(registrationRequest.getCardInformation(), cust_id.intValue()));

			response.setRegistrationSuccess(true);

		} catch (Exception e) {
			response.setRegistrationSuccess(false);
			throw new CustomerRegistrationException(e);
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
		throw new CustomerRegistrationException("User is already exist");
		// TODO Auto-generated method stub
		// return false;
	}

	@Override
	public RegistrationResponse loginCustomer(String emailId, String password) throws CustomerRegistrationException {

		RegistrationResponse response = null;
		try {
			Object[] loginDetails = new Object[] { emailId, password };

			response = jdbcTemplate.queryForObject(
					"select cust_id, firstname, lastname from customer where email_id=? " + " and password=?",
					loginDetails, RegistrationResponse.class); // validate this
																// call. Dont
																// think so it
																// will work as
																// field name is
																// different
																// from column
																// name

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
							+ " and securityquestion1=? and answer1=? and securityquestion2=? and answer2=?",
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
			jdbcTemplate.update("update customer set phone_isValid=? where cust_id=?",
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
			response = jdbcTemplate.queryForObject("select * from customer where email=?",new Object[]{emailId}, RegistrationResponse.class);
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "updatePhoneVerifiedFlag");
		}
		return response;
	}

	private Map<String, String> customerDataMap(RegistrationRequest request) {
		Map<String, String> customer = new HashMap<>();

		return customer;
	}

	private Map<String, String> customerAddresDataMap(Address address, Integer cust_id) {
		Map<String, String> addressDetails = new HashMap<>();

		return addressDetails;
	}

}
