
package com.gogenie.customer.fullregistration.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class SecurityQuestions implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1221625005481656931L;
	
	@JsonProperty("question1")
    private String question1;
    @JsonProperty("answer1")
    private String answer1;
    @JsonProperty("question2")
    private String question2;
    @JsonProperty("answer2")
    private String answer2;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
