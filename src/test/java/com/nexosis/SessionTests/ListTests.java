package com.nexosis.SessionTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.PagingInfo;
import com.nexosis.model.SessionQuery;
import com.nexosis.model.SessionResponses;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class ListTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void formatsPropertiesForListSessions() throws Exception
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
            public MockLowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);

        SessionQuery query = new SessionQuery();
        query.setSortBy("id");
        query.setDataSourceName("alpha");
        query.setEventName("zulu");
        query.setPage(new PagingInfo(0,50));
        query.setRequestedAfterDate(DateTime.parse("2017-01-01T00:00:00Z"));
        query.setRequestedBeforeDate(DateTime.parse("2017-01-11T00:00:00Z"));

        SessionResponses result = target.getSessions().list(query);

        Assert.assertNotNull(result);
        Assert.assertEquals(fakeEndpoint + "/sessions?sortOrder=asc&requestedBeforeDate=2017-01-11T00:00:00.000Z&eventName=zulu&pageSize=50&sortBy=id&requestedAfterDate=2017-01-01T00:00:00.000Z&page=0&dataSourceName=alpha", request.getUrl());
    }

    @Test
    public void excludesPropertiesWhenNoneGiven() throws Exception {

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
        SessionResponses result = target.getSessions().list(new SessionQuery());

        Assert.assertNotNull(result);
        Assert.assertEquals(fakeEndpoint + "/sessions", request.getUrl());
    }
}
