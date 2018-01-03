package com.nexosis.model;

import com.nexosis.impl.NexosisClientException;

import java.io.InputStream;
import java.security.InvalidParameterException;

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
        if (!contentType.equalsIgnoreCase("text/csv") && contentType.equalsIgnoreCase("application/json")) {
            throw new IllegalArgumentException("contentType must be set to text/csv or application/json");
        }
        this.contentType = contentType;
    }
}
