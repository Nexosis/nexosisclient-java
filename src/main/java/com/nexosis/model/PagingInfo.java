package com.nexosis.model;

import java.util.HashMap;
import java.util.Map;

public class PagingInfo {
    private Integer pageNumber;
    private Integer pageSize;
    public static PagingInfo Default = new PagingInfo(null, 50);

    public PagingInfo() {
        this.pageNumber = null;
        this.pageSize = null;
    }

    public PagingInfo(Integer pageNumber, Integer pageSize)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * Get PageNumber
     *
     * @return The page number to be retrieved
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Set PageNumber
     *
     * @param pageNumber The page number to be retrieved
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    /**
     * Get PageSize
     *
     * @return The size of the page to be returned
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Set PageSize
     *
     * @param pageSize  The size of the page to be returned
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * @return A Map containing the paramters to be used in the querystring
     */
    public Map<String,Object> toParameters() {
        Map<String,Object> parameters = new HashMap<>();

        if (this.pageNumber != null) {
            parameters.put("page", this.getPageNumber());
        }

        if (this.pageSize != null) {
            parameters.put("pageSize", this.getPageSize());
        } else {
            parameters.put("pageSize", this.Default.getPageSize());
        }

        return parameters;
    }
}
