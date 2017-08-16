package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColumnOptions {
    @JsonProperty("alias")
    private String alias;

    @JsonProperty("joinInterval")
    private ResultInterval joinInterval;

    @JsonProperty("alias")
    public void setAlias(String alias){
        this.alias = alias;
    }

    @JsonProperty("alias")
    public String getAlias(){
        return this.alias;
    }

    @JsonProperty("joinInterval")
    public void setJoinInterval(ResultInterval interval){
        this.joinInterval = interval;
    }

    @JsonProperty("joinInterval")
    public ResultInterval getJoinInterval(){
        return this.joinInterval;
    }
}
