package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "metrics",
        "data"
})
public class SessionResult extends SessionResponse
{

    /**
     * For impact sessions, an object containing overall metrics about the impact
     *
     */
    @JsonProperty("metrics")
    @JsonPropertyDescription("For impact sessions, an object containing overall metrics about the impact")
    private Metrics metrics;
    /**
     * Continuous results from all forecast sessions executed on the dataset
     * (Required)
     *
     */
    @JsonProperty("data")
    @JsonPropertyDescription("Continuous results from all forecast sessions executed on the dataset")
    private List<Map<String, String>> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5482780237822916682L;

    /**
     * For impact sessions, an object containing overall metrics about the impact
     * @return A {@link Metrics Metrics } object
     */
    @JsonProperty("metrics")
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * For impact sessions, an object containing overall metrics about the impact
     */
    @JsonProperty("metrics")
    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * Continuous results from all forecast sessions executed on the dataset
     * (Required)
     *
     */
    @JsonProperty("data")
    public List<Map<String, String>> getData() {
        return data;
    }

    /**
     * Continuous results from all forecast sessions executed on the dataset
     * (Required)
     *
     */
    @JsonProperty("data")
    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(metrics).append(data).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SessionResult) == false) {
            return false;
        }
        SessionResult rhs = ((SessionResult) other);
        return new EqualsBuilder().append(metrics, rhs.metrics).append(data, rhs.data).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}