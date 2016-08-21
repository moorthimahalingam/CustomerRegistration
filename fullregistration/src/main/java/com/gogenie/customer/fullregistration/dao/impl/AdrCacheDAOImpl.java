package com.gogenie.customer.fullregistration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.gogenie.customer.fullregistration.dao.AdrCacheDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.CountryCache;
import com.gogenie.customer.fullregistration.model.GoGenieAdrCache;
import com.gogenie.customer.fullregistration.model.StateCache;
import com.gogenie.customer.fullregistration.util.CountryStateCityMapExtractor;

@Named("adrCache")
public class AdrCacheDAOImpl implements AdrCacheDAO {

	@Resource
	private DataSource gogenieDataSource;

	@Inject
	private GoGenieAdrCache goGenieAdrCache;

	private NamedParameterJdbcTemplate jdbcTemplate;

	Logger logger = LoggerFactory.getLogger(AdrCacheDAOImpl.class);

	@Override
	@PostConstruct
	public void populateAddressDetails() throws CustomerRegistrationException {
		logger.debug("Entering into populateAddressDetails()");
		jdbcTemplate = new NamedParameterJdbcTemplate(gogenieDataSource);

		Map<Integer, String> cityMap = (Map<Integer, String>) jdbcTemplate.query("select city_id, city_name from city",
				new ResultSetExtractor<Map<Integer, String>>() {
					@Override
					public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, String> cityMap = new HashMap<Integer, String>();
						while (rs.next()) {
							cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
						}
						return cityMap;
					}
				});

		logger.debug("City map values are {} ", cityMap);
		goGenieAdrCache.setCityMap(cityMap);

		Map<Integer, String> stateMap = (Map<Integer, String>) jdbcTemplate
				.query("select state_id, state_name from state", new ResultSetExtractor<Map<Integer, String>>() {
					@Override
					public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, String> stateMap = new HashMap<Integer, String>();
						while (rs.next()) {
							stateMap.put(rs.getInt("state_id"), rs.getString("state_name"));
						}
						return stateMap;
					}
				});

		logger.debug("State map values are {} ", stateMap.toString());
		goGenieAdrCache.setStateMap(stateMap);

		Map<Integer, String> countryMap = (Map<Integer, String>) jdbcTemplate
				.query("select country_id, country_name from country", new ResultSetExtractor<Map<Integer, String>>() {
					@Override
					public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, String> countryMap = new HashMap<Integer, String>();
						while (rs.next()) {
							countryMap.put(rs.getInt("country_id"), rs.getString("country_name"));
						}
						return countryMap;
					}
				});

		logger.debug("State map values are {} ", stateMap.toString());
		goGenieAdrCache.setCountryMap(countryMap);

		Map<Integer, StateCache> stateCityMap = (Map<Integer, StateCache>) jdbcTemplate.query(
				"select s.state_id, s.state_name, c.city_id, c.city_name from state s , city c where s.state_id=c.state_id order by s.state_id asc",
				new ResultSetExtractor<Map<Integer, StateCache>>() {
					@Override
					public Map<Integer, StateCache> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, StateCache> stateCityMap = new HashMap<Integer, StateCache>();
						while (rs.next()) {
							Integer stateId = rs.getInt("state_id");
							StateCache stateCache = stateCityMap.get(stateId);
							if (stateCache != null) {
								stateCache.getCityCache().put(rs.getInt("city_id"), rs.getString("city_name"));
							} else {
								Map<Integer, String> cityMap = new HashMap<Integer, String>();
								cityMap.put(rs.getInt("city_id"), rs.getString("city_name"));
								stateCache = new StateCache();
								stateCache.setCityCache(cityMap);
								stateCache.setState(rs.getString("state_name"));
								stateCityMap.put(rs.getInt("state_id"), stateCache);
							}
						}
						return stateCityMap;
					}
				});

		logger.debug("State and City map values are {} ", stateCityMap.toString());
		goGenieAdrCache.setStateCityMap(stateCityMap);

		Map<Integer, CountryCache> countryStateAndCityMap = (Map<Integer, CountryCache>) jdbcTemplate.query(
				"select co.country_id, co.country_name, s.state_id, s.state_name, c.city_id, c.city_name from state s , city c, country co where co.country_id=s.country_id and co.country_id=c.country_id order by co.country_id, s.state_id asc",
				new CountryStateCityMapExtractor());
		
		logger.debug("complete address mapping details {} " , countryStateAndCityMap.toString());
		goGenieAdrCache.setCountryStateCityMap(countryStateAndCityMap);

		logger.debug("Exiting from populateAddressDetails()");
	}

}
