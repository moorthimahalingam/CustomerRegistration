package com.gogenie.customer.fullregistration.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class State {

	@JsonProperty("state_id")
	private Integer stateId;
	@JsonProperty("state_name")
	private String stateName;
	@JsonProperty("cities")
	private List<City> cities;
	
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	
}
