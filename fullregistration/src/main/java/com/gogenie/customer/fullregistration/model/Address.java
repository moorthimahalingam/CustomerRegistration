
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
public class Address implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1668407827289138958L;

	@JsonProperty("addressId")
	private Long addressId;
	@JsonProperty("addressline1")
    private String addressline1;
    @JsonProperty("addressline2")
    private String addressline2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postalcode")
    private String postalcode;
    @JsonProperty("country")
    private String country;
    
	@JsonProperty("defaultAddressFlag")
    private String defaultAddressFlag;
    

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The addressline1
     */
    @JsonProperty("addressline1")
    public String getAddressline1() {
        return addressline1;
    }

    /**
     * 
     * @param addressline1
     *     The addressline1
     */
    @JsonProperty("addressline1")
    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    /**
     * 
     * @return
     *     The addressline2
     */
    @JsonProperty("addressline2")
    public String getAddressline2() {
        return addressline2;
    }

    /**
     * 
     * @param addressline2
     *     The addressline2
     */
    @JsonProperty("addressline2")
    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    /**
     * 
     * @return
     *     The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The postalcode
     */
    @JsonProperty("postalcode")
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * 
     * @param postalcode
     *     The postalcode
     */
    @JsonProperty("postalcode")
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @JsonProperty("country")
    public String getCountry() {
		return country;
	}

    @JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonProperty("defaultAddressFlag")
    public String getDefaultAddressFlag() {
		return defaultAddressFlag;
	}

    @JsonProperty("defaultAddressFlag")
	public void setDefaultAddressFlag(String defaultAddressFlag) {
		this.defaultAddressFlag = defaultAddressFlag;
	}
    
    @JsonProperty("addressId")
    public Long getAddressId() {
		return addressId;
	}

    @JsonProperty("addressId")
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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
