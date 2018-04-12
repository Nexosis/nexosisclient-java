package com.nexosis.DataSetTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.DataSetDataQuery;
import com.nexosis.model.PagingInfo;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GetTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void loadsByName() throws Exception {
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
            public MockLowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        DataSetDataQuery query = new DataSetDataQuery("test");
        target.getDataSets().get(query);
        Assert.assertEquals(fakeEndpoint + "/data/test", request.getUrl());
    }

    @Test
    public void includesAllParametersWhenGiven() throws Exception {
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
            public MockLowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);

        DataSetDataQuery query = new DataSetDataQuery("test");
        query.setPage(new PagingInfo(10,10));
        query.setStartDate(DateTime.parse("2017-01-01T00:00:00Z"));
        query.setEndDate(DateTime.parse("2017-01-31T00:00:00Z"));
        query.setIncludedColumns(new ArrayList<String>() {{ add("test1"); add("test2");}});
        target.getDataSets().get(query);

        Assert.assertEquals(fakeEndpoint + "/data/test?pageSize=10&include=test1&include=test2&page=10&endDate=2017-01-31T00:00:00.000Z"+
                "&startDate=2017-01-01T00:00:00.000Z", request.getUrl());
    }

    @Test //(expected = IllegalArgumentException.class)
    public void requiresDataSetNameIsNotNullOrEmpty() throws NexosisClientException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value DataSetDataQuery.Name cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getDataSets().get(new DataSetDataQuery());
    }

    @Test
    public void willSaveDataSetToGivenFile() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // File contents to get written to baos.
        final String fileContent = "timestamp,lima\r\n2017-01-01,123\r\n2017-01-2,444\r\n2017-01-03,123";

        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType("text/csv");
                response.setContent(fileContent.getBytes());
                return response;
            }
        };

        MockHttpTransport transport = new MockHttpTransport() {
            @Override
            public MockLowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        DataSetDataQuery query = new DataSetDataQuery("kilo");
        target.getDataSets().get(query, baos);

        Assert.assertEquals(fakeEndpoint + "/data/kilo", request.getUrl());
        Assert.assertEquals(fileContent, new String(baos.toByteArray(), "UTF-8"));
    }
}
