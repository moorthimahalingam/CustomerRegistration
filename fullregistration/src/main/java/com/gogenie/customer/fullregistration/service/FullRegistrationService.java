package com.gogenie.customer.fullregistration.service;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public interface FullRegistrationService {
	
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException;

	public RegistrationResponse loginCustomer(String emailId, String password) throws CustomerRegistrationException;
	
	public CustomerDetails existingCustomer(String emailId) throws CustomerRegistrationException;
	
	public SecurityQuestions retrieveQuestions(String emailId) throws CustomerRegistrationException;

	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException;

	public RegistrationResponse retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException;
	
	public String updatePhoneVerifiedFlag(Integer customerId, String phoneverifiedFlag) throws CustomerRegistrationException;
	
	public boolean resetCustomerCredential(String emailId, String newPassword) throws CustomerRegistrationException;
	
	public String updateCustomerDetails(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public String updateCustomerDefaultAddress(Address address, Integer customerId) throws CustomerRegistrationException;
	
	public CustomerDetails retrieveCustomerDetails(Integer customerId, String email) throws CustomerRegistrationException;
	
}
