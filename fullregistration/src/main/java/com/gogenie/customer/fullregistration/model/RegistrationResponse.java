package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;

public class RegistrationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1391697961446227121L;

	private boolean registrationSuccess;

	public boolean isRegistrationSuccess() {
		return registrationSuccess;
	}

	public void setRegistrationSuccess(boolean registrationSuccess) {
		this.registrationSuccess = registrationSuccess;
	}

	@Override
	public String toString() {
		return "RegistrationResponse [registrationSuccess=" + registrationSuccess + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	} 
	
}
