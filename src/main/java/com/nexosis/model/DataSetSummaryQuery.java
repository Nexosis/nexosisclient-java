package com.nexosis.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query criteria to be used when retrieving DataSets
 */
public class DataSetSummaryQuery {
    private String partialName;
    private PagingInfo page;
    private String sortBy;
    private SortOrder sortOrder = SortOrder.ASC;

    /**
     * Get partialname
     *
     * @return All DataSets whose name contains this string will be returned
     */
    public String getPartialName() {
        return partialName;
    }

    /**
     * Set partialName
     *
     * @param partialName All DataSets whose name contains this string will be returned
     */
    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    /**
     * Get page
     *
     * @return Paging information for the response
     */
    public PagingInfo getPage() {
        return page;
    }

    /**
     * Set page
     * @param page Paging information for the response
     */
    public void setPage(PagingInfo page) {
        this.page = page;
    }


    private List<String> validSortBy = Arrays.asList("datasetname", "datasetsize", "rowcount", "datecreated", "lastmodified");

    /**
     *
     * @param sortBy - valid values are dataSetName, dataSetSize, rowCount, dateCreated, and lastModified
     */
    public void setSortBy(String sortBy){
        if(sortBy != null && validSortBy.contains(sortBy.toLowerCase()))
            this.sortBy = sortBy;
        else
            throw new IllegalArgumentException("The valid values for dataset sortBy are dataSetName, dataSetSize, rowCount, dateCreated, and lastModified");
    }
    public String getSortBy(){
        return this.sortBy;
    }

    public void setSortOrder(SortOrder sortOrder){
        this.sortOrder = sortOrder;
    }

    public SortOrder getSortOrder(){
        return this.sortOrder;
    }

    public Map<String,Object> toParameters() {
        Map<String,Object> parameters = new HashMap<>();

        if (page != null) {
            parameters.putAll(page.toParameters());
        }

        if (!StringUtils.isEmpty(partialName)) {
            parameters.put("partialName", this.partialName);
        }
        if (sortBy != null){
            parameters.put("sortBy", sortBy);
            parameters.put("sortOrder", sortOrder.toString());
        }
        return parameters;
    }
}
