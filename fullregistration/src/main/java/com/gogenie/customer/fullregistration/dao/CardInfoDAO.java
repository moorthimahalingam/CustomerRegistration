package com.gogenie.customer.fullregistration.dao;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.CardInformation;

public interface CardInfoDAO {

	public boolean insertCardInformation(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException;

	public boolean updateCardInformation(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException;

	public boolean updateDefaultCardInfo(CardInformation cardInfo, Integer customerId)
			throws CustomerRegistrationException;
}
