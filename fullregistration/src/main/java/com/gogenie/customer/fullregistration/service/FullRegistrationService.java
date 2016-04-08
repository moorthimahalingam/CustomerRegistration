package com.gogenie.customer.fullregistration.service;

import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;

public interface FullRegistrationService {
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest);
}
