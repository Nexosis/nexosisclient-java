package com.nexosis.ViewTests;

import com.google.api.client.http.LowLevelHttpRequest;
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
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value viewName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getViews().remove(null, false, null);
    }

    @Test
    public void removeAddsViewNameToUri() throws Exception {
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
        target.getViews().remove("testView", false, null);

        Assert.assertEquals(fakeEndpoint + "/views/testView", request.getUrl());
    }

}
