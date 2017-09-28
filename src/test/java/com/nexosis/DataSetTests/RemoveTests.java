package com.nexosis.DataSetTests;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.DataSetDeleteOptions;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class RemoveTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void removeRequiresDataSetName() throws NexosisClientException
    {
        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        target.getDataSets().remove(null, DataSetDeleteOptions.CASCADE_NONE);
    }

    @Test
    public void generatesCascadeValuesFromDeleteOptions() throws Exception
    {
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
        target.getDataSets().remove("sierra", DataSetDeleteOptions.CASCASE_BOTH);

        Assert.assertEquals(fakeEndpoint + "/data/sierra?cascade=forecast&cascade=session", request.getUrl());
    }

    @Test public void doesNotSetCascadeWhenNoneOptionGiven() throws Exception
    {
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
        target.getDataSets().remove("november", DataSetDeleteOptions.CASCADE_NONE);

        Assert.assertEquals( fakeEndpoint+"/data/november", request.getUrl());
    }

    @Test
    public void removeForDateRangeQueriesThatRange() throws Exception
    {
        final MockLowLevelHttpRequest req = new MockLowLevelHttpRequest() {
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
                req.setUrl(url);
                return req;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);

        target.getDataSets().remove(
                "oscar",
                DateTime.parse("2015-10-12T00:00:00Z"),
                DateTime.parse("2015-10-31T19:47:00Z"),
                DataSetDeleteOptions.CASCADE_NONE
        );

        Assert.assertEquals(fakeEndpoint + "/data/oscar?endDate=2015-10-31T19:47:00.000Z&startDate=2015-10-12T00:00:00.000Z", req.getUrl());
    }
}
