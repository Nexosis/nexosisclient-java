package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "viewName",
        "dataSetName",
        "columns",
        "joins",
        "links"
})
public class ViewDefinition  implements Serializable {
    @JsonProperty("viewName")
    private String viewName;
    @JsonProperty("dataSetName")
    private String dataSetName;
    @JsonProperty("columns")
    private Columns columns;
    @JsonProperty("joins")
    private List<Join> joins;
    @JsonProperty("links")
    private List<Link> links = null;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -669273973972140997L;

    @JsonProperty("viewName")
    public String getViewName(){return this.viewName;}

    @JsonProperty("viewName")
    public void setViewName(String viewName) {this.viewName = viewName;}

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

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
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
        return new HashCodeBuilder().append(viewName).append(columns).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ViewDefinition) == false) {
            return false;
        }
        ViewDefinition rhs = ((ViewDefinition) other);
        return new EqualsBuilder().append(viewName, rhs.viewName).append(dataSetName, rhs.dataSetName).append(columns, rhs.columns).append(joins, rhs.joins).append(links, rhs.links).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}
