package com.nexosis.DataSetTests;

import com.google.api.client.http.*;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.*;
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getDataSets().get("test");
        //Assert.assertEquals(fakeEndpoint + "/data/test?pageSize=" + NexosisClient.getMaxPageSize() + "&page=0", req.getUrl());
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getDataSets().get(
                "test",
                10,
                10,
                DateTime.parse("2017-01-01T00:00:00Z"),
                DateTime.parse("2017-01-31T00:00:00Z"),
                new ArrayList<String>() {{ add("test1"); add("test2");}});

        Assert.assertEquals(fakeEndpoint + "/data/test?include=test1&include=test2&endDate=2017-01-31T00:00:00.000Z"+
                "&pageSize=10&page=10&startDate=2017-01-01T00:00:00.000Z", request.getUrl());
    }

    @Test //(expected = IllegalArgumentException.class)
    public void requiresDataSetNameIsNotNullOrEmpty() throws NexosisClientException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getDataSets().get("");
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getDataSets().get("kilo", baos);

        Assert.assertEquals(fakeEndpoint + "/data/kilo", request.getUrl());
        Assert.assertEquals(fileContent, new String(baos.toByteArray(), "UTF-8"));
    }
}
