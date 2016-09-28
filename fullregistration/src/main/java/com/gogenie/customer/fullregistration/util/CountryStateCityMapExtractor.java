package com.gogenie.customer.fullregistration.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gogenie.customer.fullregistration.model.City;
import com.gogenie.customer.fullregistration.model.Country;
import com.gogenie.customer.fullregistration.model.CountryCache;
import com.gogenie.customer.fullregistration.model.State;
import com.gogenie.customer.fullregistration.model.StateCache;

public class CountryStateCityMapExtractor implements ResultSetExtractor<List<Country>> {

	private Logger logger = LoggerFactory.getLogger(CountryStateCityMapExtractor.class);
//	@Override
	public Map<Integer, CountryCache> extractData1(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, CountryCache> countryStateCityMap = new HashMap<Integer, CountryCache>();
		Map<Integer, String> cityMap = null;
		Map<Integer, StateCache> stateMap = null;
		StateCache stateCache = null;
		
		while (rs.next()) {
			Integer countryId = rs.getInt("country_id");
			CountryCache countryCache = countryStateCityMap.get(countryId);
			if (countryCache == null) {
				countryCache = new CountryCache();
				countryCache.setCountryName(rs.getString("country_name"));
				countryCache.setCountryId(countryId);
				stateCache = new StateCache();
				stateCache.setState(rs.getString("state_name"));
				cityMap = new HashMap<Integer, String>();
				cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
				stateCache.setCityCache(cityMap);
				stateMap = new HashMap<Integer, StateCache>();
				stateMap.put(rs.getInt("state_id"), stateCache);
				countryCache.setStateCache(stateMap);
				countryStateCityMap.put(countryId, countryCache);
			} else {
				stateMap = countryCache.getStateCache();
				stateCache = stateMap.get(rs.getInt("state_id"));
				if (stateCache != null) {
					cityMap = stateCache.getCityCache();
					cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
				} else {
					stateCache = new StateCache();
					stateCache.setStateId(rs.getInt("state_id"));
					stateCache.setState(rs.getString("state_name"));
					cityMap = new HashMap<>();
					cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
					stateCache.setCityCache(cityMap);
					stateMap.put(rs.getInt("state_id"), stateCache);
				}
			}
		}
		return countryStateCityMap;
	}
	
	@Override
	public List<Country> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Integer, Country> countryStateCityMap = new HashMap<Integer, Country>();
		List<Country> countries = new ArrayList<>();
		Map<Integer, State> stateMap = null;
		while (rs.next()) {
			logger.debug("Inside result set" );
			Integer countryId = rs.getInt("country_id");
			Integer stateId = rs.getInt("state_id");
			Country country = countryStateCityMap.get(countryId);
			if (country == null) {
				logger.debug("New country has been added");
				stateMap = new HashMap<>();
				country = new Country();
				country.setCountryId(rs.getInt("country_id"));
				country.setCountryName(rs.getString("country_name"));
				List<State> states = new ArrayList<>();
				State state = new State();
				state.setStateId(stateId);
				state.setStateName(rs.getString("state_name"));
				List<City> cities = new ArrayList<>();
				City city = new City();
				city.setCityId(rs.getInt("city_id"));
				city.setCityName(rs.getString("city_name"));
				cities.add(city);
				state.setCities(cities);
				states.add(state);
				stateMap.put(stateId, state);
				country.setStates(states);
			} else {
				List<State> states = country.getStates();
				State state = stateMap.get(stateId);
				if (state != null) {
					logger.debug("Existing state ");
					City city  = new City();
					city.setCityId(rs.getInt("city_id"));
					city.setCityName(rs.getString("city_name"));
					logger.debug("City values are {} " , state.getCities());
					state.getCities().add(city);
					states.add(state);
				} else {
					logger.debug("New state has been added");
					List<City> cities = new ArrayList<>();
					state = new State();
					state.setStateId(stateId);
					state.setStateName(rs.getString("state_name"));
					City city  = new City();
					city.setCityId(rs.getInt("city_id"));
					city.setCityName(rs.getString("city_name"));
					cities.add(city);
					state.setCities(cities);
					states.add(state);
					stateMap.put(stateId, state);
					country.setStates(states);
				}
			}
			countryStateCityMap.put(countryId, country);
		}
		
		countryStateCityMap.forEach((countryId, country) ->  countries.add(country));
		return countries;
	}

}
