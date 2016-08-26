package com.gogenie.customer.fullregistration.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gogenie.customer.fullregistration.model.CountryCache;
import com.gogenie.customer.fullregistration.model.StateCache;

public class CountryStateCityMapExtractor implements ResultSetExtractor<Map<Integer, CountryCache>> {

	@Override
	public Map<Integer, CountryCache> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
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

}
