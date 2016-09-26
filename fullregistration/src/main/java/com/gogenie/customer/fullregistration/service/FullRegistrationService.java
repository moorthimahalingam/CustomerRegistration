package com.gogenie.customer.fullregistration.service;

import java.util.List;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public interface FullRegistrationService {
	
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException;

	public LoginDetails loginCustomer(String emailId, String password) throws CustomerRegistrationException;
	
	public CustomerDetails existingCustomer(String emailId) throws CustomerRegistrationException;
	
	public SecurityQuestions retrieveQuestions(String emailId) throws CustomerRegistrationException;

	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException;

	public CustomerDetails retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException;
	
	public String updatePhoneVerifiedFlag(Integer customerId, String phoneverifiedFlag) throws CustomerRegistrationException;
	
	public boolean resetCustomerCredential(String emailId, String newPassword) throws CustomerRegistrationException;
	
	public String updateCustomerDetails(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public String updateCustomerDefaultAddress(Address address, Integer customerId) throws CustomerRegistrationException;
	
	public CustomerDetails retrieveCustomerDetails(Integer customerId, String email) throws CustomerRegistrationException;
	
	public String addAdditionalAddress(Address address, Integer customerId) throws CustomerRegistrationException;
	
	public String addAdditionalCardInfo(CardInformation cardInfo, Integer customerId) throws CustomerRegistrationException;
	
}
