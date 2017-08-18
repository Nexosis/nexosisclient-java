package com.nexosis.model;

import org.joda.time.DateTime;

public class ListQuery {

    int pageNumber = 0;
    int pageSize = 50;
    org.joda.time.DateTime startDate;
    org.joda.time.DateTime endDate;
    Iterable<String> includeColumns;

    public ListQuery(){

    }

    /**
     *
     * @param pageNumber - The page of results to return. Defaults to 0
     * @param pageSize - The number of results per page. Defaults to 50. Max 1000.
     * @param startDate - The begin of relevant dates in the results (varies by list)
     * @param endDate - The end of relevant dates in results (varies by list)
     */
    public ListQuery(int pageNumber, int pageSize, DateTime startDate, DateTime endDate){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     *
     * @return page of results
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     *
     * @param pageNumber
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * @return
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     */
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public Iterable<String> getIncludeColumns() {
        return includeColumns;
    }

    /**
     *
     * @param includeColumns
     */
    public void setIncludeColumns(Iterable<String> includeColumns) {
        this.includeColumns = includeColumns;
    }

}
