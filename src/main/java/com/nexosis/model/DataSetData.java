package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSetName",
        "isTimeSeries",
        "dataSetSize",
        "columns",
        "data",
})
public class DataSetData extends Paged implements Serializable
{
    public DataSetData() {
        columns = new Columns();
        data = new ArrayList<>();
    }

    @JsonProperty("columns")
    private Columns columns;

    @JsonProperty("data")
    private List<Map<String, String>> data = null;

    @JsonProperty("dataSetName")
    private String dataSetName;

    @JsonProperty("isTimeSeries")
    private String isTimeSeries;

    @JsonProperty("dataSetSize")
    private String dataSetSize;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7159522227188063616L;

    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getIsTimeSeries() {
        return isTimeSeries;
    }

    public void setIsTimeSeries(String isTimeSeries) {
        this.isTimeSeries = isTimeSeries;
    }

    public String getDataSetSize() {
        return dataSetSize;
    }

    public void setDataSetSize(String dataSetSize) {
        this.dataSetSize = dataSetSize;
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
        return new HashCodeBuilder().append(dataSetName).append(columns).append(data).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataSetData) == false) {
            return false;
        }
        DataSetData rhs = ((DataSetData) other);
        return new EqualsBuilder().append(dataSetName, rhs.dataSetName).append(columns, rhs.columns).append(data, rhs.data).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}