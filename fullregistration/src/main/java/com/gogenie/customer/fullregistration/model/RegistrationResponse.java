package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegistrationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1391697961446227121L;

	@JsonProperty("registrationSuccess")
	private boolean registrationSuccess;
	
	@JsonProperty("responseText")
	private String responseText;

	@JsonProperty("customer_id")
	private String customerId;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("phone_isValid")
	private String phoneIsValid;

	
	@JsonProperty("responseText")
	public String getResponseText() {
		return responseText;
	}

	@JsonProperty("responseText")
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	@JsonProperty("registrationSuccess")
	public boolean getRegistrationSuccess() {
		return registrationSuccess;
	}

	@JsonProperty("registrationSuccess")
	public void setRegistrationSuccess(boolean registrationSuccess) {
		this.registrationSuccess = registrationSuccess;
	}

	@JsonProperty("customer_id")
	public String getCustomerId() {
		return customerId;
	}

	@JsonProperty("customer_id")
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("phone_isValid")
	public String getPhoneIsValid() {
		return phoneIsValid;
	}

	@JsonProperty("phone_isValid")
	public void setPhoneIsValid(String phoneIsValid) {
		this.phoneIsValid = phoneIsValid;
	}

	@Override
	public String toString() {
		return "RegistrationResponse [registrationSuccess=" + registrationSuccess + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	} 
	
}
