package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private DataSetJoinSource dataSet;
    @JsonProperty("columnOptions")
    private JoinColumns columnOptions;
    @JsonProperty("joins")
    private List<Join> joins;

    public Join(){
        setDataSet(new DataSetJoinSource());
    }

    @JsonProperty("dataSet")
    public DataSetJoinSource getDataSet() {
        return dataSet;
    }

    @JsonProperty("dataSet")
    public void setDataSet(DataSetJoinSource dataSet) {
        this.dataSet = dataSet;
    }

    @JsonIgnore
    public String getDataSetName(){return getDataSet().getName();}

    @JsonIgnore
    public void setDataSetName(String dataSetName){getDataSet().setName(dataSetName);}

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
