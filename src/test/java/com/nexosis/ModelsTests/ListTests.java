package com.nexosis.ModelsTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ModelSummaryQuery;
import com.nexosis.model.PagingInfo;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class ListTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Test
    public void willNotIncludeFilterParametersWhenNull() throws Exception {
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

        target.getModels().list(new ModelSummaryQuery());
        Assert.assertEquals(fakeEndpoint + "/models", request.getUrl());
    }

    @Test
    public void formatsPropertiesWhenProvided() throws Exception {
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
        
        ModelSummaryQuery query = new ModelSummaryQuery();
        query.setSortBy("id");
        query.setDataSourceName("data-source-name");
        query.setCreatedAfterDate(DateTime.parse("2017-01-01T00:00Z"));
        query.setCreatedBeforeDate(DateTime.parse("2017-01-11T00:00Z"));
        target.getModels().list(query);

        Assert.assertEquals(fakeEndpoint + "/models?sortOrder=asc&createdBeforeDate=2017-01-11T00:00:00.000Z&sortBy=id&createdAfterDate=2017-01-01T00:00:00.000Z&dataSourceName=data-source-name", request.getUrl());
    }

    @Test
    public void setPageSizeIncludesParam() throws Exception {
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

        ModelSummaryQuery query = new ModelSummaryQuery();
        query.setPage(new PagingInfo(0,20));

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getModels().list(query);
        Assert.assertEquals(fakeEndpoint + "/models?pageSize=20&page=0", request.getUrl());
    }
}
