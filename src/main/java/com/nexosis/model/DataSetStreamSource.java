package com.nexosis.model;

import com.google.api.client.json.Json;

import java.io.InputStream;

public class DataSetStreamSource implements IDataSetSource {
    private InputStream streamReader;
    private String name;
    private String contentType;

    public DataSetStreamSource(String name, InputStream streamReader)
    {
        this.streamReader = streamReader;
        this.name = name;
        this.contentType = "text/csv"; //default
    }

    /**
     *
     * @return The data
     */
    public InputStream getData() {
        return streamReader;
    }

    /**
     *
     * @return The DataSet name
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return
     */
    public String getContentType() {
        return contentType;
    }

    /**
     *
     * @param contentType
     */
    public void setContentType(String contentType) throws IllegalArgumentException {
        if (!contentType.equalsIgnoreCase("text/csv") && !contentType.startsWith("application/json")) {
            throw new IllegalArgumentException("contentType must be set to text/csv or application/json");
        }
        // force to use application/json w/ utf-8
        if (contentType.equalsIgnoreCase("application/json")) {
            contentType = Json.MEDIA_TYPE;
        }

        this.contentType = contentType;
    }
}
