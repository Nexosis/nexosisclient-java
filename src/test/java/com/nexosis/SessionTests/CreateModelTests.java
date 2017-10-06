package com.nexosis.SessionTests;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ModelSessionDetail;
import com.nexosis.model.PredictionDomain;
import org.apache.http.protocol.ResponseServer;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.*;

public class CreateModelTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

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
        ModelSessionDetail modelSession = new ModelSessionDetail();
        modelSession.setDataSourceName("data-source-name");
        modelSession.setTargetColumn("target-column");
        modelSession.setPredictionDomain(PredictionDomain.REGRESSION);
        modelSession.setCallbackUrl("http://this.is.a.callback.url");
        target.getSessions().trainModel(modelSession);

        Assert.assertEquals(fakeEndpoint + "/sessions/model", request.getUrl());
        String expected = "{\"dataSourceName\":\"data-source-name\",\"targetColumn\":\"target-column\",\"predictionDomain\":\"regression\",\"callbackUrl\":\"http://this.is.a.callback.url\",\"isEstimate\":false}";
        Assert.assertEquals(expected, request.getContentAsString());
    }

    @Test
    public void requiresNotNullDetail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object data cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().trainModel((ModelSessionDetail)null);
    }

    @Test
    public void requiresNotNullOrEmptyDataSourceName() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ModelSessionDetail.getDataSourceName() cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        ModelSessionDetail modelSession = new ModelSessionDetail();
        modelSession.setDataSourceName(null);
        modelSession.setTargetColumn("target-column");
        modelSession.setPredictionDomain(PredictionDomain.REGRESSION);

        target.getSessions().trainModel(modelSession);
    }

    @Test
    public void requiredNotNullOrEmptyTargetColumn() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ModelSessionDetail.getTargetColumn() cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        ModelSessionDetail modelSession = new ModelSessionDetail();
        modelSession.setDataSourceName("data-source-name");
        modelSession.setTargetColumn(null);
        modelSession.setPredictionDomain(PredictionDomain.REGRESSION);

        target.getSessions().trainModel(modelSession);
    }
}