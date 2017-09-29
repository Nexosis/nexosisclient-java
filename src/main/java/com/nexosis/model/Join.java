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
    @JsonProperty("calendar")
    private CalendarJoinSource calendar;

    public Join(){

    }

    @JsonProperty("dataSet")
    public DataSetJoinSource getDataSet() {
        return dataSet;
    }

    /**
     * The dataset to join to if any. exclusive of calendar.
     * @param dataSet
     */
    @JsonProperty("dataSet")
    public void setDataSet(DataSetJoinSource dataSet) {
        //if(this.calendar != null)
        //    throw new InvalidParameterException("Set only the dataset or the calendar, not both.");
        this.dataSet = dataSet;
    }

    @JsonIgnore
    public String getDataSetName(){
        if(getDataSet() == null)
            return null;
        return getDataSet().getName();
    }

    @JsonIgnore
    public void setDataSetName(String dataSetName){
        this.setDataSet(new DataSetJoinSource());
        getDataSet().setName(dataSetName);
    }

    @JsonProperty("calendar")
    public CalendarJoinSource getCalendar() {
        return calendar;
    }

    /**
     * Calendar identifier for join if any. exclusive of dataSet.
     * @param calendar
     */
    @JsonProperty("calendar")
    public void setCalendar(CalendarJoinSource calendar) {
        //if(this.dataSet != null)
        //    throw new InvalidParameterException("Set only the dataset or the calendar, not both.");
        this.calendar = calendar;
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
