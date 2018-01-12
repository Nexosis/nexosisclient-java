package com.nexosis.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Query criteria to be used when retrieving DataSets
 */
public class DataSetSummaryQuery {
    private String partialName;
    private PagingInfo page;

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

    public Map<String,Object> toParameters() {
        Map<String,Object> parameters = new HashMap<>();

        if (page != null) {
            parameters.putAll(page.toParameters());
        }

        if (!StringUtils.isEmpty(partialName)) {
            parameters.put("partialName", this.partialName);
        }

        return parameters;
    }
}
