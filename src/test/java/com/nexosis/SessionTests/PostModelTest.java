package com.nexosis.SessionTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.Sessions;
import com.nexosis.model.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class PostModelTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Test
    public void setsDataSourceNameWhenGiven() throws Exception {
        ModelSessionRequest sessionRequest = Sessions.trainModel(
                "data-source-name",
                PredictionDomain.REGRESSION,
                "target-column",
                null
        );

        sessionRequest.setCallbackUrl("http://this.is.a.callback.url");

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

        target.getSessions().trainModel(sessionRequest);

        Assert.assertEquals(fakeEndpoint + "/sessions/model", request.getUrl());
        String expected = "{\"dataSourceName\":\"data-source-name\",\"targetColumn\":\"target-column\",\"predictionDomain\":\"regression\",\"callbackUrl\":\"http://this.is.a.callback.url\",\"extraParameters\":{}}";
        Assert.assertEquals(expected, request.getContentAsString());
    }

    @Test
    public void requiresNotNullOrEmptyDataSourceName() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ModelSessionRequest.dataSourceName cannot be null or empty.");

        ModelSessionRequest request = Sessions.trainModel(null,
                PredictionDomain.REGRESSION,
                "target-column",
                null
        );

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().trainModel(request);
    }

    @Test
    public void includesBalanceOptionWhenSpecified() throws Exception {
        ClassificationModelSessionRequest sessionRequest = (ClassificationModelSessionRequest) Sessions.trainModel(
                "data-source-name",
                PredictionDomain.CLASSIFICATION,
                "target-column",
                new ClassificationModelSessionRequest() {{
                    setBalance(true);
                }}
        );

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
        target.getSessions().trainModel(sessionRequest);

        Assert.assertEquals(fakeEndpoint + "/sessions/model", request.getUrl());
        String expected = "{\"dataSourceName\":\"data-source-name\",\"targetColumn\":\"target-column\",\"predictionDomain\":\"classification\",\"extraParameters\":{\"balance\":\"true\"}}";
        Assert.assertEquals(expected, request.getContentAsString());
    }

    @Test
    public void includesContainsAnomaliesOptionWhenSpecified() throws Exception {
        AnomaliesModelSessionRequest anomRequest = (AnomaliesModelSessionRequest)Sessions.trainModel(
                "data-source-name",
                PredictionDomain.ANOMALIES,
                "target-column",
                new AnomaliesModelSessionRequest() {{
                    setContainsAnomalies(true);
                }}
        );

        anomRequest.setContainsAnomalies(true);

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
        target.getSessions().trainModel(anomRequest);

        Assert.assertEquals(fakeEndpoint + "/sessions/model", request.getUrl());
        String expected = "{\"dataSourceName\":\"data-source-name\",\"targetColumn\":\"target-column\",\"predictionDomain\":\"anomalies\",\"extraParameters\":{\"containsAnomalies\":\"true\"}}";
        Assert.assertEquals(expected, request.getContentAsString());
    }
}