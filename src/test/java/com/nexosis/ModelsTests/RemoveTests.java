package com.nexosis.ModelsTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ModelRemoveCriteria;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.UUID;

public class RemoveTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Test
    public void idIsUsedInUrl() throws Exception {
        UUID modelId = UUID.randomUUID();

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

        ModelRemoveCriteria criteria = new ModelRemoveCriteria();
        criteria.setModelId(modelId);

        target.getModels().remove(criteria);

        Assert.assertEquals(fakeEndpoint + "/models/" + modelId.toString(), request.getUrl());
    }

    @Test
    public void includesDatesInUrlWhenGiven() throws Exception {
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
        ModelRemoveCriteria criteria = new ModelRemoveCriteria();
        criteria.setDataSourceName("data-source-name");
        criteria.setCreatedAfterDate(DateTime.parse("2017-02-02T20:20:12Z"));
        criteria.setCreatedBeforeDate(DateTime.parse("2017-02-22T21:12Z"));

        target.getModels().remove(criteria);

        Assert.assertEquals(fakeEndpoint + "/models?createdBeforeDate=2017-02-22T21:12:00.000Z&createdAfterDate=2017-02-02T20:20:12.000Z&dataSourceName=data-source-name", request.getUrl());
    }

    @Test
    public void requireAtLeastOneRemoveCriteria() throws Exception
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("One of ModelRemoveCriteria.DataSourceName, ModelRemoveCriteria.ModelId should not be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        ModelRemoveCriteria criteria = new ModelRemoveCriteria();
        target.getModels().remove(criteria);

    }

}