package com.gogenie.customer.registration.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

/**
 * 
 *
 */
@RestController
public class CustomerRegistrationController {

	Logger logger = LoggerFactory.getLogger(CustomerRegistrationController.class);

	@Inject
	FullRegistrationService registrationService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public RegistrationResponse customerRegistration(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into customerRegistration()");
		String email = request.getEmail();
		logger.debug("Validating the customer is existing or not {}", email);
		boolean isExistingCustomer = registrationService.existingCustomer(email);
		logger.debug("Customer existing flag {}", isExistingCustomer);

		RegistrationResponse registrationResponse = new RegistrationResponse();
		if (isExistingCustomer) {
			String registration = "User is already exists";
			registrationResponse.setRegistrationSuccess(false);
			registrationResponse.setResponseText(registration);
			return registrationResponse;
		}
		logger.debug("This user {} is not exist. Register as a new user", email);
		registrationResponse = registrationService.registerCustomer(request);
		logger.debug("User {} is registered sucessfully ", email);
		registrationResponse.setResponseText("User is successfully registered");
		logger.debug("Exiting from customerRegistration()");
		return registrationResponse;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public RegistrationResponse loginCustomer(@RequestParam(value = "email") String emailId,
			@RequestParam(value = "password") String password)
			throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		logger.debug("Check customer id {} is exist or not", emailId);
		RegistrationResponse respone = registrationService.loginCustomer(emailId, password);
		logger.debug("Exiting from loginCustomer()");
		return respone;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public String validateExistingCustomer(@RequestParam(value = "email") String emailId)
			throws CustomerRegistrationException {
		logger.debug("Entering into validateExistingCustomer()");
		boolean isExistingCustomer = registrationService.existingCustomer(emailId);
		logger.debug("Customer validated flag {} ", isExistingCustomer);
		if (isExistingCustomer) {
			return "Success";
		}
		logger.debug("Exiting from validateExistingCustomer()");
		return "Not a valid user";
	}

	@RequestMapping(value = "/retrieveSecurityQuestions", method = RequestMethod.GET)
	public SecurityQuestions retrieveSecurityQuestionsToResetPassword(@RequestParam(value = "email") String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveSecurityQuestionsToResetPassword()");
		SecurityQuestions questions = registrationService.retrieveQuestions(emailId);
		logger.debug("Exiting from retrieveSecurityQuestionsToResetPassword()");
		return questions;
	}

	@RequestMapping(value = "/validateSecurityQuestions", method = RequestMethod.GET)
	public String validateSecurityQuestionsToResetPassword(@RequestBody RegistrationRequest request) throws CustomerRegistrationException {
		logger.debug("Entering into validateSecurityQuestionsToResetPassword()");
		String answerDetail = registrationService.validateSecurityQuestions(request);
		logger.debug("Exiting from validateSecurityQuestionsToResetPassword()");
		return answerDetail;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
	public String resetPassword(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into resetPassword()");
		logger.debug("Resetting the password for the email {}", request.getEmail());
		boolean passwordReset = registrationService.resetCustomerCredential(request.getEmail(), request.getPassword());
		if (passwordReset) {
			return "Password updated successfully";
		}
		logger.debug("Exiting from resetPassword()");
		return "Couldn't update this time. Please try again later";
	}

	@RequestMapping(value = "/retrievePhoneIsValidFlag", method = RequestMethod.GET)
	public RegistrationResponse retrivePhoneValidationFlag(@RequestParam(value = "email") String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrivePhoneValidationFlag()");
		RegistrationResponse response = registrationService.retrievePhoneVerifiedFlag(emailId);
		logger.debug("Exiting from retrivePhoneValidationFlag()");
		return response;
	}

	@RequestMapping(value = "/updateVerificationFlag", method = RequestMethod.PUT)
	public String updatePhoneValidationFlag(@RequestBody RegistrationRequest request)
			throws CustomerRegistrationException {
		logger.debug("Entering into updatePhoneValidationFlag()");
		Integer customerId = request.getCustomerId();
		String verifiedFlag = request.getPhoneValidationFlag();
		logger.debug("Update the phone verified flag as {}  for the customer {}", verifiedFlag, customerId);
		String response = registrationService.updatePhoneVerifiedFlag(customerId, verifiedFlag);
		logger.debug("Exiting from updatePhoneValidationFlag()");
		return response;
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT)
	public String updateCustomerDetails(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDetails()");
		String response = registrationService.updateCustomerDetails(request);
		logger.debug("Exiting from updateCustomerDetails()");
		return response;
	}

	@RequestMapping(value = "/updateCustAddressAsDefault", method = RequestMethod.PUT)
	public String updateCustomerDefaultAddress(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDefaultAddress()");
		String response = registrationService.updateCustomerDefaultAddress(request.getAddress().getAddressId(),
				request.getCustomerId());
		logger.debug("Exiting from updateCustomerDefaultAddress()");
		return response;
	}

	@RequestMapping(value = "retrieveCustomerDetails", method = RequestMethod.GET)
	public @ResponseBody CustomerDetails retrieveCustomerDetails(@RequestParam(value = "customer_id") Integer customerId,
			@RequestParam(value = "email") String emailId) throws CustomerRegistrationException {
		CustomerDetails customerDetails = null;
		logger.debug("Entering into retrieveCustomerDetails()");
		try{
			customerDetails = registrationService.retrieveCustomerDetails(customerId, emailId);
		logger.debug("Exiting from retrieveCustomerDetails()");
		}catch(Exception e){
			e.printStackTrace();
		}
		return customerDetails;
	}

	@ExceptionHandler(CustomerRegistrationException.class)
	public String exceptionHandler(CustomerRegistrationException exception) {
		return "Exception " + exception.getMessage();
	}
}
