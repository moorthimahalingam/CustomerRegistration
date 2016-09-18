package com.gogenie.customer.fullregistration.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gogenie.customer.fullregistration.dao.FullRegistrationDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

public class FullRegistrationServiceImplTest {

	@Mock
	FullRegistrationDAO fullRegistrationDAO;

	@InjectMocks
	FullRegistrationServiceImpl service;

	RegistrationRequest registrationRequest;
	RegistrationResponse registrationResponse;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		registrationRequest = new RegistrationRequest();
		registrationResponse = new RegistrationResponse();
	}

	@Test
	public void registerCustomer_return_response() throws CustomerRegistrationException {
		when(fullRegistrationDAO.registerCustomer(registrationRequest)).thenReturn(registrationResponse);
		RegistrationResponse response = service.registerCustomer(registrationRequest);
		assertNotNull(response);
		verify(fullRegistrationDAO).registerCustomer(registrationRequest);
	}

	@Test
	public void registerCustomer_should_return_NullResponse() throws CustomerRegistrationException {
		// when(fullRegistrationDAO.registerCustomer(registrationRequest)).thenReturn(null);
		// RegistrationResponse response =
		// service.registerCustomer(registrationRequest);
		// assertNull(response);
		// verify(fullRegistrationDAO).registerCustomer(registrationRequest);
	}

	@Test(expected = CustomerRegistrationException.class)
	public void registerCustomer_should_throw_exception() throws CustomerRegistrationException {
		when(fullRegistrationDAO.registerCustomer(registrationRequest)).thenThrow(CustomerRegistrationException.class);
		service.registerCustomer(registrationRequest);
		verify(fullRegistrationDAO).registerCustomer(registrationRequest);
	}

	@Test
	public void existing_customer_should_return_true() throws CustomerRegistrationException {
		String emailId = "test@gmail.com";
		when(fullRegistrationDAO.existingCustomer(emailId)).thenReturn(new CustomerDetails());
		CustomerDetails customerDetails = service.existingCustomer(emailId);
		assertNotNull(customerDetails);
		verify(fullRegistrationDAO).existingCustomer(emailId);
	}

	@Test
	public void existing_customer_should_return_false() throws CustomerRegistrationException {
		String emailId = "test@gmail.com";
		when(fullRegistrationDAO.existingCustomer(emailId)).thenReturn(new CustomerDetails());
		CustomerDetails customerDetails = service.existingCustomer(emailId);
		assertNull(customerDetails);
		verify(fullRegistrationDAO).existingCustomer(emailId);
	}

	@Test(expected = CustomerRegistrationException.class)
	public void existing_customer_should_throw_exception() throws CustomerRegistrationException {
		String emailId = "test@gmail.com";
		when(fullRegistrationDAO.existingCustomer(emailId)).thenThrow(CustomerRegistrationException.class);
		service.existingCustomer(emailId);
		verify(fullRegistrationDAO).existingCustomer(emailId);
	}

	@Test
	public void retrieveQuestions_should_return_SecurityQuestion() throws CustomerRegistrationException {
		String emailId = "eama@ga.com";
		SecurityQuestions questions = new SecurityQuestions();
		when(fullRegistrationDAO.retrieveSecurityQuestion(emailId)).thenReturn(questions);
		SecurityQuestions retrivedquestions = service.retrieveQuestions(emailId);
		assertNotNull(retrivedquestions);
		verify(fullRegistrationDAO).retrieveSecurityQuestion(emailId);
	}

	@Test
	public void retrieveQuestions_should_return_NULL() throws CustomerRegistrationException {
		String emailId = "eama@ga.com";
		when(fullRegistrationDAO.retrieveSecurityQuestion(emailId)).thenReturn(null);
		SecurityQuestions retrivedquestions = service.retrieveQuestions(emailId);
		assertNull(retrivedquestions);
		verify(fullRegistrationDAO).retrieveSecurityQuestion(emailId);
	}

	@Test(expected = CustomerRegistrationException.class)
	public void retrieveQuestions_should_throw_Exception() throws CustomerRegistrationException {
		String emailId = "eama@ga.com";
		when(fullRegistrationDAO.retrieveSecurityQuestion(emailId)).thenThrow(CustomerRegistrationException.class);
		SecurityQuestions retrivedquestions = service.retrieveQuestions(emailId);
		verify(fullRegistrationDAO).retrieveSecurityQuestion(emailId);
	}

	@Test
	public void reset_Customer_credential_sucessful() throws CustomerRegistrationException {
		String emailId = "dafkj@ac.com";
		String password = "dskafjkjda";
		when(fullRegistrationDAO.resetPassword(emailId, password)).thenReturn(true);
		boolean resetSuccess = service.resetCustomerCredential(emailId, password);
		assertTrue(resetSuccess);
		verify(fullRegistrationDAO).resetPassword(emailId, password);
	}

	@Test
	public void reset_Customer_credential_failed() throws CustomerRegistrationException {
		String emailId = "dafkj@ac.com";
		String password = "dskafjkjda";
		when(fullRegistrationDAO.resetPassword(emailId, password)).thenReturn(false);
		boolean resetSuccess = service.resetCustomerCredential(emailId, password);
		assertFalse(resetSuccess);
		verify(fullRegistrationDAO).resetPassword(emailId, password);
	}

	@Test(expected = CustomerRegistrationException.class)
	public void reset_Customer_credential_throw_exception() throws CustomerRegistrationException {
		String emailId = "dafkj@ac.com";
		String password = "dskafjkjda";
		when(fullRegistrationDAO.resetPassword(emailId, password)).thenThrow(CustomerRegistrationException.class);
		service.resetCustomerCredential(emailId, password);
		verify(fullRegistrationDAO).resetPassword(emailId, password);
	}

	@After
	public void tearDown() {
		registrationRequest = null;
		registrationResponse = null;
	}
}
