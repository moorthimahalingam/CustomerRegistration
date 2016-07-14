package com.gogenie.customer.fullregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class DeviceInfo {

	@JsonProperty("deviceInfo_Id")
	private Integer deviceInfoId;
	@JsonProperty("device_Id")
	private String deviceId;
	@JsonProperty("cust_id")
	private Integer custId;
	@JsonProperty("platform")
	private String platform;
	@JsonProperty("device_name")
	private String deviceName;
	@JsonProperty("device_model")
	private String deviceModel;
	@JsonProperty("device_version")
	private String deviceVersion;
	@JsonProperty("device_mfrte")
	private String deviceMfrte;
	@JsonProperty("carrier")
	private String carrier;
	@JsonProperty("language")
	private String language;
	@JsonProperty("imei")
	private String imei;
	@JsonProperty("machinfo")
	private String machInfo;
	
	@JsonProperty("deviceInfo_Id")
	public Integer getDeviceInfoId() {
		return deviceInfoId;
	}

	@JsonProperty("deviceInfo_Id")
	public void setDeviceInfoId(Integer deviceInfoId) {
		this.deviceInfoId = deviceInfoId;
	}
	
	@JsonProperty("device_Id")
	public String getDeviceId() {
		return deviceId;
	}
	
	@JsonProperty("device_Id")
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@JsonProperty("cust_id")
	public Integer getCustId() {
		return custId;
	}
	
	@JsonProperty("cust_id")
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	
	@JsonProperty("platform")
	public String getPlatform() {
		return platform;
	}
	
	@JsonProperty("platform")
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	@JsonProperty("device_name")
	public String getDeviceName() {
		return deviceName;
	}
	
	@JsonProperty("device_name")
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	@JsonProperty("device_model")
	public String getDeviceModel() {
		return deviceModel;
	}
	
	@JsonProperty("device_model")
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	
	@JsonProperty("device_version")
	public String getDeviceVersion() {
		return deviceVersion;
	}
	
	@JsonProperty("device_version")
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	
	@JsonProperty("device_mfrte")
	public String getDeviceMfrte() {
		return deviceMfrte;
	}

	@JsonProperty("device_mfrte")
	public void setDeviceMfrte(String deviceMfrte) {
		this.deviceMfrte = deviceMfrte;
	}

	@JsonProperty("carrier")
	public String getCarrier() {
		return carrier;
	}
	
	@JsonProperty("carrier")
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	@JsonProperty("language")
	public String getLanguage() {
		return language;
	}
	
	@JsonProperty("language")
	public void setLanguage(String language) {
		this.language = language;
	}
	
	@JsonProperty("imei")
	public String getImei() {
		return imei;
	}
	
	@JsonProperty("imei")
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	@JsonProperty("machinfo")
	public String getMachInfo() {
		return machInfo;
	}
	
	@JsonProperty("machinfo")
	public void setMachInfo(String machInfo) {
		this.machInfo = machInfo;
	}
	
}
