package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.Map;

public class CountryCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973338346759241633L;
	
	private String countryName;
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
	
	
}
