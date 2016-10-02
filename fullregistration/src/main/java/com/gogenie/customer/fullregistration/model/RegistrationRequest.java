
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

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
	
	@JsonProperty("firstname")
	@NotNull(message="error.firstname.notnull")
    private String firstname;
	
	@JsonProperty("lastname")
	@NotNull(message="error.lastname.notnull")
    private String lastname;
	
    @JsonProperty("dateofbirth")
    private Date dateofbirth;
    @JsonProperty("email")
    @NotNull(message="error.email.notnull")
    @Email(message="error.email.invalid")
    private String email;
    
    @JsonProperty("password")
    @NotNull(message="error.password.notnull")
    private String password;

    private String encryptedPassword;

	@JsonProperty("mobilephone")
	@NotNull(message="error.mobilephone.notnull")
	private String mobilephone;
	
	@JsonProperty("workphone")
	@NotNull(message="error.workphone.notnull")
	private String workphone;
	
	@JsonProperty("phoneValidationFlag")
	@NotNull(message="error.phoneValidationFlag.notnull")
	private String phoneValidationFlag;
	
	@JsonProperty("machinfo")
	private String machinfo;

    @JsonProperty("SecurityQuestions")
    @NotNull(message="error.securityQuestions.notnull")
    SecurityQuestions securityQuestions;
    
    @JsonProperty("Address")
    private Address address;
    
	@JsonProperty("CardInformation")
    private CardInformation cardInformation;

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
    public SecurityQuestions getSecurityQuestions() {
        return securityQuestions;
    }

    /**
     * 
     * @param SecurityQuestions
     *     The SecurityQuestions
     */
    @JsonProperty("SecurityQuestions")
    public void setSecurityQuestions(SecurityQuestions SecurityQuestions) {
        this.securityQuestions = SecurityQuestions;
    }

    /**
     * 
     * @return
     *     The Address
     */
    @JsonProperty("Address")
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param Address
     *     The Address
     */
    @JsonProperty("Address")
    public void setAddress(Address address) {
        this.address = address;
    }

    
    /**
     * 
     * @return
     *     The CardInformation
     */
    @JsonProperty("CardInformation")
    public CardInformation getCardInformation() {
        return cardInformation;
    }

    /**
     * 
     * @param CardInformation
     *     The CardInformation
     */
    @JsonProperty("CardInformation")
    public void setCardInformation(CardInformation cardInformation) {
        this.cardInformation = cardInformation;
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
