package com.gogenie.customer.fullregistration.service;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public interface FullRegistrationService {
	
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException;
	
	public SecurityQuestions retrieveQuestions(String emailId) throws CustomerRegistrationException;
	
	public boolean resetCustomerCredential(String emailId, String newPassword) throws CustomerRegistrationException;
	
}
