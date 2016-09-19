package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class LoginDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6265152414293998105L;

	@JsonProperty("login_status")
	private String loginStatus;
	@JsonProperty("customer_details")
	private CustomerDetails customerDetails;
	
	@JsonProperty("login_status")
	public String getLoginStatus() {
		return this.loginStatus;
	}
	@JsonProperty("login_status")
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	@JsonProperty("customer_details")
	public CustomerDetails getCustomerDetails() {
		return this.customerDetails;
	}
	
	@JsonProperty("customer_details")
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
}
