package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomerDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454622163358012013L;

	@JsonProperty("cust_id")
	private Integer customerId;
	@JsonProperty("firstname")
	private String firstname;
	@JsonProperty("lastname")
	private String lastname;
	@JsonProperty("dateofbirth")
	private Date dateofbirth;
	@JsonProperty("password")
	private String password;
	@JsonProperty("email")
	private String email;
	@JsonProperty("mobilephone")
	private String mobilephone;
	@JsonProperty("workphone")
	private String workphone;
	@JsonProperty("phoneValidationFlag")
	private String phoneValidationFlag;
	@JsonProperty("cust_isactive")
	private String custIsActive;
	@JsonProperty("machinfo")
	private String machinfo;
	@JsonProperty("SecurityQuestions")
	private SecurityQuestions securityQuestions;
	@JsonProperty("Address")
	private List<Address> address = new ArrayList<Address>();
	@JsonProperty("Cardinformation")
	private List<CardInformation> cardinformation = new ArrayList<CardInformation>();
	@JsonProperty("DeviceInfo")
	private DeviceInfo deviceInfo;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	@JsonProperty("cust_id")
	public Integer getCustomerId() {
		return customerId;
	}

	@JsonProperty("cust_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * 
	 * @return The firstname
	 */
	@JsonProperty("firstname")
	public String getFirstname() {
		return firstname;
	}

	/**
	 * 
	 * @param firstname
	 *            The firstname
	 */
	@JsonProperty("firstname")
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * 
	 * @return The lastname
	 */
	@JsonProperty("lastname")
	public String getLastname() {
		return lastname;
	}

	/**
	 * 
	 * @param lastname
	 *            The lastname
	 */
	@JsonProperty("lastname")
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * 
	 * @return The dateofbirth
	 */
	@JsonProperty("dateofbirth")
	public Date getDateofbirth() {
		return dateofbirth;
	}

	/**
	 * 
	 * @param dateofbirth
	 *            The dateofbirth
	 */
	@JsonProperty("dateofbirth")
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	/**
	 * 
	 * @return The password
	 */
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 *            The password
	 */
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return The email
	 */
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 *            The email
	 */
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return The mobilephone
	 */
	@JsonProperty("mobilephone")
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * 
	 * @param mobilephone
	 *            The mobilephone
	 */
	@JsonProperty("mobilephone")
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	/**
	 * 
	 * @return The workphone
	 */
	@JsonProperty("workphone")
	public String getWorkphone() {
		return workphone;
	}

	/**
	 * 
	 * @param workphone
	 *            The workphone
	 */
	@JsonProperty("workphone")
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	/**
	 * 
	 * @return The phoneValidationFlag
	 */
	@JsonProperty("phoneValidationFlag")
	public String getPhoneValidationFlag() {
		return phoneValidationFlag;
	}

	/**
	 * 
	 * @param phoneValidationFlag
	 *            The phoneValidationFlag
	 */
	@JsonProperty("phoneValidationFlag")
	public void setPhoneValidationFlag(String phoneValidationFlag) {
		this.phoneValidationFlag = phoneValidationFlag;
	}

	@JsonProperty("cust_isactive")
	public String getCustIsActive() {
		return custIsActive;
	}

	@JsonProperty("cust_isactive")
	public void setCustIsActive(String custIsActive) {
		this.custIsActive = custIsActive;
	}

	/**
	 * 
	 * @return The machinfo
	 */
	@JsonProperty("machinfo")
	public String getMachinfo() {
		return machinfo;
	}

	/**
	 * 
	 * @param machinfo
	 *            The machinfo
	 */
	@JsonProperty("machinfo")
	public void setMachinfo(String machinfo) {
		this.machinfo = machinfo;
	}

	/**
	 * 
	 * @return The securityQuestions
	 */
	@JsonProperty("SecurityQuestions")
	public SecurityQuestions getSecurityQuestions() {
		return securityQuestions;
	}

	/**
	 * 
	 * @param securityQuestions
	 *            The SecurityQuestions
	 */
	@JsonProperty("SecurityQuestions")
	public void setSecurityQuestions(SecurityQuestions securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	/**
	 * 
	 * @return The address
	 */
	@JsonProperty("Address")
	public List<Address> getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 *            The Address
	 */
	@JsonProperty("Address")
	public void setAddress(List<Address> address) {
		this.address = address;
	}

	/**
	 * 
	 * @return The cardinformation
	 */
	@JsonProperty("Cardinformation")
	public List<CardInformation> getCardinformation() {
		return cardinformation;
	}

	/**
	 * 
	 * @param cardinformation
	 *            The Cardinformation
	 */
	@JsonProperty("Cardinformation")
	public void setCardinformation(List<CardInformation> cardinformation) {
		this.cardinformation = cardinformation;
	}

	/**
	 * 
	 * @return The deviceInfo
	 */
	@JsonProperty("DeviceInfo")
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * 
	 * @param deviceInfo
	 *            The DeviceInfo
	 */
	@JsonProperty("DeviceInfo")
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}