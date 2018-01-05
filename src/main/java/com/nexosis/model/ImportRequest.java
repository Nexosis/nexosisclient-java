package com.nexosis.model;

public abstract class ImportRequest {
    private String dataSetName;
    private Columns columns;
    private ImportContentType contentType;

    /**
     * The name of the DataSet into which the data should be imported
     */
    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    /**
     * Describes the columns that are in the data to be imported
     */
    public Columns getColumns() {
        return columns;
    }

    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    /**
     * The format of the data to be imported.  Optional.
     * If not provided, Nexosis will attempt to examine the file and determine the type automatically
     */
    public ImportContentType getContentType() {
        return contentType;
    }

    public void setContentType(ImportContentType contentType) {
        this.contentType = contentType;
    }
}
