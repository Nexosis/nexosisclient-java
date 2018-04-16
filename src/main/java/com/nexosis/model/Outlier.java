package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class Outlier {

    @JsonProperty("timeStamp")
    DateTime timeStamp;

    double actual;
    double smooth;

    @JsonProperty("timeStamp")
    public void setTimeStamp(DateTime value){
        this.timeStamp = value;
    }

    @JsonProperty("timeStamp")
    public DateTime getTimeStamp(){
        return this.timeStamp;
    }

    @JsonIgnore
    public double getActual(){
        return this.actual;
    }

    @JsonIgnore
    public double getSmooth(){
        return this.smooth;
    }

    @JsonAnySetter
    public void setValues(String name, String value){
        double doubleValue = Double.parseDouble(value);
        if(name.endsWith(":actual"))
            actual = doubleValue;
        else
            smooth = doubleValue;
    }
}
