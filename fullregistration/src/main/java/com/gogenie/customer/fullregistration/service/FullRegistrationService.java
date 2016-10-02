package com.gogenie.customer.fullregistration.service;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.DefaultAddressFlag;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public interface FullRegistrationService {
	
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException;

	public LoginDetails loginCustomer(String emailId, String password) throws CustomerRegistrationException;
	
	public CustomerDetails existingCustomer(String emailId) throws CustomerRegistrationException;
	
	public SecurityQuestions retrieveQuestions(String emailId) throws CustomerRegistrationException;

	public String validateSecurityQuestions(SecurityQuestions request) throws CustomerRegistrationException;

	public CustomerDetails retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException;
	
	public String updatePhoneVerifiedFlag(Integer customerId, String phoneverifiedFlag) throws CustomerRegistrationException;
	
	public String resetCustomerCredential(String emailId, String newPassword) throws CustomerRegistrationException;
	
	public String updateCustomerDetails(RegistrationRequest registrationRequest) throws CustomerRegistrationException;
	
	public String updateCustomerDefaultAddress(DefaultAddressFlag address) throws CustomerRegistrationException;
	
	public CustomerDetails retrieveCustomerDetails(Integer customerId, String email) throws CustomerRegistrationException;
	
	public String addAdditionalAddress(Address address) throws CustomerRegistrationException;
	
	public String addAdditionalCardInfo(CardInformation cardInfo) throws CustomerRegistrationException;
	
}
