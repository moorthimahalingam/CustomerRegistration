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

	
	@Override
	public String toString() {
		return "RegistrationResponse [registrationSuccess=" + registrationSuccess + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	} 
	
}
