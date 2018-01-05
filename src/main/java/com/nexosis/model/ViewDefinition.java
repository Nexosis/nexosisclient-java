package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "viewName",
        "dataSetName",
        "columns",
        "joins",
        "links"
})
public class ViewDefinition extends ViewInfo implements Serializable {

    @JsonProperty("viewName")
    private String viewName;

    @JsonProperty("viewName")
    public String getViewName(){return this.viewName;}

    @JsonProperty("viewName")
    public void setViewName(String viewName) {this.viewName = viewName;}
}
