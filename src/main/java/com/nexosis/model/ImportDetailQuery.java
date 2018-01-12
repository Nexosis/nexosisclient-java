package com.nexosis.model;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class ImportDetailQuery {

    private String dataSetName;
    private DateTime requestedBeforeDate;
    private DateTime requestedAfterDate;
    private PagingInfo page;

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

        return parameters;
    }
}
