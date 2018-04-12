package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataType",
        "role"
})
public class ColumnsProperty implements Serializable
{
    @JsonProperty("dataType")
    private DataType dataType;
    @JsonProperty("role")
    private DataRole dataRole;
    @JsonProperty("imputation")
    private ImputationStrategy imputation;
    @JsonProperty("aggregation")
    private AggregationStrategy aggregation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = -7704235352454871348L;

    @JsonProperty("dataType")
    public DataType getDataType() {
        return dataType;
    }

    @JsonProperty("dataType")
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @JsonProperty("role")
    public DataRole getRole() {
        return dataRole;
    }

    @JsonProperty("role")
    public void setRole(DataRole dataRole) {
        this.dataRole = dataRole;
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
        return new HashCodeBuilder().append(dataType).append(dataRole).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ColumnsProperty) == false) {
            return false;
        }
        ColumnsProperty rhs = ((ColumnsProperty) other);
        return new EqualsBuilder().append(dataType, rhs.dataType).append(dataRole, rhs.dataRole).append(imputation,rhs.imputation).append(aggregation,rhs.aggregation).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @JsonProperty("imputation")
    public ImputationStrategy getImputation() {
        return this.imputation;
    }
    @JsonProperty("imputation")
    public void setImputation(ImputationStrategy imputation) {
        this.imputation = imputation;
    }
    @JsonProperty("aggregation")
    public AggregationStrategy getAggregation() {
        return this.aggregation;
    }
    @JsonProperty("aggregation")
    public void setAggregation(AggregationStrategy aggregation) {
        this.aggregation = aggregation;
    }
}