package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "connectionString",
        "container",
        "blob"
})
public class ImportFromAzureRequest extends ImportRequest {
    @JsonProperty("connectionString")
    private String connectionString;
    @JsonProperty("container")
    private String container;
    @JsonProperty("blob")
    private String blob;

    /**
     * The Azure Storage connection string to use when connecting to Azure
     */
    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Name of the Azure blob container from which to import
    */
    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    /**
     * Name of the Azure blob (i.e. file) containing data to import
     * The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.
     */
    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }
}
