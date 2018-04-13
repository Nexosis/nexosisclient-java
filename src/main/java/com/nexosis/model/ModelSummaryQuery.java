package com.nexosis.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelSummaryQuery {

    private String dataSourceName;
    private DateTime createdAfterDate;
    private DateTime createdBeforeDate;
    private PagingInfo page;
    private String sortBy;
    private SortOrder sortOrder = SortOrder.ASC;

    /**
     * Limits models to those for a particular data source
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * Limits models to those created after this date
     */
    public DateTime getCreatedAfterDate() {
        return createdAfterDate;
    }

    public void setCreatedAfterDate(DateTime createdAfterDate) {
        this.createdAfterDate = createdAfterDate;
    }

    /**
     * Limits models to those created before this date
     */
    public DateTime getCreatedBeforeDate() {
        return createdBeforeDate;
    }

    public void setCreatedBeforeDate(DateTime createdBeforeDate) {
        this.createdBeforeDate = createdBeforeDate;
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

    private List<String> validSortBy = Arrays.asList("id", "modelname", "datasourcename", "type", "createddate", "lastuseddate");

    /**
     *
     * @param sortBy - valid values are id, modelName, dataSourceName, type, createdDate, and lastUsedDate
     */
    public void setSortBy(String sortBy){
        if(sortBy != null && validSortBy.contains(sortBy.toLowerCase()))
            this.sortBy = sortBy;
        else
            throw new IllegalArgumentException("The valid values for model sortBy are id, modelName, dataSourceName, type, createdDate, and lastUsedDate");
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

    /**
     * Converts the ModelClientParam object to a Map used to call the Nexosis API.
     *
     * @return A Map parameters used to call the API that specify valid results.
     */
    public Map<String,Object> toParameters() throws IllegalArgumentException {
        Map<String, Object> parameters = new HashMap<>();

        if (page != null) {
            parameters.putAll(page.toParameters());
        }

        if (!StringUtils.isEmpty(this.dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }

        if (null != createdAfterDate) {
            parameters.put("createdAfterDate", createdAfterDate.toDateTimeISO().toString());
        }

        if (null != createdBeforeDate) {
            parameters.put("createdBeforeDate", createdBeforeDate.toDateTimeISO().toString());
        }
        if (sortBy != null){
            parameters.put("sortBy", sortBy);
            parameters.put("sortOrder", sortOrder.toString());
        }
        return parameters;
    }
}
