package com.nexosis.model;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionQuery {
    private String dataSourceName;
    private DateTime requestedAfterDate;
    private DateTime requestedBeforeDate;
    private String eventName;
    private PagingInfo page;
    private String sortBy;
    private SortOrder sortOrder = SortOrder.ASC;

    /**
     * Only sessions associated with this data source should be returned
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * Only sessions requested after this date should be returned
     */
    public DateTime getRequestedAfterDate() {
        return requestedAfterDate;
    }

    public void setRequestedAfterDate(DateTime requestedAfterDate) {
        this.requestedAfterDate = requestedAfterDate;
    }

    /**
     * Only sessions requested before this date should be returned
     */
    public DateTime getRequestedBeforeDate() {
        return requestedBeforeDate;
    }

    public void setRequestedBeforeDate(DateTime requestedBeforeDate) {
        this.requestedBeforeDate = requestedBeforeDate;
    }

    /**
     *  Paging info for the response
     */
    public PagingInfo getPage() {
        return page;
    }

    public void setPage(PagingInfo page) {
        this.page = page;
    }
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    private List<String> validSortBy = Arrays.asList("id", "name", "type", "status", "datasourcename", "requesteddate");

    /**
     *
     * @param sortBy - valid values are id, name, type, status, dataSourceName, and requestedDate
     */
    public void setSortBy(String sortBy){
        if(sortBy != null && validSortBy.contains(sortBy.toLowerCase()))
            this.sortBy = sortBy;
        else
            throw new IllegalArgumentException("The valid values for session sortBy are id, name, type, status, dataSourceName, and requestedDate");
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

    public Map<String, Object> toParameters() {
        Map<String, Object> parameters = new HashMap<>();

        if (dataSourceName != null) {
            parameters.put("dataSourceName", dataSourceName);
        }

        if (requestedAfterDate != null) {
            parameters.put("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString());
        }

        if (requestedBeforeDate != null) {
            parameters.put("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString());
        }
        if (eventName != null) {
            parameters.put("eventName",eventName);
        }

        if (page != null) {
            parameters.putAll(page.toParameters());
        }

        if (sortBy != null){
            parameters.put("sortBy", sortBy);
            parameters.put("sortOrder", sortOrder.toString());
        }
        return parameters;
    }


}
