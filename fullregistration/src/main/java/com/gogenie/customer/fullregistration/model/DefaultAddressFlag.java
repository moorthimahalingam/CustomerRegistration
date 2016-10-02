
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
//@JsonInclude(Include.NON_NULL)
public class DefaultAddressFlag implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1668407827289138958L;

	@JsonProperty("address_id")
	@NotNull(message="error.adr.id.notnull")
	private Long addressId;
	
    
    @JsonProperty("address_id")
    public Long getAddressId() {
		return addressId;
	}

    @JsonProperty("address_id")
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

    @JsonProperty("customer_id")
    @NotNull(message="error.customerId.notnull")
    private Integer customerId;
	
    @JsonProperty("customer_id")
	public Integer getCustomerId() {
		return customerId;
	}
    
    @JsonProperty("customer_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
