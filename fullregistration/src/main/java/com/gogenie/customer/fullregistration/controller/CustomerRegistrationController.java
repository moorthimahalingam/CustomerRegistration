package com.gogenie.customer.fullregistration.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;

@RestController
public class CustomerRegistrationController {

	@RequestMapping(value="/registration", method= RequestMethod.POST)
	public RegistrationResponse customerRegistration(@RequestBody RegistrationRequest request , BindingResult result){
		return null;
	}
	
}
