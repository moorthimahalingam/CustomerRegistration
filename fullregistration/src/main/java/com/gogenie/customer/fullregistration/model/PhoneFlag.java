package com.gogenie.customer.fullregistration.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneFlag {

	@JsonProperty("customer_id")
	@NotNull(message="error.customerId.notnull")
	private Integer customerId;
	
	@JsonProperty("phoneValidationFlag")
	@NotNull(message="error.phoneValidationFlag.notnull")
	private String phoneValidationFlag;

	@JsonProperty("customer_id")
	public Integer getCustomerId() {
		return customerId;
	}

	@JsonProperty("customer_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@JsonProperty("phoneValidationFlag")
	public String getPhoneValidationFlag() {
		return phoneValidationFlag;
	}

	@JsonProperty("phoneValidationFlag")
	public void setPhoneValidationFlag(String phoneValidationFlag) {
		this.phoneValidationFlag = phoneValidationFlag;
	}
	
	
}
