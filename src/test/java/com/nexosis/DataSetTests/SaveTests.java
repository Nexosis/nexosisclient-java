package com.nexosis.DataSetTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.DataSetGenerator;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SaveTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectMapper mapper = new ObjectMapper();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void requiresDataSetNameToBeGiven() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getDataSets().create((String)null, (DataSetData)null);
    }

    @Test
    public void requiresDataSetListToBeGiven() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object data cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getDataSets().create("foxtrot", (DataSetData)null);
    }

    @Test
    public void requiresFileToBeGiven() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object input cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getDataSets().create("whiskey", (InputStream)null);
    }

    @Test
    public void willUploadDataGivenAsFile() throws Exception {
        final String fileContent = "timestamp,alpha,beta\r\n2017-01-01,10,14\r\n2017-01-02,11,13\r\n2017-01-03,12,12";
        InputStream stream = new ByteArrayInputStream(fileContent.getBytes("UTF8"));

        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent("{}");
                return response;
            }
        };

        MockHttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getDataSets().create("tango", stream);

        Assert.assertEquals(fakeEndpoint + "/data/tango", request.getUrl());
        Assert.assertEquals(fileContent, request.getContentAsString());
    }

    @Test
    public void willSaveDataGivenDirectly() throws Exception
    {
        DataSetData data = DataSetGenerator.Run(DateTime.now().plusDays(-90), DateTime.now(), "something");

        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent("{}");
                return response;
            }
        };

        MockHttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getDataSets().create("yankee", data);

        Assert.assertEquals(fakeEndpoint + "/data/yankee", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(data),  request.getContentAsString());
    }

    @Test
    public void willSaveWithMeasureColumnData() throws Exception{
        Columns columns = new Columns();
        ColumnsProperty props = new ColumnsProperty();
        props.setImputation(ImputationStrategy.MEAN);
        props.setDataType(DataType.NUMERICMEASURE);
        columns.setColumnMetadata("Foo", props);

        DataSetData data = DataSetGenerator.Run(DateTime.now().plusDays(-90), DateTime.now(), "something");
        data.setColumns(columns);

        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent("{}");
                return response;
            }
        };

        MockHttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getDataSets().create("yankee", data);

        Assert.assertEquals(fakeEndpoint + "/data/yankee", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(data), request.getContentAsString());
    }
}

