package com.gogenie.customer.fullregistration.service.impl;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

@Named
@Service
public class FullRegistrationServiceImpl implements FullRegistrationService {

	@Autowired
	FullRegistrationDAO fullRegistrationDao;

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws CustomerRegistrationException {
		RegistrationResponse registrationResponse = fullRegistrationDao.registerCustomer(registrationRequest);
		return registrationResponse;
	}

	@Override
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException {
		boolean isCustomerExist = fullRegistrationDao.existingCustomer(emailId) ;
		return isCustomerExist;
	}

	@Override
	public SecurityQuestions retrieveQuestions(String emailId) throws CustomerRegistrationException {
		SecurityQuestions questionsAndAnswers = fullRegistrationDao.retrieveSecurityQuestion(emailId);
		return questionsAndAnswers;
	}

	@Override
	public boolean resetCustomerCredential(String emailId, String newPassword) throws CustomerRegistrationException {
		boolean passwordReset = fullRegistrationDao.resetPassword(emailId, newPassword);
		return passwordReset;
	}
}
