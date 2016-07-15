package com.gogenie.customer.fullregistration.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.CustomerDetails;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.util.constants.CustomerConstants;

public class CustomerDetailsExtractor  implements ResultSetExtractor<CustomerDetails> {
	
	Logger logger = LoggerFactory.getLogger(CustomerDetailsExtractor.class);

	@Override
	public CustomerDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
		logger.debug("Entering into extractData() ");
		CustomerDetails customerDetails = null;
		List<Address> addressList = new ArrayList<Address>();
		List<CardInformation> cardInfoList = new ArrayList<CardInformation>();
		Map<Object, Object> addressDetailIdMap = new HashMap<Object, Object>();
		Map<Object, Object> paymentInfoIdMap = new HashMap<Object, Object>();
		while (rs.next()) {
			logger.debug("Result set value is {}", rs.toString());
			if (customerDetails == null) {
				customerDetails = new CustomerDetails();
				customerDetails.setCustomerId(rs.getInt(CustomerConstants.CUST_ID));
				customerDetails.setFirstname(rs.getString(CustomerConstants.FIRSTNAME));
				customerDetails.setLastname(rs.getString(CustomerConstants.LASTNAME));
				customerDetails.setDateofbirth(rs.getDate(CustomerConstants.DATEOFBIRTH));
				customerDetails.setEmail(rs.getString(CustomerConstants.EMAIL));
				customerDetails.setWorkphone(rs.getString(CustomerConstants.WORKPHONE));
				customerDetails.setMobilephone(rs.getString(CustomerConstants.MOBILEPHONE));
				customerDetails.setPhoneValidationFlag(rs.getString(CustomerConstants.PHONE_ISVALID));
				customerDetails.setCustIsActive(rs.getString(CustomerConstants.CUST_ISACTIVE));
				SecurityQuestions securityQuestions = new SecurityQuestions();
				securityQuestions.setQuestion1(rs.getString(CustomerConstants.SECURITY_QUESTION1));
				securityQuestions.setAnswer1(rs.getString(CustomerConstants.SECURITY_ANSWER1));
				securityQuestions.setQuestion2(rs.getString(CustomerConstants.SECURITY_QUESTION2));
				securityQuestions.setAnswer2(rs.getString(CustomerConstants.SECURITY_ANSWER2));
				customerDetails.setSecurityQuestions(securityQuestions);
			}
			Long addressDetailId = rs.getLong(CustomerConstants.ADDRESS_DETAILS_ID);
			if (addressDetailId != null) {
				if (addressDetailIdMap.get(addressDetailId) == null) {
					addressDetailIdMap.put(addressDetailId, addressDetailId);
					Address address = new Address();
					address.setAddressId(addressDetailId);
					address.setAddressline1(rs.getString(CustomerConstants.ADDRESS1));
					address.setAddressline2(rs.getString(CustomerConstants.ADDRESS2));
					address.setCountry(rs.getInt(CustomerConstants.COUNTRY_ID));
					address.setCity(rs.getInt(CustomerConstants.CITY_ID));
					address.setState(rs.getInt(CustomerConstants.STATE_ID));
					address.setPostalcode(rs.getString(CustomerConstants.ZIPCODE));
					address.setDefaultAddressFlag(rs.getString("ISDEFAULT_ADDRESS"));
					addressList.add(address);
				}
			}
			
			Long customerPaymentInfoId = rs.getLong("CUSTOMER_PAYMENT_INFO_ID");
			if (customerPaymentInfoId != null) {
				if (paymentInfoIdMap.get(customerPaymentInfoId) == null) {
					paymentInfoIdMap.put(customerPaymentInfoId, customerPaymentInfoId);
					CardInformation cardInformation = new CardInformation();
					cardInformation.setPaymentInfoId(customerPaymentInfoId);
					cardInformation.setPaymentType(rs.getString("PAYEMENTTYPE"));
					cardInformation.setCreditcardnumber(rs.getString("CARDNUMBER"));
					cardInformation.setExpirydate(rs.getString("EXPIRYDATE"));
					cardInformation.setCvvNumber(rs.getInt("CVV_NUMBER"));
					cardInformation.setNameOnCard(rs.getString("NAME_ON_CARD"));
					Address address = new Address();
//					address.setAddressId(addressDetailId);
					address.setAddressline1(rs.getString("payment_address_1"));
					address.setAddressline2(rs.getString("payment_address_2"));
					address.setCountry(rs.getInt("payment_country"));
					address.setCity(rs.getInt("payment_city"));
					address.setState(rs.getInt("payment_state"));
					address.setPostalcode(rs.getString("payment_zipcode"));
					address.setDefaultAddressFlag(rs.getString("payment_default_addr"));
					cardInformation.setAddress(address);
					cardInfoList.add(cardInformation);
				}
			}
		}
		
		if (customerDetails != null) {
			customerDetails.setAddress(addressList);
			customerDetails.setCardinformation(cardInfoList);
		}
		logger.debug("Exiting from extractData() ");
		return customerDetails;
	}

}
