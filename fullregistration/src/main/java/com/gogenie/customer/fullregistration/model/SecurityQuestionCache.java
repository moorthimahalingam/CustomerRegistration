package com.gogenie.customer.fullregistration.model;

import java.util.List;

import javax.inject.Named;

@Named("securityQuestionCache")
public class SecurityQuestionCache {

	private List<Questions> securityQuestionsList;

	public List<Questions> getSecurityQuestionsList() {
		return securityQuestionsList;
	}

	public void setSecurityQuestionsList(List<Questions> securityQuestionsList) {
		this.securityQuestionsList = securityQuestionsList;
	}
	
}
