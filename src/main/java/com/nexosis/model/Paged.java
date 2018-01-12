package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pageNumber",
        "totalPages",
        "pageSize",
        "totalCount"
})
public class Paged extends Resource {
    @JsonProperty("pageNumber")
    private int pageNumber;
    @JsonProperty("totalPages")
    private int totalPages;
    @JsonProperty("pageSize")
    private int pageSize;
    @JsonProperty("totalCount")
    private int totalCount;

    /**
     * @return The current page number
     */
    @JsonProperty("pageNumber")
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber The current page number
     */
    @JsonProperty("pageNumber")
    public void setPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
    }

    /**
     * @return The total number of pages that could be returned
     */
    @JsonProperty("totalPages")
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages The total number of pages that could be returned
     */
    @JsonProperty("totalPages")
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }

    /**
     * @return The number of records in the page
     */
    @JsonProperty("pageSize")
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize The number of records in the page
     */
    @JsonProperty("pageSize")
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

    /**
     * @return The total count of records that could be returned
     */
    @JsonProperty("totalCount")
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount The total count of records that could be returned
     */
    @JsonProperty("totalCount")
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
}
