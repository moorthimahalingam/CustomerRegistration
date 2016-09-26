package com.gogenie.customer.fullregistration.dao;

import java.util.List;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public interface FullRegistrationDAO {
	
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public LoginDetails loginCustomer(String emailId, String password) throws CustomerRegistrationException;
	
	public CustomerDetails existingCustomer(String emailId) throws CustomerRegistrationException;
	
	public SecurityQuestions retrieveSecurityQuestion(String emailId) throws CustomerRegistrationException;
	
	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException;
	
	public CustomerDetails retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException;
	
	public String updatePhoneVerifiedFlag(Integer customerId, String phoneverifiedFlag) throws CustomerRegistrationException;
	
	public boolean resetPassword(String emailId, String newPassword) throws CustomerRegistrationException ;
	
	public String updateCustomerDetails(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public CustomerDetails retrieveCustomerDetails(Integer customerId, String email) throws CustomerRegistrationException;
	
}
