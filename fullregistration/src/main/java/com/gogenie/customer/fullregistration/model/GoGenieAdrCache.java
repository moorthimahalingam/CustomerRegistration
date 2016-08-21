package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Named;

@Named("goGenieAdrCache")
public class GoGenieAdrCache implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2663323852810824418L;
	
	private Map<Integer, String> countryMap;
	private Map<Integer, String> stateMap;
	private Map<Integer, String> cityMap;
	private Map<Integer, StateCache> stateCityMap;
	private Map<Integer, CountryCache> countryStateCityMap;
	
	public Map<Integer, CountryCache> getCountryStateCityMap() {
		return countryStateCityMap;
	}

	public void setCountryStateCityMap(Map<Integer, CountryCache> countryStateCityMap) {
		this.countryStateCityMap = countryStateCityMap;
	}

	public Map<Integer, StateCache> getStateCityMap() {
		return stateCityMap;
	}

	public void setStateCityMap(Map<Integer, StateCache> stateCityMap) {
		this.stateCityMap = stateCityMap;
	}

	public Map<Integer, String> getCountryMap() {
		return countryMap;
	}
	
	public void setCountryMap(Map<Integer, String> countryMap) {
		this.countryMap = countryMap;
	}
	
	public Map<Integer, String> getStateMap() {
		return stateMap;
	}
	
	public void setStateMap(Map<Integer, String> stateMap) {
		this.stateMap = stateMap;
	}
	
	public Map<Integer, String> getCityMap() {
		return cityMap;
	}
	
	public void setCityMap(Map<Integer, String> cityMap) {
		this.cityMap = cityMap;
	}
	
	
}
