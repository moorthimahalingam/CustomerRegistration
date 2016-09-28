package com.gogenie.customer.fullregistration.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
	
	@JsonProperty("country_id")
	private Integer countryId;
	@JsonProperty("country_name")
	private String countryName;
	@JsonProperty("states")
	private List<State> states;
	
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	
}
