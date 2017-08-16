package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSet",
        "columnsOptions",
        "joins"
})
public class Join implements Serializable{

    @JsonProperty("dataSet")
    private String dataSetName;
    @JsonProperty("columnOptions")
    private JoinColumns columnOptions;
    @JsonProperty("joins")
    private List<Join> joins;

    @JsonProperty("dataSet")
    public String getDataSetName() {
        return dataSetName;
    }

    @JsonProperty("dataSet")
    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    @JsonProperty("columnOptions")
    public JoinColumns getColumnOptions() {
        return columnOptions;
    }

    @JsonProperty("columnOptions")
    public void setColumnOptions(JoinColumns columnOptions) {
        this.columnOptions = columnOptions;
    }

    @JsonProperty("joins")
    public List<Join> getJoins() {
        return joins;
    }

    @JsonProperty("joins")
    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }
}
