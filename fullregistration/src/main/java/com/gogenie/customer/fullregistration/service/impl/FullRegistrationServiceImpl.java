package com.gogenie.customer.fullregistration.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gogenie.customer.fullregistration.dao.AddressDAO;
import com.gogenie.customer.fullregistration.dao.CardInfoDAO;
import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

@Named
@Service
public class FullRegistrationServiceImpl implements FullRegistrationService {

	Logger logger = LoggerFactory.getLogger(FullRegistrationServiceImpl.class);

	@Inject
	FullRegistrationDAO fullRegistrationDao;
	
	@Inject
	AddressDAO addressDao;
	
	@Inject
	CardInfoDAO cardInfoDao;
	
	@Override
	public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest)
			throws CustomerRegistrationException {
		
		logger.debug("Entering into registerCustomer()");
		RegistrationResponse registrationResponse = null;
		registrationResponse = fullRegistrationDao.registerCustomer(registrationRequest);
		if (registrationResponse.getRegistrationSuccess() && registrationResponse.getCustomerDetails() != null) {
			Integer customerId = registrationResponse.getCustomerDetails().getCustomerId();
			if (customerId != null) {
				Address address = registrationRequest.getAddress();
				if (address != null) {
					addressDao.insertCustomerAddress(address, customerId);
				}
				CardInformation cardInfo = registrationRequest.getCardInformation();
				if (cardInfo != null) {
					cardInfoDao.insertCardInformation(cardInfo, customerId);
				}
				registrationResponse.setResponseText("Customer registration is successfully completed");
			}
		}
		logger.debug("Exiting from registerCustomer()");
		return registrationResponse;
	}

	@Override
	public CustomerDetails existingCustomer(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into existingCustomer()");
		CustomerDetails customerDetails = fullRegistrationDao.existingCustomer(emailId);
		logger.debug("Exiting from existingCustomer()");
		return customerDetails;
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
	public LoginDetails loginCustomer(String emailId, String password) throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		LoginDetails loginDetails = fullRegistrationDao.loginCustomer(emailId, password);
		logger.debug("Exiting from loginCustomer()");
		return loginDetails;
	}

	@Override
	public String validateSecurityQuestions(RegistrationRequest request) throws CustomerRegistrationException {
		logger.debug("Entering into validateSecurityQuestions()");
		String securityQuestionValidatedResult = fullRegistrationDao.validateSecurityQuestions(request);
		logger.debug("Exiting from validateSecurityQuestions()");
		return securityQuestionValidatedResult;
	}

	@Override
	public CustomerDetails retrievePhoneVerifiedFlag(String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrievePhoneVerifiedFlag()");
		CustomerDetails response = fullRegistrationDao.retrievePhoneVerifiedFlag(emailId);
		logger.debug("Exiting from retrievePhoneVerifiedFlag()");
		return response;
	}

	@Override
	public String updatePhoneVerifiedFlag(Integer customerId, String phoneverifiedFlag)
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
		Address address = registrationRequest.getAddress();
		if (address != null) {
			addressDao.updateCustomerAddress(address, registrationRequest.getCustomerId());
		}
		CardInformation cardInfo = registrationRequest.getCardInformation();
		if (cardInfo != null) {
			cardInfoDao.updateCardInformation(cardInfo, registrationRequest.getCustomerId());
		}
		logger.debug("Exiting from updateCustomerDetails()");
		return response;
	}

	@Override
	public String updateCustomerDefaultAddress(Address address, Integer customerId)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDefaultAddress()");
		String response = addressDao.updateCustomerDefaultAddress(address, customerId);
		logger.debug("Exiting from updateCustomerDefaultAddress()");
		return response;
	}

	@Override
	public CustomerDetails retrieveCustomerDetails(Integer customerId, String email) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveCustomerDetails()");
		CustomerDetails customerDetails = fullRegistrationDao.retrieveCustomerDetails(customerId, email);
		logger.debug("Exiting from retrieveCustomerDetails()");
		return customerDetails;
	}

	@Override
	public String addAdditionalAddress(Address address, Integer customerId) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveCustomerDetails()");
		addressDao.insertCustomerAddress(address, customerId);
		logger.debug("Exiting from retrieveCustomerDetails()");
		return "Success";
	}

	@Override
	public String addAdditionalCardInfo(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException {
		logger.debug("Entering into retrieveCustomerDetails()");
		cardInfoDao.insertCardInformation(cardInfo, customerId);
		logger.debug("Exiting from retrieveCustomerDetails()");
		return "Success";
	}
	
	
}
