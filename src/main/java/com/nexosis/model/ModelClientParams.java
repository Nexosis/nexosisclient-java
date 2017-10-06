package com.nexosis.model;
import com.nexosis.impl.NexosisClient;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class ModelClientParams {
    private String dataSourceName;
    private int page;
    private int pageSize;
    private DateTime createdAfterDate;
    private DateTime createdBeforeDate;

    public ModelClientParams(){
        page = 0;
        pageSize = NexosisClient.getDefaultPageSize();
    }
    /**
     * @return A string of the dataSourceName that is used to limit models to those for a particular data source.
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * @param dataSourceName Limits models to those for a particular data source.
     */
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * @return A Zero index page of the Model results to retrieve
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page A Zero index page of the Model results to retrieve
     */
    public void setPage(int page) {
        this.page = page;
    }


    /**
     * @return The number of model results to return per page
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize The number of model results to return per page
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return Limits models to those requested on or after the specified date.
     */
    public DateTime getCreatedAfterDate() {
        return createdAfterDate;
    }

    /**
     * @param createdAfterDate Limits models to those requested on or after the specified date.
     */
    public void setCreatedAfterDate(DateTime createdAfterDate) {
        this.createdAfterDate = createdAfterDate;
    }

    /**
     * @return Limits models to those requested on or before the specified date.
     */
    public DateTime getCreatedBeforeDate() {
        return createdBeforeDate;
    }

    /**
     * @param createdBeforeDate  Limits models to those requested on or before the specified date.
     */
    public void setCreatedBeforeDate(DateTime createdBeforeDate) {
        this.createdBeforeDate = createdBeforeDate;
    }

    /**
     * Converts the ModelClientParam object to a Map used to call the Nexosis API.
     *
     * @return A Map parameters used to call the API that specify valid results.
     */
    public Map<String,Object> buildParameter() throws IllegalArgumentException {
        Map<String, Object> parameters = new HashMap<>();
        if (!StringUtils.isEmpty(this.dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }

        if (null != createdAfterDate) {
            parameters.put("createdAfterDate", createdAfterDate.toDateTimeISO().toString());
        }

        if (null != createdBeforeDate) {
            parameters.put("createdBeforeDate", createdBeforeDate.toDateTimeISO().toString());
        }

        if (page < 0) {
            throw new IllegalArgumentException("Page parameter of ModelClientParams must be zero or greater.");
        }
        // Always put the page number
        parameters.put("page", page);

        if (pageSize < 0 || pageSize > NexosisClient.getMaxPageSize()) {
            throw new IllegalArgumentException("PageSize parameter of ModelClientParams must be between 0 and 1000.");
        } else if (pageSize != NexosisClient.getDefaultPageSize()) {
            parameters.put("pageSize", pageSize);
        }

        return parameters;
    }
}
