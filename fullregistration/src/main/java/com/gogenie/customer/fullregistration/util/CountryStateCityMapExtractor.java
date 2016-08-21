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
		
		while (rs.next()) {
			Integer countryId = rs.getInt("country_id");
			CountryCache countryCache = countryStateCityMap.get(countryId); 
			if (countryCache != null) {
				Map<Integer, StateCache> stateCacheMap = countryCache.getStateCache();
				StateCache stateCache = stateCacheMap.get("state_id");
				if (stateCache != null) {
					Map<Integer, String> cityMap = new HashMap<Integer, String>();
					cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
					stateCache.setCityCache(cityMap);
				} else {
					stateCache = new StateCache();
					stateCache.setState(rs.getString("state_name"));
					Map<Integer, String> cityMap = new HashMap<Integer, String>();
					cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
					stateCache.setCityCache(cityMap);
					Map<Integer, StateCache> stateMap = new HashMap<Integer, StateCache>();
					stateMap.put(rs.getInt("state_id"), stateCache);
				}
			} else {
				countryCache = new CountryCache();
				countryCache.setCountryName(rs.getString("country_name"));
				StateCache stateCache = new StateCache();
				stateCache.setState(rs.getString("state_name"));
				Map<Integer, String> cityMap = new HashMap<Integer, String>();
				cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
				stateCache.setCityCache(cityMap);
				Map<Integer, StateCache> stateMap = new HashMap<Integer, StateCache>();
				stateMap.put(rs.getInt("state_id"), stateCache);
				countryCache.setStateCache(stateMap);
			}
		}
		return countryStateCityMap;
	}

}
