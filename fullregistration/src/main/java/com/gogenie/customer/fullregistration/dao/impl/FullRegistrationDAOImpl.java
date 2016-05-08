package com.gogenie.customer.fullregistration.dao.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

@Repository
public class FullRegistrationDAOImpl implements FullRegistrationDAO {

	@Resource
	private DataSource registrationDataSource;
	
	private JdbcTemplate JdbcTemplate;
	
	@PostConstruct 
	private void setJdbcTemplate() {
		this.JdbcTemplate = new JdbcTemplate(registrationDataSource);
	}
	
	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)  throws CustomerRegistrationException {
		RegistrationResponse response = new RegistrationResponse();
		try {
			SimpleJdbcCall registerACustomer = new SimpleJdbcCall(JdbcTemplate);
			response.setRegistrationSuccess(true);
			
		} catch (Exception e) {
			throw new CustomerRegistrationException(e);
		}
		return response;
	}

	@Override
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SecurityQuestions retrieveSecurityQuestion(String emailId) throws CustomerRegistrationException {
		
//		@TODO - Question codes and answers will be retrieved from Database 
//		Corresponding questions will be retrieved from mapping class or from mapping table 
//		Both will be send to client side for customer verification..
		String questionCode1 = "11100";
		String questionCode2 = "11101";
		
		String question1 = "Whats your favorite color";
		String answer1 = "Red" ;

		String question2 = "Whats your favorite sport";
		String answer2 = "Cricket" ;
		
		SecurityQuestions securityQuestions = new SecurityQuestions();
		securityQuestions.setQuestion1(question1);
		securityQuestions.setAnswer1(answer1);
		
		securityQuestions.setQuestion2(question2);
		securityQuestions.setAnswer2(answer2);
		
		return securityQuestions;
	}

	@Override
	public boolean resetPassword(String emailId, String newPassword) throws CustomerRegistrationException  {
		throw new CustomerRegistrationException ("User is already exist");
		// TODO Auto-generated method stub
//		return false;
	}

	@Override
	public boolean loginCustomer(String emailId, String password) throws CustomerRegistrationException {
		return true;
	}

}
