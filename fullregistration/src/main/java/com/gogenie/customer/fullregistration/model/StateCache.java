package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.Map;

public class StateCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7323237192368095324L;
	
	private String state;
	private Map<Integer, String> cityCache;
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public Map<Integer, String> getCityCache() {
		return cityCache;
	}
	
	public void setCityCache(Map<Integer, String> cityCache) {
		this.cityCache = cityCache;
	}
	
	
	
}
