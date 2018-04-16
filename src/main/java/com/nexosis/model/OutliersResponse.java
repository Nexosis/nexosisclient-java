package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutliersResponse extends SessionResponse {
    @JsonProperty("data")
    private Outlier[] outliers;

    @JsonProperty("data")
    public Outlier[] getOutliers(){
        return this.outliers;
    }

    @JsonProperty("data")
    public void setData(Outlier[] outliers){
        this.outliers = outliers;
    }
}