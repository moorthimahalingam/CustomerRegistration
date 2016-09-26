
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
//@JsonInclude(Include.NON_NULL)
public class RegistrationRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6745458098444463847L;

	@JsonProperty("customer_id")
    private Integer customerId;

	@NotNull
	@JsonProperty("firstname")
    private String firstname;
	@JsonProperty("lastname")
    private String lastname;
    @JsonProperty("dateofbirth")
    private Date dateofbirth;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

//    @JsonProperty("encrypted_password")
    private String encryptedPassword;

	@JsonProperty("mobilephone")
    private String mobilephone;
	@JsonProperty("workphone")
    private String workphone;
	@JsonProperty("phoneValidationFlag")
    private String phoneValidationFlag;
	@JsonProperty("machinfo")
	private String machinfo;

    @JsonProperty("SecurityQuestions")
    SecurityQuestions SecurityQuestions;
    
    @JsonProperty("Address")
    @NotNull
    private Address Address;
    
	@JsonProperty("CardInformation")
	@NotNull
    private CardInformation CardInformation;

	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
    /**
     * 
     * @return
     *     The firstname
     */
    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    /**
     * 
     * @param firstname
     *     The firstname
     */
    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    
    @JsonProperty("dateofbirth")
    public Date getDateofbirth() {
		return dateofbirth;
	}

    @JsonProperty("dateofbirth")
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

    @JsonProperty("phoneValidationFlag")
    public String getPhoneValidationFlag() {
		return phoneValidationFlag;
	}

    @JsonProperty("phoneValidationFlag")
	public void setPhoneValidationFlag(String phoneValidationFlag) {
		this.phoneValidationFlag = phoneValidationFlag;
	}

	/**
     * 
     * @return
     *     The lastname
     */
    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    /**
     * 
     * @param lastname
     *     The lastname
     */
    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("mobilephone")
    public String getMobilephone() {
		return mobilephone;
	}

    @JsonProperty("mobilephone")
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

    @JsonProperty("workphone")
	public String getWorkphone() {
		return workphone;
	}

    @JsonProperty("workphone")
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

    @JsonProperty("machinfo")
	public String getMachinfo() {
		return machinfo;
	}

    @JsonProperty("machinfo")
    public void setMachinfo(String machinfo) {
    	this.machinfo = machinfo;
    }
    
    /**
     * 
     * @return
     *     The SecurityQuestions
     */
    @JsonProperty("SecurityQuestions")
    public com.gogenie.customer.fullregistration.model.SecurityQuestions getSecurityQuestions() {
        return SecurityQuestions;
    }

    /**
     * 
     * @param SecurityQuestions
     *     The SecurityQuestions
     */
    @JsonProperty("SecurityQuestions")
    public void setSecurityQuestions(com.gogenie.customer.fullregistration.model.SecurityQuestions SecurityQuestions) {
        this.SecurityQuestions = SecurityQuestions;
    }

    /**
     * 
     * @return
     *     The Address
     */
    @JsonProperty("Address")
    public com.gogenie.customer.fullregistration.model.Address getAddress() {
        return Address;
    }

    /**
     * 
     * @param Address
     *     The Address
     */
    @JsonProperty("Address")
    public void setAddress(com.gogenie.customer.fullregistration.model.Address Address) {
        this.Address = Address;
    }

    
    /**
     * 
     * @return
     *     The CardInformation
     */
    @JsonProperty("CardInformation")
    public com.gogenie.customer.fullregistration.model.CardInformation getCardInformation() {
        return CardInformation;
    }

    /**
     * 
     * @param CardInformation
     *     The CardInformation
     */
    @JsonProperty("CardInformation")
    public void setCardInformation(com.gogenie.customer.fullregistration.model.CardInformation CardInformation) {
        this.CardInformation = CardInformation;
    }

    @JsonProperty("password")
    public String getPassword() {
		return password;
	}

    @JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

    @JsonProperty("customer_id")
    public Integer getCustomerId() {
		return customerId;
	}

    @JsonProperty("customer_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

//    @JsonProperty("encrypted_password")
    public String getEncryptedPassword() {
		return encryptedPassword;
	}

//    @JsonProperty("encrypted_password")
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@JsonProperty("DeviceInfo")
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	@JsonProperty("DeviceInfo")
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
