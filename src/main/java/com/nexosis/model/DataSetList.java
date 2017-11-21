package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The data sets that the user has uploaded.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "items"
})
public class DataSetList implements Serializable
{
    /**
     * Summaries of the data sets that have been uploaded
     *
     */
    @JsonProperty("items")
    @JsonPropertyDescription("Summaries of the data sets that have been uploaded")
    private List<DataSetSummary> items = null;
    @JsonProperty("pageNumber")
    private int pageNumber = 0;
    @JsonProperty("totalPages")
    private int totalPages = 0;
    @JsonProperty("pageSize")
    private int pageSize = 0;
    @JsonProperty("totalCount")
    private int totalCount = 0;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -9201827645013946314L;

    /**
     * Summaries of the data sets that have been uploaded
     * @return Returns a List&lt;T&gt; of {@link DataSetSummary DataSetSummary} objects.
     */
    @JsonProperty("dataSets")
    public List<DataSetSummary> getItems() {
        return items;
    }

    /**
     * Summaries of the data sets that have been uploaded
     *
     */
    @JsonProperty("dataSets")
    public void setItems(List<DataSetSummary> items) {
        this.items = items;
    }

    @JsonProperty("pageNumber")
    public int getPageNumber() {
        return pageNumber;
    }

    @JsonProperty("pageNumber")
    public void setPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
    }

    @JsonProperty("totalPages")
    public int getTotalPages() {
        return totalPages;
    }

    @JsonProperty("totalPages")
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }

    @JsonProperty("pageSize")
    public int getPageSize() {
        return pageSize;
    }

    @JsonProperty("pageSize")
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

    @JsonProperty("totalCount")
    public int getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
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
        return new HashCodeBuilder().append(items).append(pageNumber).append(pageSize).append(totalPages).append(totalCount).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataSetList) == false) {
            return false;
        }
        DataSetList rhs = ((DataSetList) other);
        return new EqualsBuilder().append(items, rhs.items).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}