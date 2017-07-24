package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSetName",
        "bucket",
        "path",
        "region",
        "columns"
})

public class ImportData implements Serializable {

    /**
     * Name of the dataset to create from S3 file
     */
    @JsonProperty("dataSetName")
    private String dataSetName;

    /**
     * Name of the S3 bucket to retrieve from
     */
    @JsonProperty("bucket")
    private String bucket;

    /**
     * THe path or filename
     */
    @JsonProperty("path")
    private String path;

    /**
     * The AWS region where the bucket exists
     */
    @JsonProperty("region")
    private String region;

    /**
     * The column metadata to associate with the dataset
     */
    @JsonProperty("columns")
    private Columns columns;

    @JsonProperty("dataSetName")
    public String getDataSetName() {
        return dataSetName;
    }

    @JsonProperty("dataSetName")
    public void setDataSetName(String value) {
        dataSetName = value;
    }

    @JsonProperty("bucket")
    public String getBucket() {
        return bucket;
    }

    @JsonProperty("bucket")
    public void setBucket(String value) {
        bucket = value;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String value) {
        path = value;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String value) {
        region = value;
    }

    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    @JsonProperty("columns")
    public void setColumns(Columns value) {
        columns = value;
    }
}
