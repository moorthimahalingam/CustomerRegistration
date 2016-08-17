package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;

public interface AddressDAO {

	public boolean insertCustomerAddress(Address address, Integer customerId) throws CustomerRegistrationException;
	
	public String updateCustomerAddress(Address address , Integer customerId) throws CustomerRegistrationException;
	
	public String updateCustomerDefaultAddress(Address address, Integer customerId) throws CustomerRegistrationException;
}
