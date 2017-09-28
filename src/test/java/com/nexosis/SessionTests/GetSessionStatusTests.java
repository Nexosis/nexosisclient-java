package com.nexosis.SessionTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.SessionResultStatus;
import com.nexosis.model.SessionStatus;
import com.nexosis.util.Action;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.UUID;

public class GetSessionStatusTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void statusHeaderIsAssignedToResult() throws Exception {
        UUID sessionId = UUID.randomUUID();

        SessionResultStatus sessionStatus = new SessionResultStatus();
        sessionStatus.setSessionId(sessionId);
        sessionStatus.setStatus(SessionStatus.STARTED);

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
        SessionResultStatus result = target.getSessions().getStatus(sessionId);

        Assert.assertEquals(request.getUrl(), fakeEndpoint + "/sessions/" + sessionId);
    }

    @Test
    public void httpTransformerIsWrappedAndCalled() throws Exception {
        UUID sessionId = UUID.randomUUID();

        SessionResultStatus sessionStatus = new SessionResultStatus();
        sessionStatus.setSessionId(sessionId);
        sessionStatus.setStatus(SessionStatus.STARTED);


        TestIfCalled isCalled = new TestIfCalled(false);

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
        target.getSessions().getStatus(sessionId, isCalled);

        Assert.assertTrue("Http transform function not called", isCalled.called);
    }

    class TestIfCalled implements Action<HttpRequest, HttpResponse> {
        private boolean called = false;

        TestIfCalled(boolean called) {
            this.called = called;
        }

        boolean getCalled() {
            return called;
        }

        @Override
        public void invoke(HttpRequest target1, HttpResponse target2) throws Exception {
            called = true;
        }
    }
}
