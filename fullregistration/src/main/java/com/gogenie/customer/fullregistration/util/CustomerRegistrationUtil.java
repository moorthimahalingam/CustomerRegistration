package com.gogenie.customer.fullregistration.util;

import java.util.Random;

public class CustomerRegistrationUtil {

	public String encryption(String nonEncryptedText) {
		String encrypted = "daslf324324afdas!#@#@!";
		return encrypted;
	}
	
	public Integer generateAndSendPhoneVerificationCode(String phonenumber) {
		Random random = new Random();
		Integer phoneVerificationCode = random.nextInt(1000000);
		//Send phone verification code to customer mobile number
		return phoneVerificationCode;
	}
	
	
	/*public static void main(String[] args) {
		for (int i = 0; i <15; i++) {
			System.out.println(CustomerRegistrationUtil.generateAndSendPhoneVerificationCode("123213123"));
		}
	}*/
}
