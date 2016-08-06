package com.gogenie.customer.registration.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerRegistrationCROSFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(CustomerRegistrationCROSFilter.class);
	
	public CustomerRegistrationCROSFilter() {
		logger.debug("CustomerRegistrationCROSFilter constructor");
	}
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("Entering into doFilter method");
		 HttpServletRequest request = (HttpServletRequest) req;
		    HttpServletResponse response = (HttpServletResponse) res;
		    response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		    logger.debug("request header of Origin {}", request.getHeader("Origin"));
		    response.addHeader("Access-Control-Allow-Credentials", "false");
//		    response.addHeader("Access-Control-Allow-Origin", "*");
		    response.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		    response.addHeader("Access-Control-Max-Age", "3600");
		    response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		    logger.debug("response header names are :: {} " , response.getHeaderNames());
		    chain.doFilter(request, response);
		logger.debug("Exiting from doFilter method");
		
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
