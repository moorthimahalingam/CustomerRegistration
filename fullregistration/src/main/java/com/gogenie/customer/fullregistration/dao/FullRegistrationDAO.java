package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public interface FullRegistrationDAO {
	
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException;
	
	public SecurityQuestions retrieveSecurityQuestion(String emailId) throws CustomerRegistrationException;
	
	public boolean resetPassword(String emailId, String newPassword) throws CustomerRegistrationException ;
}
