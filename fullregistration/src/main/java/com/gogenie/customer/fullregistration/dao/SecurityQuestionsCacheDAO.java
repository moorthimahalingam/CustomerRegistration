package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.SecurityQuestionCache;

public interface SecurityQuestionsCacheDAO {
	
	public void populateSecurityQuestionCache() throws CustomerRegistrationException;
	
}
