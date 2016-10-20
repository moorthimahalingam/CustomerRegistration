package com.gogenie.customer.registration.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.Country;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.DefaultAddressFlag;
import com.gogenie.customer.fullregistration.model.ErrorMessages;
import com.gogenie.customer.fullregistration.model.GoGenieAdrCache;
import com.gogenie.customer.fullregistration.model.LoginDetails;
import com.gogenie.customer.fullregistration.model.MessageType;
import com.gogenie.customer.fullregistration.model.PhoneFlag;
import com.gogenie.customer.fullregistration.model.Questions;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.ResetPassword;
import com.gogenie.customer.fullregistration.model.SecurityQuestionCache;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

/**
 * 
 *
 */
@RestController
@EnableWebMvc
public class CustomerLoginController {

	Logger logger = LoggerFactory.getLogger(CustomerLoginController.class);

	@Inject
	FullRegistrationService registrationService;

	@Inject
	GoGenieAdrCache goGenieAdrCache;

	@Inject
	SecurityQuestionCache securityQuestionCache;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public LoginDetails loginCustomer(@RequestParam(value = "email", required = true) String emailId,
			@RequestParam(value = "password", required = true) String password) throws CustomerRegistrationException {
		logger.debug("Entering into loginCustomer()");
		LoginDetails response = registrationService.loginCustomer(emailId, password);
		logger.debug("Exiting from loginCustomer()");
		return response;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public CustomerDetails validateExistingCustomer(@RequestParam(value = "email", required = true) String emailId)
			throws CustomerRegistrationException {
		logger.debug("Entering into validateExistingCustomer()");
		CustomerDetails existingCustomerDetails = registrationService.existingCustomer(emailId);
		logger.debug("Exiting from validateExistingCustomer()");
		return existingCustomerDetails;
	}

	@RequestMapping(value = "/retrieveSecurityQuestions", method = RequestMethod.GET)
	public SecurityQuestions retrieveSecurityQuestionsToResetPassword(
			@RequestParam(value = "email", required = true) String emailId) throws CustomerRegistrationException {
		logger.debug("Entering into retrieveSecurityQuestionsToResetPassword()");
		SecurityQuestions questions = registrationService.retrieveQuestions(emailId);
		logger.debug("Exiting from retrieveSecurityQuestionsToResetPassword()");
		return questions;
	}

	@RequestMapping(value = "/validateSecurityQuestions", method = RequestMethod.POST)
	public String validateSecurityQuestionsToResetPassword(@Validated @RequestBody SecurityQuestions request)
			throws CustomerRegistrationException {
		logger.debug("Entering into validateSecurityQuestionsToResetPassword()");
		String matched = registrationService.validateSecurityQuestions(request);
		logger.debug("Exiting from validateSecurityQuestionsToResetPassword()");
		return "{\"matched\": \"" + matched + " \"}";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
	public String resetPassword(@Validated @RequestBody ResetPassword request)
			throws CustomerRegistrationException {
		logger.debug("Entering into resetPassword()");
		logger.debug("Resetting the password for the email {}", request.getEmail());
		String response = registrationService.resetCustomerCredential(request.getEmail(), request.getPassword());
		logger.debug("Exiting from resetPassword()");
		return "{\"responseText\" : \"" + response + " \"}";
	}

	@ExceptionHandler(CustomerRegistrationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String exceptionHandler(CustomerRegistrationException exception) {
		return "{\"errorResponse\": \"" + exception.getErrorDesc() + " \"}";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessages processValidationError(MethodArgumentNotValidException ex) {
		logger.error("Method argument not valid ", ex.getMessage());
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();
		return processFieldError(error);
	}

	private ErrorMessages processFieldError(FieldError error) {
		ErrorMessages errorMessage = null;
		if (error != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
			errorMessage = new ErrorMessages(MessageType.ERROR, msg);
		}
		return errorMessage;
	}
}
