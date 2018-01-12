package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewInfo extends Resource {
    @JsonProperty("dataSetName")
    private String dataSetName;
    @JsonProperty("columns")
    private Columns columns;
    @JsonProperty("joins")
    private List<Join> joins;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -669273973972140997L;

    @JsonProperty("dataSetName")
    public String getDataSetName() {
        return this.dataSetName;
    }

    @JsonProperty("dataSetName")
    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    @JsonProperty("columns")
    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    @JsonProperty("joins")
    public List<Join> getJoins(){return this.joins;}

    @JsonProperty("joins")
    public void setJoins(List<Join> joins){this.joins = joins;}

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
        return new HashCodeBuilder().append(columns).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ViewDefinition) == false) {
            return false;
        }
        ViewInfo rhs = ((ViewDefinition) other);
        return new EqualsBuilder().append(dataSetName, rhs.dataSetName).append(columns, rhs.columns).append(joins, rhs.joins).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}
