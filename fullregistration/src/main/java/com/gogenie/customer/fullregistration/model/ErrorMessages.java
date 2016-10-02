package com.gogenie.customer.fullregistration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessages {

	@JsonProperty("errorMessage")
	private String message;
	private MessageType messageType;
	
	public ErrorMessages() {
		super();
	}
	
	public ErrorMessages(MessageType messageType, String message) {
		super();
		this.message = message;
		this.messageType = messageType;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	
}
