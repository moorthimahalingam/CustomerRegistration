package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;

public interface FullRegistrationDAO {
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest);
}
