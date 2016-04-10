package com.gogenie.customer.fullregistration.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

@Named
@Service
public class FullRegistrationServiceImpl implements FullRegistrationService {

	@Autowired
	FullRegistrationDAO fullRegistrationDao;

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = fullRegistrationDao.registerCustomer(registrationRequest);
		return registrationResponse;
	}
}
