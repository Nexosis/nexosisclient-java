package com.nexosis.model;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportDetailQuery {

    private String dataSetName;
    private DateTime requestedBeforeDate;
    private DateTime requestedAfterDate;
    private PagingInfo page;
    private String sortBy;
    private SortOrder sortOrder = SortOrder.ASC;

    /**
     * Limit imports to those with the specified name
     */
    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    /**
     * Limit imports to those requested before this date
     */
    public DateTime getRequestedBeforeDate() {
        return requestedBeforeDate;
    }

    public void setRequestedBeforeDate(DateTime requestedBeforeDate) {
        this.requestedBeforeDate = requestedBeforeDate;
    }

    /**
     * Limit imports to those requested after this date
     */
    public DateTime getRequestedAfterDate() {
        return requestedAfterDate;
    }

    public void setRequestedAfterDate(DateTime requestedAfterDate) {
        this.requestedAfterDate = requestedAfterDate;
    }

    /**
     * Paging parameters for the response
     */
    public PagingInfo getPage() {
        return page;
    }

    public void setPage(PagingInfo page) {
        this.page = page;
    }


    private List<String> validSortBy = Arrays.asList("id", "datasetname", "requesteddate", "currentstatusdate");

    /**
     *
     * @param sortBy - valid values are id, dataSetName, requestedDate, and currentStatusDate
     */
    public void setSortBy(String sortBy){
        if(sortBy != null && validSortBy.contains(sortBy.toLowerCase()))
            this.sortBy = sortBy;
        else
            throw new IllegalArgumentException("The valid values for import sortBy are id, dataSetName, requestedDate, and currentStatusDate");
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
        Map<String, Object> parameters = new HashMap<>();
        if (this.page != null) {
            parameters.putAll(page.toParameters());
        }

        if (dataSetName != null && !dataSetName.isEmpty()) {
            parameters.put("dataSetName", dataSetName);
        }
        if (requestedAfterDate != null) {
            parameters.put("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString());
        }
        if (requestedBeforeDate != null) {
            parameters.put("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString());
        }
        if (sortBy != null){
            parameters.put("sortBy", sortBy);
            parameters.put("sortOrder", sortOrder.toString());
        }
        return parameters;
    }
}
