package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSetName",
        "targetColumn",
        "winningAlgorithm"
})
// Item in json
public class ForecastModel implements Serializable
{

    @JsonProperty("dataSetName")
    private String dataSetName;
    @JsonProperty("targetColumn")
    private String targetColumn;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3223628774190626271L;

    @JsonProperty("dataSetName")
    public String getDataSetName() {
        return dataSetName;
    }

    @JsonProperty("dataSetName")
    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    @JsonProperty("targetColumn")
    public String getTargetColumn() {
        return targetColumn;
    }

    @JsonProperty("targetColumn")
    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
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
        return new HashCodeBuilder().append(dataSetName).append(targetColumn).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ForecastModel) == false) {
            return false;
        }
        ForecastModel rhs = ((ForecastModel) other);
        return new EqualsBuilder().append(dataSetName, rhs.dataSetName).append(targetColumn, rhs.targetColumn).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}