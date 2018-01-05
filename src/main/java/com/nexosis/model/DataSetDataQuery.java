package com.nexosis.model;

import com.google.api.client.json.Json;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/// Query critera for retrieving data from a dataset.  Use the DataSet.Data method to create me
public class DataSetDataQuery {
    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private Iterable<String> includedColumns;
    private PagingInfo page;
    private String contentType;

    public DataSetDataQuery() {
        this.setName(null);
        this.contentType = Json.MEDIA_TYPE; //default
    }

    public DataSetDataQuery(String name) {
        this.setName(name);
        this.contentType = Json.MEDIA_TYPE; //default
    }

    /**
    *  Get Dataset Name
    *
    *  @return  The name of the DataSet whose data should be retrieved
    */
    public String getName() {
        return name;
    }

    /**
     *  Set DataSet Name.
     *
     *  @param name The name of the DataSet whose data should be retrieved
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  Get StartDate
     *
     * @return  Data after this date should be returned (Time Series DataSets only)
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * Set StartDate
     *
     * @param startDate Data after this date should be returned (Time Series DataSets only)
     */
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Get EndDate
     *
     * @return Data before this date should be returned (Time Series DataSets only)
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * Set EndDate
     *
     * @param endDate Data before this date should be returned (Time Series DataSets only)
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Get Columns
     *
     * @return Columns from the DataSet to be returned
     */
    public Iterable<String> getIncludedColumns() {
        return includedColumns;
    }

    /**
     * Set Columns
     *
     * @param includedColumns Columns from the DataSet to be returned
     */
    public void setIncludedColumns(Iterable<String> includedColumns) {
        this.includedColumns = includedColumns;
    }

    /**
     * Get Page
     *
     * @return Paging information for the response
     */
    public PagingInfo getPage() {
        return page;
    }

    /**
     *  Set Page
     *
     * @param page Paging information for the response
     */
    public void setPage(PagingInfo page) {
        this.page = page;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        if (!contentType.equalsIgnoreCase("text/csv") && contentType.equalsIgnoreCase("application/json")) {
            throw new IllegalArgumentException("contentType must be set to text/csv or application/json");
        }
        this.contentType = contentType;
    }

    /**
     * @return a Map containing the parameters to be put in the HTTP Query string of the API
     */
    public Map<String,Object> toParameters() {
        Map<String,Object> parameters = new HashMap<>();

        if (page != null) {
            parameters.putAll(this.page.toParameters());
        }

        if (includedColumns != null) {
            parameters.put("include", includedColumns);
        }

        if (startDate != null) {
            parameters.put("startDate", startDate.toDateTimeISO().toString());
        }

        if (endDate != null) {
            parameters.put("endDate", endDate.toDateTimeISO().toString());
        }

        return parameters;
    }


}
