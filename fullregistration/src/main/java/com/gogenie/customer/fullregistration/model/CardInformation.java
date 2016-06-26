
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CardInformation implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6313894630775815219L;
	
	@JsonProperty("creditcardnumber")
    private String creditcardnumber;

//	@JsonProperty("encrypted_creditcardnumber")
    private String encryptedCreditcardumber;

	@JsonProperty("expirydate")
    private String expirydate;

//	@JsonProperty("encrypted_expirydate")
	private String encryptedExpirydate;

	@JsonProperty("Address")
	private Address address;

	@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The creditcardnumber
     */
    @JsonProperty("creditcardnumber")
    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    /**
     * 
     * @param creditcardnumber
     *     The creditcardnumber
     */
    @JsonProperty("creditcardnumber")
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

    @JsonProperty("expirydate")
    public String getExpirydate() {
    	return expirydate;
    }
    
    @JsonProperty("expirydate")
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
