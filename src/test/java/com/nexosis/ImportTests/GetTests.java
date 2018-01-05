package com.nexosis.ImportTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.UUID;

public class GetTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";


    @Before
    public void setUp() throws Exception {

    }

    @Test //(expected = IllegalArgumentException.class)
    public void getRequiresNonNullIdentifier() throws NexosisClientException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object id cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getImports().get(null);
    }

    @Test
    public void getIncludesIdAsPartOfPath() throws Exception {
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
        UUID id = UUID.randomUUID();
        target.getImports().get(id);
        Assert.assertEquals(fakeEndpoint + String.format("/imports/%s", id.toString()), request.getUrl());
    }
}
