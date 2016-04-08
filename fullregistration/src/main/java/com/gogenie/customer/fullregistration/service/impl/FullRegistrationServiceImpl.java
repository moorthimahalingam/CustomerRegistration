package com.gogenie.customer.fullregistration.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

@Named
public class FullRegistrationServiceImpl implements FullRegistrationService {

	@Inject
	FullRegistrationDAO fullRegistrationDao;

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = fullRegistrationDao.registerCustomer(registrationRequest);
		return registrationResponse;
	}
}
