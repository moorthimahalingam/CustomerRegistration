package com.gogenie.customer.fullregistration.dao.impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gogenie.customer.fullregistration.dao.AddressDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Address;
import com.gogenie.util.constants.CustomerConstants;

@Repository
public class AddressDAOImpl implements AddressDAO {

	Logger logger = LoggerFactory.getLogger(AddressDAOImpl.class);

	@Resource
	private DataSource gogenieDataSource;
	
	private SimpleJdbcCall simpleJdbcCall;

	@PostConstruct
	private void initialize() {
		this.simpleJdbcCall = new SimpleJdbcCall(gogenieDataSource);
	}

	@Override
	public boolean insertCustomerAddress(Address address, Integer customerId) throws CustomerRegistrationException {
		logger.debug("Entering into insertCustomerAddress()");
		try {
			simpleJdbcCall.withProcedureName("post_address_details").withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter(CustomerConstants.CUST_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.COUNTRY_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.STATE_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.CITY_ID, Types.INTEGER),
							new SqlParameter(CustomerConstants.ADDRESS1, Types.VARCHAR),
							new SqlParameter(CustomerConstants.ADDRESS2, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CREATEDBY, Types.VARCHAR),
							new SqlParameter(CustomerConstants.CREATEDDATE, Types.DATE),
							new SqlParameter(CustomerConstants.ZIPCODE, Types.VARCHAR),
							new SqlParameter(CustomerConstants.ISDEFAULT, Types.VARCHAR),
							new SqlOutParameter("estatus", Types.VARCHAR),
							new SqlOutParameter("sstatus", Types.VARCHAR));

			Map<String, Object> addressResult = simpleJdbcCall.execute(customerAddresDataMap(address, customerId));
			
			logger.debug("Address details insert {} result ", addressResult.toString());

			if (addressResult.get("estatus") != null) {
				errorMessageHandler((String)addressResult.get("estatus"));
			}
 			
			logger.debug("Customer {} address has been added successfully {} ", customerId);
			
			logger.debug("Exiting from insertCustomerAddress()");

		} catch (Exception e) {
			logger.error("Error while inserting customer address ");
			logger.error("Customer details insert has been rollbacked ");
			throw new CustomerRegistrationException(e, "23432432");
		}
		return false;
	}

	private Map<String, Object> customerAddresDataMap(Address address, Integer custId) {
		logger.debug("Entering into customerAddresDataMap()");
		Map<String, Object> addressDetails = new HashMap<>();
		addressDetails.put(CustomerConstants.CUST_ID, custId);
		addressDetails.put(CustomerConstants.COUNTRY_ID, address.getCountry());
		addressDetails.put(CustomerConstants.STATE_ID, address.getState());
		addressDetails.put(CustomerConstants.CITY_ID, address.getCity());
		addressDetails.put(CustomerConstants.ADDRESS1, address.getAddressline1());
		addressDetails.put(CustomerConstants.ADDRESS2, address.getAddressline2());
		addressDetails.put(CustomerConstants.CREATEDDATE, new java.sql.Date(new Date().getTime()));
		addressDetails.put(CustomerConstants.CREATEDBY, "gogenie");
		addressDetails.put(CustomerConstants.ZIPCODE, address.getPostalcode());
		addressDetails.put(CustomerConstants.ISDEFAULT, "Y");
		logger.debug("Exiting from customerAddresDataMap()");
		return addressDetails;
	}

	@Override
	public String updateCustomerAddress(Address address, Integer customerId) throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerAddress()");
		try {
			simpleJdbcCall.withProcedureName("put_customer_address").withoutProcedureColumnMetaDataAccess()
			.declareParameters(new SqlParameter("add_details_id", Types.INTEGER),
					new SqlParameter("cu_id", Types.INTEGER), new SqlParameter("cou_id", Types.INTEGER),
					new SqlParameter("st_id", Types.INTEGER), new SqlParameter("ci_id", Types.INTEGER),
					new SqlParameter("add1", Types.VARCHAR), new SqlParameter("add2", Types.VARCHAR),
					new SqlParameter("up_by", Types.VARCHAR), new SqlParameter("up_date", Types.DATE),
					new SqlParameter("zip", Types.VARCHAR), new SqlParameter("isdef_address", Types.VARCHAR),
					new SqlOutParameter("estatus", Types.VARCHAR), new SqlOutParameter("sstatus", Types.VARCHAR));
			
			Map<String, Object> addressResult = simpleJdbcCall.execute(customerUpdateAddresDataMap(address, customerId));
			logger.debug("Address details insert {} result ", addressResult.toString());
			if (addressResult.get("estatus") != null) {
				errorMessageHandler((String)addressResult.get("estatus"));
			}
			
			logger.debug("Customer {} address detail has been updated", customerId);
			
			logger.debug("Exiting from updateCustomerAddress()");
		} catch (Exception e) {
			logger.error("Error while updating customer address ");
			throw new CustomerRegistrationException(e, "2343242");
		}
		return "Success";
	}

	private Map<String, Object> customerUpdateAddresDataMap(Address address, Integer custId) {
		logger.debug("Entering into customerUpdateAddresDataMap()");
		Map<String, Object> addressDetails = new HashMap<>();
		addressDetails.put("add_details_id", address.getAddressId());
		addressDetails.put("cu_id", custId);
		addressDetails.put("cou_id", address.getCountry());
		addressDetails.put("st_id", address.getState());
		addressDetails.put("ci_id", address.getCity());
		addressDetails.put("add1", address.getAddressline1());
		addressDetails.put("add2", address.getAddressline2());
		addressDetails.put("up_by", "customer");
		addressDetails.put("up_date", new java.sql.Date(new Date().getTime()));
		addressDetails.put("zip", address.getPostalcode());
		addressDetails.put("isdef_address", "Y");
		logger.debug("Exiting from customerUpdateAddresDataMap()");
		return addressDetails;
	}

	@Override
	public String updateCustomerDefaultAddress(Address address, Integer customerId)
			throws CustomerRegistrationException {
		logger.debug("Entering into updateCustomerDefaultAddress()");
		String response = null;
		try {
			simpleJdbcCall.withProcedureName("put_customer_default_address").withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("add_details_id", Types.BIGINT),
							new SqlParameter("cu_id", Types.INTEGER), new SqlParameter("up_date", Types.DATE),
							new SqlParameter("up_by", Types.BIGINT),
							new SqlParameter("isdef_address", Types.VARCHAR),
							new SqlOutParameter("estatus", Types.VARCHAR),
							new SqlOutParameter("sstaus", Types.VARCHAR));
			Map<String, Object> updateDefaultAdrMap = new HashMap<String, Object>();
			updateDefaultAdrMap.put("add_details_id", address.getAddressId());
			updateDefaultAdrMap.put("cu_id", customerId);
			updateDefaultAdrMap.put("up_date", new java.sql.Date(new Date().getTime()));
			updateDefaultAdrMap.put("up_by", 12313123);
			updateDefaultAdrMap.put("isdef_address", "Y");
			Map<String, Object> result = simpleJdbcCall.execute(updateDefaultAdrMap);
			if (result.get("estatus") != null) {
				errorMessageHandler((String)result.get("estatus"));
			}
			response = (String)result.get("sstatus");
			
		} catch (Exception e) {
			throw new CustomerRegistrationException(e, "updateCustomerDefaultAddress");
		}
		logger.debug("Exiting from into updateCustomerDefaultAddress()");
		return response;
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
