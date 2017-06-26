package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * For impact sessions, an object containing overall metrics about the impact
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
public class Metrics implements Serializable
{
    @JsonProperty("metrics")
    @JsonPropertyDescription("")
    private List<JobMetric> metrics = null;
    @JsonIgnore
    private Map<String, Double> additionalProperties = new HashMap<String, Double>();
    private final static long serialVersionUID = 5052251213315544773L;

    /**
     *
     * @return A List of JobMetric objects
     */
    @JsonProperty("metrics")
    public List<JobMetric> getMetrics() {
        return metrics;
    }

    /**
     *
     * @param metrics A List of JobMetrics objects
     */
    @JsonProperty("metrics")
    public void setMetrics(List<JobMetric> metrics) {
        this.metrics = metrics;
    }


    @JsonAnyGetter
    public Map<String, Double> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, double value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metrics) == false) {
            return false;
        }
        Metrics rhs = ((Metrics) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}