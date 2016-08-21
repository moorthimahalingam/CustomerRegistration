package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;

public interface AdrCacheDAO {

	public void populateAddressDetails() throws CustomerRegistrationException;
	
}
