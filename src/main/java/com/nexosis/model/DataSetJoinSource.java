package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSetJoinSource implements Serializable{

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    /**
     * @param Name of the dataset to join to
     */
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    private String name;

}
