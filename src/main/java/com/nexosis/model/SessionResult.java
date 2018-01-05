package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "metrics",
        "data"
})
public class SessionResult extends SessionResponse
{

    /**
     * For sessions, an object containing overall metrics about the algorithms that ran.
     *
     */
    @JsonProperty("metrics")
    @JsonPropertyDescription("For impact sessions, an object containing overall metrics about the impact")
    private Map<String, String> metrics;

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
     *  For sessions, an object containing overall metrics about the algorithms that ran.
     * @return A Map containing metrics for this algorithm such as MAE, MAPE, MASE, RMSE. For Impact additionally pValue, absolute and relative effect.
     */
    @JsonProperty("metrics")
    public Map<String, String> getMetrics() {
        return metrics;
    }

    /**
     * For impact sessions, an object containing overall metrics about the impact
     */
    @JsonProperty("metrics")
    public void setMetrics(Map<String, String> metrics) {
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