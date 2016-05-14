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
import com.gogenie.customer.fullregistration.util.CustomerRegistrationUtil;

@Named
@Service
public class FullRegistrationServiceImpl implements FullRegistrationService {

	@Autowired
	FullRegistrationDAO fullRegistrationDao;

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {
		CustomerRegistrationUtil registrationServiceUtil = new CustomerRegistrationUtil();
		RegistrationResponse registrationResponse = null;
		if (!fullRegistrationDao.existingCustomer(registrationRequest.getEmail())) {
			registrationServiceUtil.generateAndSendPhoneVerificationCode(registrationRequest.getMobilephone());
			registrationResponse = fullRegistrationDao.registerCustomer(registrationRequest);
		} else {
			registrationResponse = new RegistrationResponse();
			registrationResponse.setRegistrationSuccess(false);
			registrationResponse.setResponseText("User is already exist");
		}
		return registrationResponse;
	}

	@Override
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException {
		boolean isCustomerExist = fullRegistrationDao.existingCustomer(emailId);
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

	@Override
	public RegistrationResponse loginCustomer(String emailId, String password) throws CustomerRegistrationException {
		RegistrationResponse registrationResponse = fullRegistrationDao.loginCustomer(emailId, password);
		return registrationResponse;
	}

	@Override
	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException {
		String securityQuestionValidatedResult = fullRegistrationDao.validateSecurityQuestions(request);
		return securityQuestionValidatedResult;
	}

	@Override 
	public RegistrationResponse retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException {
		RegistrationResponse response = fullRegistrationDao.retrievePhoneVerifiedFlag(emailId);
		return response;
	}
	
	@Override
	public String updatePhoneVerifiedFlag(String customerId, String phoneverifiedFlag)
			throws CustomerRegistrationException {
		String phoneFlagUpdatedText = fullRegistrationDao.updatePhoneVerifiedFlag(customerId, phoneverifiedFlag);
		return phoneFlagUpdatedText;
	}
}
