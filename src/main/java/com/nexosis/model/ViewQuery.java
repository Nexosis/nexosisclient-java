package com.nexosis.model;

import java.util.HashMap;
import java.util.Map;

public class ViewQuery {
    private String partialName;
    private String dataSetName;
    private PagingInfo page;

    /**
     *  Limits results to only those view definitions with names containing the specified value
     */
    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    /**
     * Limits results to only those view definitions that reference the specified dataset
     */
    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    /**
     * Paging info for the response
     */
    public PagingInfo getPage() {
        return page;
    }

    public void setPage(PagingInfo page) {
        this.page = page;
    }

    public Map<String,Object> toParameters() {
        Map<String, Object> parameters = new HashMap<>();

        if (page != null) {
            parameters.putAll(page.toParameters());
        }

        if (partialName != null) {
            parameters.put("partialName", partialName);
        }
        if (dataSetName != null) {
            parameters.put("dataSetName", dataSetName);
        }

        return parameters;
    }
}
