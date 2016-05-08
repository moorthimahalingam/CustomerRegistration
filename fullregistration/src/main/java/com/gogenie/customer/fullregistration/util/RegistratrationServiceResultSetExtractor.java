package com.gogenie.customer.fullregistration.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class RegistratrationServiceResultSetExtractor implements ResultSetExtractor<List<String>>{

	@Override
	public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
