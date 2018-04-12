package com.nexosis.SessionTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ConfusionMatrixResponse;
import com.nexosis.model.SessionResultQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.UUID;

public class GetResultsTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getResultsReturnsThem() throws Exception
    {
        UUID sessionId = UUID.randomUUID();

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
        SessionResultQuery query = new SessionResultQuery();
        query.setSessionId(sessionId);
        target.getSessions().getResults(query);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results", request.getUrl());
    }

    @Test
    public void getResultToFileThrowsWithNullWriter() throws Exception
    {
        UUID sessionId = UUID.randomUUID();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object output cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        SessionResultQuery query = new SessionResultQuery();
        query.setSessionId(sessionId);
        target.getSessions().getResults(query, null);
    }

    @Test
    public void getConfusionMatrixBuildsClass() throws Exception{
        final UUID sessionId = UUID.randomUUID();
        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent("{\"sessionId\":\"629f4d80-ac9d-416d-9b4a-61db74ed504a\", \"confusionMatrix\": [[0,1],[1,0]], \"classes\": [0,1]}");
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
        ConfusionMatrixResponse response = target.getSessions().getConfusionMatrix(sessionId);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getClasses()[0], "0");
        Assert.assertEquals(response.getConfusionMatrix()[0][1], 1);
    }

    @Test
    public void getConfusionMatrixUrlIsCorrect() throws Exception
    {
        UUID sessionId = UUID.randomUUID();

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
        target.getSessions().getConfusionMatrix(sessionId);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/confusionmatrix", request.getUrl());
    }

    @Test
    public void GetClassScoresReturnsThen() throws Exception
    {
        UUID sessionId = UUID.randomUUID();

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
        target.getSessions().getResultClassScores(sessionId);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/classscores", request.getUrl());
    }

    @Test
    public void getAnomalyScoresReturnsThen() throws Exception {
        UUID sessionId = UUID.randomUUID();

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
        target.getSessions().getResultAnomalyScores(sessionId);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/anomalyscores", request.getUrl());
    }
}
