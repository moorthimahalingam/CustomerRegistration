package com.gogenie.customer.fullregistration.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPassword {

	@JsonProperty("customer_id")
    private Integer customerId;
	
    @JsonProperty("email")
    @NotNull(message="error.email.notnull")
    @Email(message="error.email.invalid")
    private String email;
    
    @JsonProperty("password")
    @NotNull(message="error.password.notnull")
    private String password;

    private String encryptedPassword;

    @JsonProperty("email")
	public String getEmail() {
		return email;
	}

    @JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

    @JsonProperty("password")
	public String getPassword() {
		return password;
	}

    @JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@JsonProperty("customer_id")
	public Integer getCustomerId() {
		return customerId;
	}

	@JsonProperty("customer_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
    
    
}
