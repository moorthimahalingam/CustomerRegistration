package com.gogenie.customer.registration.controller;

import javax.inject.Inject;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

/**
 * Hello world!
 *
 */
@RestController
public class CustomerRegistrationController 
{
	
	@Inject
	FullRegistrationService registrationService;
	
	@RequestMapping(value="/registration", method= RequestMethod.POST)
	public RegistrationResponse customerRegistration(@RequestBody RegistrationRequest request , BindingResult result){
		RegistrationResponse registrationResponse = registrationService.registerCustomer(request);
		return registrationResponse;
	}
	
}
