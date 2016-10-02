package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.DefaultAddressFlag;

public interface AddressDAO {

	public boolean insertCustomerAddress(Address address) throws CustomerRegistrationException;
	
	public String updateCustomerAddress(Address address) throws CustomerRegistrationException;
	
	public String updateCustomerDefaultAddress(DefaultAddressFlag address) throws CustomerRegistrationException;
}
