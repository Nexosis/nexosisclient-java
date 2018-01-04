package com.nexosis.ImportTests;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ImportDetailQuery;
import com.nexosis.model.ImportDetails;
import com.nexosis.model.PagingInfo;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ListTests {
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void formatsPropertiesForListImports() throws Exception {
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

        ImportDetailQuery query = new ImportDetailQuery();
        query.setDataSetName("alpha");
        query.setRequestedAfterDate(DateTime.parse("2017-01-01T00:00:00Z"));
        query.setRequestedBeforeDate(DateTime.parse("2017-01-11T00:00:00Z"));
        query.setPage(new PagingInfo(0,1));

        ImportDetails result = target.getImports().list(query);

        Assert.assertNotNull(result);
        Assert.assertEquals(fakeEndpoint + "/imports?requestedBeforeDate=2017-01-11T00:00:00.000Z&pageSize=1&page=0&requestedAfterDate=2017-01-01T00:00:00.000Z&dataSetName=alpha", request.getUrl());
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

        ImportDetailQuery query = new ImportDetailQuery();
        query.setPage(new PagingInfo(0,1));

        ImportDetails result = target.getImports().list(query);

        Assert.assertNotNull(result);
        Assert.assertEquals(fakeEndpoint + "/imports?pageSize=1&page=0", request.getUrl());
    }

    @Test
    public void addOnlyThosePropertiesGiven() throws Exception {
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

        ImportDetailQuery query = new ImportDetailQuery();
        query.setDataSetName("alpha");
        query.setRequestedBeforeDate(DateTime.parse("2017-01-01T00:00:00Z"));
        query.setPage(new PagingInfo(0,1));

        ImportDetails result = target.getImports().list(query);

        Assert.assertNotNull(result);
        Assert.assertEquals(fakeEndpoint + "/imports?requestedBeforeDate=2017-01-01T00:00:00.000Z&pageSize=1&page=0&dataSetName=alpha", request.getUrl());
    }

}
