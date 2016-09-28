package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973338346759241633L;
	
	@JsonProperty("country_name")
	private String countryName;
	@JsonProperty("country_id")
	private Integer countryId;
	@JsonProperty("states")
	private Map<Integer, StateCache> stateCacheMap;

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Map<Integer, StateCache> getStateCache() {
		return stateCacheMap;
	}
	public void setStateCache(Map<Integer, StateCache> stateCacheMap) {
		this.stateCacheMap = stateCacheMap;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
}
