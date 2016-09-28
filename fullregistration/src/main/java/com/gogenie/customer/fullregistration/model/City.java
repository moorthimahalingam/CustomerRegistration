package com.gogenie.customer.fullregistration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {

	@JsonProperty("city_id")
	private Integer cityId;
	@JsonProperty("city_name")
	private String cityName;
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
