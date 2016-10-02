
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
//@JsonInclude(Include.NON_NULL)
public class SecurityQuestions implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1221625005481656931L;
	
	@JsonProperty("question1")
	@NotNull(message="error.question1.notnull")
    private String question1;
    @JsonProperty("answer1")
    @NotNull(message="error.answer1.notnull")
    private String answer1;
    
    @JsonProperty("question2")
    private String question2;
    @JsonProperty("answer2")
    private String answer2;

    /**
     * 
     * @return
     *     The question1
     */
    @JsonProperty("question1")
    public String getQuestion1() {
        return question1;
    }

    /**
     * 
     * @param question1
     *     The question1
     */
    @JsonProperty("question1")
    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    /**
     * 
     * @return
     *     The answer1
     */
    @JsonProperty("answer1")
    public String getAnswer1() {
        return answer1;
    }

    /**
     * 
     * @param answer1
     *     The answer1
     */
    @JsonProperty("answer1")
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    /**
     * 
     * @return
     *     The question2
     */
    @JsonProperty("question2")
    public String getQuestion2() {
        return question2;
    }

    /**
     * 
     * @param question2
     *     The question2
     */
    @JsonProperty("question2")
    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    /**
     * 
     * @return
     *     The answer2
     */
    @JsonProperty("answer2")
    public String getAnswer2() {
        return answer2;
    }

    /**
     * 
     * @param answer2
     *     The answer2
     */
    @JsonProperty("answer2")
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    @JsonProperty("customer_id")
	private Integer customerId;

	@JsonProperty("customer_id")
	public Integer getCustomerId() {
		return customerId;
	}

	@JsonProperty("customer_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
