package com.gogenie.customer.fullregistration.service.impl;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(FullRegistrationServiceImpl.class);

	@Autowired
	FullRegistrationDAO fullRegistrationDao;

	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {
		logger.debug("Entering into registerCustomer()");

		CustomerRegistrationUtil registrationServiceUtil = new CustomerRegistrationUtil();
		RegistrationResponse registrationResponse = null;
		boolean isExistingCustomer = fullRegistrationDao.existingCustomer(registrationRequest.getEmail());
		logger.debug("Existing Customer or not flag {} ", isExistingCustomer);
		if (!isExistingCustomer) {
			logger.debug("Register as a new user ");
			String phoneNumber = registrationRequest.getMobilephone();
			logger.debug("Generating customer verification code for the phone number {} ", phoneNumber);
			registrationServiceUtil.generateAndSendPhoneVerificationCode(phoneNumber);
			logger.debug("Customer verification code has been sent sucessfully for the phone number {} ", phoneNumber);
			registrationResponse = fullRegistrationDao.registerCustomer(registrationRequest);
		} else {
			logger.debug("User is already exist ");
			registrationResponse = new RegistrationResponse();
			registrationResponse.setRegistrationSuccess(false);
			registrationResponse.setResponseText("User is already exist");
		}
		logger.debug("Exiting from registerCustomer()");
		return registrationResponse;
	}

	@Override
	public boolean existingCustomer(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into existingCustomer()");
		boolean isCustomerExist = fullRegistrationDao.existingCustomer(emailId);
		logger.debug("Exiting from existingCustomer()");
		return isCustomerExist;
	}

	@Override
	public SecurityQuestions retrieveQuestions(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveQuestions()");
		SecurityQuestions questionsAndAnswers = fullRegistrationDao.retrieveSecurityQuestion(emailId);
		logger.debug("Exiting from retrieveQuestions()");
		return questionsAndAnswers;
	}

	@Override
	public boolean resetCustomerCredential(String emailId, String newPassword) throws CustomerRegistrationException {
		logger.debug("Entering into resetCustomerCredential()");
		boolean passwordReset = fullRegistrationDao.resetPassword(emailId, newPassword);
		logger.debug("Exiting from resetCustomerCredential()");
		return passwordReset;
	}

	@Override
	public RegistrationResponse loginCustomer(String emailId, String password) throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		RegistrationResponse registrationResponse = fullRegistrationDao.loginCustomer(emailId, password);
		logger.debug("Exiting from loginCustomer()");
		return registrationResponse;
	}

	@Override
	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException {
		logger.debug("Entering into validateSecurityQuestions()");
		String securityQuestionValidatedResult = fullRegistrationDao.validateSecurityQuestions(request);
		logger.debug("Exiting from validateSecurityQuestions()");
		return securityQuestionValidatedResult;
	}

	@Override
	public RegistrationResponse retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrievePhoneVerifiedFlag()");
		RegistrationResponse response = fullRegistrationDao.retrievePhoneVerifiedFlag(emailId);
		logger.debug("Exiting from retrievePhoneVerifiedFlag()");
		return response;
	}

	@Override
	public String updatePhoneVerifiedFlag(Long customerId, String phoneverifiedFlag)
			throws CustomerRegistrationException {
		logger.debug("Entering into updatePhoneVerifiedFlag()");
		String phoneFlagUpdatedText = fullRegistrationDao.updatePhoneVerifiedFlag(customerId, phoneverifiedFlag);
		logger.debug("Exiting from updatePhoneVerifiedFlag()");
		return phoneFlagUpdatedText;
	}

	@Override
	public String updateCustomerDetails(RegistrationRequest registrationRequest) throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDetails()");
		String response = fullRegistrationDao.updateCustomerDetails(registrationRequest);
		logger.debug("Exiting from updateCustomerDetails()");
		return response;
	}

	@Override
	public String updateCustomerDefaultAddress(Long addressDetailId, Integer customerId)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDefaultAddress()");
		String response = fullRegistrationDao.updateCustomerDefaultAddress(addressDetailId, customerId);
		logger.debug("Exiting from updateCustomerDefaultAddress()");
		return response;
	}
}
