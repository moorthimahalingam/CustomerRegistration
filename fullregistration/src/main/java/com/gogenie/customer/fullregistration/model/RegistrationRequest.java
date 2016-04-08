
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class RegistrationRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6745458098444463847L;
	
	@JsonProperty("firstname")
    private String firstname;
    @JsonProperty("middle")
    private String middle;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("SecurityQuestions")
    private com.gogenie.customer.fullregistration.model.SecurityQuestions SecurityQuestions;
    @JsonProperty("Address")
    private com.gogenie.customer.fullregistration.model.Address Address;
    @JsonProperty("CardInformation")
    private com.gogenie.customer.fullregistration.model.CardInformation CardInformation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    /**
     * 
     * @return
     *     The middle
     */
    @JsonProperty("middle")
    public String getMiddle() {
        return middle;
    }

    /**
     * 
     * @param middle
     *     The middle
     */
    @JsonProperty("middle")
    public void setMiddle(String middle) {
        this.middle = middle;
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

    /**
     * 
     * @return
     *     The phone
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
