package com.nexosis.SessionTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ModelSessionRequest;
import com.nexosis.model.PredictionDomain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CreateModelTests {
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
    public void setsDataSetNameWhenGiven() throws Exception {

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

        ModelSessionRequest sessionRequest = new ModelSessionRequest();
        sessionRequest.setDataSourceName("data-source-name");
        sessionRequest.setTargetColumn("target-column");
        sessionRequest.setPredictionDomain(PredictionDomain.REGRESSION);
        sessionRequest.setCallbackUrl("http://this.is.a.callback.url");

        target.getSessions().trainModel(sessionRequest);

        Assert.assertEquals(fakeEndpoint + "/sessions/model", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(sessionRequest), request.getContentAsString());
    }

    @Test
    public void requiresNotNullDetail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object ModelSessionRequest cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().trainModel(null);
    }

    @Test
    public void requiresNotNullOrEmptyDataSourceName() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ModelSessionRequest.dataSourceName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        ModelSessionRequest sessionRequest = new ModelSessionRequest();
        sessionRequest.setTargetColumn("target-column");
        sessionRequest.setPredictionDomain(PredictionDomain.REGRESSION);
        target.getSessions().trainModel(sessionRequest);
    }
}