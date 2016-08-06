
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
//@JsonInclude(Include.NON_NULL)
public class CardInformation implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6313894630775815219L;
	
	@JsonProperty ("payment_info_id")
	private Long paymentInfoId;
	
	@JsonProperty("credit_card_number")
    private String creditcardnumber;
	
    private String encryptedCreditcardumber;

	@JsonProperty("expiry_date")
    private String expirydate;

	private String encryptedExpirydate;

	@JsonProperty("payment_type")
	private String paymentType;
	
	@JsonProperty("cvv_number")
	private Integer cvvNumber;
	
	private String encryptedCVVNumber;
	
	@JsonProperty("name_on_card")
	private String nameOnCard;
	
	
	@JsonProperty("Address")
	private Address address;

	@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty ("payment_info_id")
    public Long getPaymentInfoId() {
		return paymentInfoId;
	}

	@JsonProperty ("payment_info_id")
	public void setPaymentInfoId(Long paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}

	/**
     * 
     * @return
     *     The creditcardnumber
     */
    @JsonProperty("credit_card_number")
    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    /**
     * 
     * @param creditcardnumber
     *     The creditcardnumber
     */
    @JsonProperty("credit_card_number")
    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

//	@JsonProperty("encrypted_creditcardnumber")
	public String getEncryptedCreditcardumber() {
		return encryptedCreditcardumber;
	}

//	@JsonProperty("encrypted_creditcardnumber")
	public void setEncryptedCreditcardumber(String encryptedCreditcardumber) {
		this.encryptedCreditcardumber = encryptedCreditcardumber;
	}

//	@JsonProperty("encrypted_expirydate")
	public String getEncryptedExpirydate() {
		return encryptedExpirydate;
	}

//	@JsonProperty("encrypted_expirydate")
	public void setEncryptedExpirydate(String encryptedExpirydate) {
		this.encryptedExpirydate = encryptedExpirydate;
	}
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonProperty("expiry_date")
    public String getExpirydate() {
    	return expirydate;
    }
    
    @JsonProperty("expiry_date")
    public void setExpirydate(String expirydate) {
    	this.expirydate = expirydate;
    }

	@JsonProperty("Address")
	public Address getAddress() {
		return address;
	}

	@JsonProperty("Address")
	public void setAddress(Address address) {
		this.address = address;
	}

	@JsonProperty("payment_type")
    public String getPaymentType() {
		return paymentType;
	}

	@JsonProperty("payment_type")
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@JsonProperty("cvv_number")
	public Integer getCvvNumber() {
		return cvvNumber;
	}

	@JsonProperty("cvv_number")
	public void setCvvNumber(Integer cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	public String getEncryptedCVVNumber() {
		return encryptedCVVNumber;
	}

	public void setEncryptedCVVNumber(String encryptedCVVNumber) {
		this.encryptedCVVNumber = encryptedCVVNumber;
	}

	@JsonProperty("name_on_card")
	public String getNameOnCard() {
		return nameOnCard;
	}

	@JsonProperty("name_on_card")
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
