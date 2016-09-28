package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7323237192368095324L;
	
	@JsonProperty("state")
	private String state;
	@JsonProperty("state_id")
	private Integer stateId;
	@JsonProperty("cities")
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

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
	
	
}
