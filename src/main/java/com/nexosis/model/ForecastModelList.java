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
        "items"
})
public class ForecastModelList implements Serializable
{

    @JsonProperty("items")
    private List<ForecastModel> items = null;
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
    private final static long serialVersionUID = -5377694715504929649L;

    @JsonProperty("items")
    public List<ForecastModel> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<ForecastModel> items) {
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
        if ((other instanceof ForecastModelList) == false) {
            return false;
        }
        ForecastModelList rhs = ((ForecastModelList) other);
        return new EqualsBuilder().append(items, rhs.items).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}