package com.nexosis.model;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class ViewDataQuery {
    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private Iterable<String> includedColumns;
    private PagingInfo page;

    public ViewDataQuery(String name)
    {
        this.setName(name);
    }

    /**
     * The name of the View to retrieve
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Limits view data rows to those on or after the specified date
     */
    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Limits view data rows to those on or before the specified date
     */
    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Includes only the specified columns in view data rows
     */
    public Iterable<String> getInclude() {
        return includedColumns;
    }

    public void setIncludedColumns(Iterable<String> include) {
        this.includedColumns = include;
    }

    /**
     * The paging info for the response
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

        if (startDate != null) {
            parameters.put("startDate",startDate);
        }

        if (endDate != null) {
            parameters.put("endDate",endDate);
        }

        if (includedColumns != null) {
            parameters.put("include", includedColumns);
        }

        return parameters;
    }
}
