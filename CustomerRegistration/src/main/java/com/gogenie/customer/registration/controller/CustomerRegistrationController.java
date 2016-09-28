package com.gogenie.customer.registration.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Country;
import com.gogenie.customer.fullregistration.model.CountryCache;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.GoGenieAdrCache;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.Questions;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestionCache;
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

	@Inject
	GoGenieAdrCache goGenieAdrCache;

	@Inject
	SecurityQuestionCache securityQuestionCache;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public RegistrationResponse customerRegistration(@Validated @RequestBody RegistrationRequest request,
			BindingResult result) throws CustomerRegistrationException {
		logger.debug("Entering into customerRegistration()");
		RegistrationResponse registrationResponse = registrationService.registerCustomer(request);
		logger.debug("Exiting from customerRegistration()");
		return registrationResponse;
	}

	@RequestMapping(value = "/retrievePhoneIsValidFlag", method = RequestMethod.GET)
	public CustomerDetails retrivePhoneValidationFlag(@RequestParam(value = "email" , required=true) String emailId)
			throws CustomerRegistrationException {
		logger.debug("Entering into retrivePhoneValidationFlag()");
		CustomerDetails response = registrationService.retrievePhoneVerifiedFlag(emailId);
		logger.debug("Exiting from retrivePhoneValidationFlag()");
		return response;
	}

	@RequestMapping(value = "/updateVerificationFlag", method = RequestMethod.PUT)
	public @ResponseBody String updatePhoneValidationFlag(@RequestBody RegistrationRequest request)
			throws CustomerRegistrationException {
		logger.debug("Entering into updatePhoneValidationFlag()");
		Integer customerId = request.getCustomerId();
		String verifiedFlag = request.getPhoneValidationFlag();
		logger.debug("Update the phone verified flag as {}  for the customer {}", verifiedFlag, customerId);
		String response = registrationService.updatePhoneVerifiedFlag(customerId, verifiedFlag);
		logger.debug("Exiting from updatePhoneValidationFlag()");
		return "{\"responseText\" :" + response + "}";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public LoginDetails loginCustomer(@RequestParam(value = "email", required=true) String emailId,
			@RequestParam(value = "password", required = true) String password) throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		LoginDetails response = registrationService.loginCustomer(emailId, password);
		logger.debug("Exiting from loginCustomer()");
		return response;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public CustomerDetails validateExistingCustomer(@RequestParam(value="email", required=true) String emailId)
			throws CustomerRegistrationException {
		logger.debug("Entering into validateExistingCustomer()");
		CustomerDetails existingCustomerDetails = registrationService.existingCustomer(emailId);
		logger.debug("Exiting from validateExistingCustomer()");
		return existingCustomerDetails;
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT)
	public @ResponseBody String updateCustomerDetails(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDetails()");
		String response = registrationService.updateCustomerDetails(request);
		logger.debug("Exiting from updateCustomerDetails()");
		return "{\"responseText\" :" + response + "}";
	}

	@RequestMapping(value = "/add_Address", method = RequestMethod.POST)
	public String addNewAddress(@RequestBody RegistrationRequest request, BindingResult result) {
		logger.debug("Entering into addNewAddress");
		String response = registrationService.addAdditionalAddress(request.getAddress(), request.getCustomerId());
		logger.debug("Exiting from addNewAddress");
		return "{\"responseText\" :" + response + "}";
	}

	@RequestMapping(value = "/add_cardDetails", method = RequestMethod.POST)
	public String addNewCardDetails(@RequestBody RegistrationRequest request, BindingResult result) {
		logger.debug("Entering into addNewCardDetails");
		String response = registrationService.addAdditionalCardInfo(request.getCardInformation(),
				request.getCustomerId());
		logger.debug("Exiting from addNewCardDetails");
		return "{\"responseText\" :" + response + "}";
	}

	@RequestMapping(value = "/updateCustAddressAsDefault", method = RequestMethod.PUT)
	public String updateCustomerDefaultAddress(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDefaultAddress()");
		String response = registrationService.updateCustomerDefaultAddress(request.getAddress(),
				request.getCustomerId());
		logger.debug("Exiting from updateCustomerDefaultAddress()");
		return "{\"responseText\" :" + response + "}";
	}

	@RequestMapping(value = "/retrieveCustomerDetails", method = RequestMethod.GET)
	public @ResponseBody CustomerDetails retrieveCustomerDetails(
			@RequestParam(value = "customer_id", required = true) Integer customerId,
			@RequestParam(value = "email", required = true) String emailId) throws CustomerRegistrationException {
		CustomerDetails customerDetails = null;
		logger.debug("Entering into retrieveCustomerDetails()");
		customerDetails = registrationService.retrieveCustomerDetails(customerId, emailId);
		logger.debug("Exiting from retrieveCustomerDetails()");
		return customerDetails;
	}

	@RequestMapping(value = "/retrieveSecurityQuestions", method = RequestMethod.GET)
	public SecurityQuestions retrieveSecurityQuestionsToResetPassword(@RequestParam(value = "email" , required=true) String emailId)
			throws CustomerRegistrationException {
		logger.debug("Entering into retrieveSecurityQuestionsToResetPassword()");
		SecurityQuestions questions = registrationService.retrieveQuestions(emailId);
		logger.debug("Exiting from retrieveSecurityQuestionsToResetPassword()");
		return questions;
	}

	@RequestMapping(value = "/validateSecurityQuestions", method = RequestMethod.POST)
	public String validateSecurityQuestionsToResetPassword(@RequestBody RegistrationRequest request)
			throws CustomerRegistrationException {
		logger.debug("Entering into validateSecurityQuestionsToResetPassword()");
		String matched = registrationService.validateSecurityQuestions(request);
		logger.debug("Exiting from validateSecurityQuestionsToResetPassword()");
		return "{\"matched\": " + matched + "}";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
	public String resetPassword(@RequestBody RegistrationRequest request, BindingResult result)
			throws CustomerRegistrationException {
		logger.debug("Entering into resetPassword()");
		logger.debug("Resetting the password for the email {}", request.getEmail());
		boolean passwordReset = false;
		String response;
		if (request.getEmail() != null) {
			passwordReset = registrationService.resetCustomerCredential(request.getEmail(), request.getPassword());
			if (!passwordReset) {
				response = "Couldn't update this time. Please try again later";
			}
		} else {
			response = "Email id is not passed";
		}
		response = "Password has been reset successfully";
		logger.debug("Exiting from resetPassword()");
		return "{\"responseText\":" + response + "}";
	}

	@RequestMapping(value = "/countrystateMapping", method = RequestMethod.GET)
	public @ResponseBody List<Country> retrieveCountryStateMapping() throws CustomerRegistrationException {
		logger.debug("Entering into retrieveCountryStateMapping()");
//		Map<Integer, CountryCache> countryCache = goGenieAdrCache.getCountryStateCityMap();
		List<Country> countryCache = goGenieAdrCache.getCountryStateCityMap();
		logger.debug("Exiting from retrieveCountryStateMapping()");
		return countryCache;
	}

	@RequestMapping(value = "/security_questions", method = RequestMethod.GET)
	public @ResponseBody List<Questions> retrieveQuestions() throws CustomerRegistrationException {
		logger.debug("Entering into retrieveQuestions()");
		List<Questions> questionsList = securityQuestionCache.getSecurityQuestionsList();
		logger.debug("Exiting from retrieveQuestions()");
		return questionsList;
	}

	@ExceptionHandler(CustomerRegistrationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String exceptionHandler(CustomerRegistrationException exception) {
		return "{\"errorResponse\":" + exception.getErrorDesc() + "}";
	}
}
