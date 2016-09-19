package com.gogenie.customer.fullregistration.exception;

public class CustomerRegistrationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4074951255773438898L;

	public CustomerRegistrationException() {
		super();
	}
	
	public CustomerRegistrationException(String message) {
		super(message);
	}
	
	public CustomerRegistrationException(Throwable t, String errorCode) {
		super(errorCode,t);
	}
	
	public CustomerRegistrationException(Exception e, String errorCode) {
		super(errorCode,e);
	}

	public CustomerRegistrationException(Throwable t) {
		super(t);
	}

	public CustomerRegistrationException(Exception e) {
		super(e);
	}
	
	private String errorCode;
	private String errorDesc;

	public CustomerRegistrationException(Throwable throwable, String errorDtl, String errCode,
			String errDec) {
		super(errorDtl, throwable);
		this.errorCode = errCode;
		this.errorDesc = errDec;
	}
	
	public CustomerRegistrationException(Exception exception, String errorDtl, String errCode,
			String errDec) {
		super(errorDtl, exception);
		this.errorCode = errCode;
		this.errorDesc = errDec;
	}

	public CustomerRegistrationException(String errCode,
			String errDec) {
		this.errorCode = errCode;
		this.errorDesc = errDec;
	}
}
