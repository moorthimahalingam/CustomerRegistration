package com.gogenie.customer.fullregistration.dao.impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.CardInfoDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.customer.fullregistration.model.CardInformation;
import com.gogenie.customer.fullregistration.model.GoGenieAdrCache;
import com.gogenie.customer.fullregistration.util.CustomerRegistrationConstants;
import com.gogenie.util.constants.CustomerConstants;
import com.gogenie.util.service.EncryptionService;
import com.gogenie.util.service.impl.EncryptionServiceImpl;

@Repository
public class CardInfoDAOImpl implements CardInfoDAO {

	Logger logger = LoggerFactory.getLogger(CardInfoDAOImpl.class);

	@Resource
	private DataSource gogenieDataSource;

	private SimpleJdbcCall simpleJdbcCall;
	private SimpleJdbcCall updateCardCall;
	private JdbcTemplate jdbcTemplate;

	@Inject
	private GoGenieAdrCache adrCache;

	@PostConstruct
	private void initialize() {
		this.simpleJdbcCall = new SimpleJdbcCall(gogenieDataSource);
		this.updateCardCall = new SimpleJdbcCall(gogenieDataSource);
		this.jdbcTemplate = new JdbcTemplate(gogenieDataSource);
	}

	@Override
	public boolean insertCardInformation(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException {
		logger.debug("Entering into insertCardInformation()");
		try {
			jdbcTemplate.update("update customer_payment_info set ISDEFAULT='N' where CUST_ID = ?",
					new Object[] { customerId });

			simpleJdbcCall.withProcedureName("post_cust_payment_info").withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter(CustomerConstants.CUST_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.PAYEMENTTYPE, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CARDNUMBER, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CARD_LAST_4_DIGITS, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CARD_LA, Types.VARCHAR),
							new SqlParameter(CustomerConstants.EXPIRYDATE, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CVV_NUMBER, Types.VARCHAR),
							new SqlParameter(CustomerConstants.NAME_ON_CARD, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CREATEDBY, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CREATEDDATE, Types.DATE),
							new SqlParameter(CustomerConstants.ADDRESS1, Types.VARCHAR),
							new SqlParameter(CustomerConstants.ADDRESS2, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CITY_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.STATE_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.COUNTRY_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.ZIPCODE, Types.VARCHAR),
							new SqlOutParameter("estatus", Types.VARCHAR),
							new SqlOutParameter("sstatus", Types.VARCHAR));
			Map<String, Object> cardInsertResult = simpleJdbcCall
					.execute(customerCardInformationDataMap(cardInfo, customerId));

			logger.debug("Card Insert Resultset is {}", cardInsertResult.toString());

			if (cardInsertResult.get("estatus") != null) {
				errorMessageHandler((String) cardInsertResult.get("estatus"));
			}
			logger.debug("Card information table data has been executed successfully {} ", customerId);
		} catch (Exception e) {
			logger.error("Error while inserting customer card information {}" , e.getMessage());
			jdbcTemplate.update("delete from address_details where cust_id=?", new Object[] { customerId });
			jdbcTemplate.update("delete from customer where cust_id=?", new Object[] { customerId });
			if(e instanceof CustomerRegistrationException) {
				CustomerRegistrationException exception = (CustomerRegistrationException)e;
				throw exception;
			}
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0013,
					CustomerRegistrationConstants.CUST_REGISTN_0013_DESC);
		}
		logger.debug("Exiting from insertCardInformation()");
		return true;
	}

	private Map<String, Object> customerCardInformationDataMap(CardInformation cardInformation, Integer custId) {
		logger.debug("Entering into customerCardInformationDataMap()");
		Map<String, Object> cardDetails = new HashMap<>();
		cardDetails.put(CustomerConstants.CUST_ID, custId);
		cardDetails.put(CustomerConstants.PAYEMENTTYPE, cardInformation.getPaymentType());
		String cardNumber = cardInformation.getCardnumber();
		EncryptionService encryptionService = new EncryptionServiceImpl();
		String encryptedCardNumber = encryptionService.hashedValue(cardNumber);
		String last4Digits = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
		cardDetails.put(CustomerConstants.CARDNUMBER, encryptedCardNumber);
		cardDetails.put(CustomerConstants.CARD_LAST_4_DIGITS, last4Digits);
		cardDetails.put(CustomerConstants.CARD_LA, last4Digits);
		cardDetails.put(CustomerConstants.EXPIRYDATE, cardInformation.getExpirydate());
		cardDetails.put(CustomerConstants.CVV_NUMBER, cardInformation.getCvvNumber());
		cardDetails.put(CustomerConstants.NAME_ON_CARD, cardInformation.getNameOnCard());
		cardDetails.put(CustomerConstants.CREATEDDATE, new java.sql.Date(new Date().getTime()));
		cardDetails.put(CustomerConstants.CREATEDBY, "gogenie");
		Address billingAddress = cardInformation.getAddress();
		if (billingAddress != null) {
			cardDetails.put(CustomerConstants.ADDRESS1, billingAddress.getAddressline1());
			cardDetails.put(CustomerConstants.ADDRESS2, billingAddress.getAddressline2());
			cardDetails.put(CustomerConstants.CITY_ID, billingAddress.getCity());
			cardDetails.put(CustomerConstants.STATE_ID, billingAddress.getState());
			cardDetails.put(CustomerConstants.COUNTRY_ID, billingAddress.getCountry());
			cardDetails.put(CustomerConstants.ZIPCODE, billingAddress.getPostalcode());
			cardDetails.put(CustomerConstants.ISDEFAULT, billingAddress.getDefaultAddressFlag());
		}
		logger.debug("Exiting from customerCardInformationDataMap()");
		return cardDetails;
	}

	@Override
	public boolean updateCardInformation(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCardInformation()");
		try {
			updateCardCall.withProcedureName("put_customer_default_payment").withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("cust_payment_info_id", Types.INTEGER),
							new SqlParameter("cu_id", Types.INTEGER), new SqlParameter("paytype", Types.VARCHAR),
							new SqlParameter("cardnum", Types.VARCHAR),
							new SqlParameter("card_last_4_digits", Types.VARCHAR),
							new SqlParameter("expdate", Types.VARCHAR), new SqlParameter("cvv_num", Types.VARCHAR),
							new SqlParameter("name_on_c", Types.VARCHAR), new SqlParameter("up_date", Types.DATE),
							new SqlParameter("up_by", Types.VARCHAR), new SqlParameter("add1", Types.VARCHAR),
							new SqlParameter("add2", Types.VARCHAR), new SqlParameter("ci_id", Types.INTEGER),
							new SqlParameter("st_id", Types.INTEGER), new SqlParameter("cou_id", Types.INTEGER),
							new SqlParameter("zip", Types.VARCHAR), new SqlParameter("isdef", Types.VARCHAR),
							new SqlOutParameter("estatus", Types.VARCHAR),
							new SqlOutParameter("sstatus", Types.VARCHAR));
			Map<String, Object> cardInsertResult = updateCardCall
					.execute(customerUpdateCardInformationDataMap(cardInfo, customerId));

			logger.debug("Card Insert Resultset is {}", cardInsertResult.toString());
			logger.debug("Card information table data has been executed successfully {} ", customerId);
		} catch (Exception e) {
			logger.error("Error while updating card information {}" , e.getMessage());
			throw new CustomerRegistrationException(CustomerRegistrationConstants.CUST_REGISTN_0017,
					CustomerRegistrationConstants.CUST_REGISTN_0017_DESC);
		}
		logger.debug("Exiting from updateCardInformation()");
		return true;
	}

	private Map<String, Object> customerUpdateCardInformationDataMap(CardInformation cardInformation, Integer custId) {
		logger.debug("Entering into customerUpdateCardInformationDataMap()");
		Map<String, Object> cardDetails = new HashMap<>();
		cardDetails.put("cust_payment_info_id", cardInformation.getPaymentInfoId());
		cardDetails.put("cu_id", custId);
		cardDetails.put("paytype", cardInformation.getPaymentType());
		String cardNumber = cardInformation.getCardnumber();
		EncryptionService encryptionService = new EncryptionServiceImpl();
		String encryptedCardNumber = encryptionService.hashedValue(cardNumber);
		String last4Digits = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
		cardDetails.put("cardnum", encryptedCardNumber);
		cardDetails.put("card_last_4_digits", last4Digits);
		cardDetails.put("expdate", cardInformation.getExpirydate());
		cardDetails.put("cvv_num", cardInformation.getCvvNumber());
		cardDetails.put("name_on_c", cardInformation.getNameOnCard());
		cardDetails.put("up_date", new java.sql.Date(new Date().getTime()));
		cardDetails.put("up_by", "Customer");
		Address billingAddress = cardInformation.getAddress();
		if (billingAddress != null) {
			cardDetails.put("add1", billingAddress.getAddressline1());
			cardDetails.put("add2", billingAddress.getAddressline2());
			cardDetails.put("ci_id", billingAddress.getCity());
			cardDetails.put("st_id", billingAddress.getState());
			cardDetails.put("cou_id", billingAddress.getCountry());
			cardDetails.put("zip", billingAddress.getPostalcode());
			cardDetails.put("isdef", billingAddress.getDefaultAddressFlag());
		}
		logger.debug("Exiting from customerUpdateCardInformationDataMap()");
		return cardDetails;
	}

	@Override
	public boolean updateDefaultCardInfo(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException {
		return false;
	}

	/**
	 * 
	 * @param errorMessage
	 * @return
	 */
	private void errorMessageHandler(String errorMessage) throws CustomerRegistrationException {
		String errorMsg[] = errorMessage.split(":");
		CustomerRegistrationException cre = new CustomerRegistrationException(errorMsg[0], errorMsg[1]);
		throw cre;
	}

}
