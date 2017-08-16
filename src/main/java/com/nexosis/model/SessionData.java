package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "dataSetName",
        "columns",
        "links"
})
public class SessionData implements Serializable
{
    public SessionData() {
        columns = new Columns();
    }

    @JsonProperty("columns")
    private Columns columns;
    @JsonProperty("dataSetName")
    private String dataSetName;
    @JsonProperty("dataSourceName")
    private String dataSourceName;
    @JsonProperty("links")
    private List<Link> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7159522227188063616L;

    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    @JsonProperty("columns")
    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    @JsonProperty("dataSetName")
    @Deprecated
    /**
     * @deprecated prefer {@link getDataSourceName()}
     */
    public String getDataSetName() {
        return dataSetName;
    }

    @JsonProperty("dataSetName")
    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    @JsonProperty("dataSourceName")
    public String getDataSourceName() {
        return dataSourceName;
    }
    @JsonProperty("dataSourceName")
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

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
        return new HashCodeBuilder().append(dataSetName).append(columns).append(links).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SessionData) == false) {
            return false;
        }
        SessionData rhs = ((SessionData) other);
        return new EqualsBuilder().append(dataSetName, rhs.dataSetName).append(columns, rhs.columns).append(links, rhs.links).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}