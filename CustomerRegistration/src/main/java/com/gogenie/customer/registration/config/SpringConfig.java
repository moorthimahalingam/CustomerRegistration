package com.gogenie.customer.registration.config;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({"com.gogenie.customer.registration, com.gogenie.customer.fullregistration"})
public class SpringConfig {

	@Bean(name="restTemplate")
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
	
	@Bean(name="gogenieDataSource")
	public DataSource gogenieDataSource() throws NamingException {
		JndiTemplate jndiTemplate = new JndiTemplate();
	    DataSource gogenieDataSource
	            = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/MyGogenieDB");
		return gogenieDataSource;
	}	
}
