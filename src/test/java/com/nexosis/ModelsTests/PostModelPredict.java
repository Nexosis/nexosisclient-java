package com.nexosis.ModelsTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ModelPredictionRequest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.*;

public class PostModelPredict {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Test
    public void willSendValuesForPrediction() throws Exception {
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
        HashMap<String, String> map = new HashMap<>();
        List<Map<String, String>> data = new ArrayList<>();
        map.put("column","value");
        data.add(map);

        target.getModels().predict(new ModelPredictionRequest(modelId, data));

        Assert.assertEquals(fakeEndpoint + "/models/" + modelId.toString() + "/predict",  request.getUrl());
        Assert.assertEquals("{\"data\":[{\"column\":\"value\"}]}", request.getContentAsString());
    }

    @Test
    public void requiresNotNullFeatures() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object data cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        ModelPredictionRequest request = new ModelPredictionRequest(UUID.randomUUID(), null);

        target.getModels().predict( request);
    }
}