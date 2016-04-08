package com.gogenie.customer.fullregistration.dao.impl;

import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;

@Repository
public class FullRegistrationDAOImpl implements FullRegistrationDAO {

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) {
		RegistrationResponse response = new RegistrationResponse();
		response.setRegistrationSuccess(true);
		return response;
	}

}
