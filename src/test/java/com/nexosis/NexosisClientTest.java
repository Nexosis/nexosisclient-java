package com.nexosis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.AccountQuotas;
import com.nexosis.model.ErrorResponse;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

import static com.nexosis.util.NexosisHeaders.*;

public class NexosisClientTest {
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

    /* Disabling as it breaks full suite runs
     * Not clear how incorrect env var gets used
     * in subsequent tests, but it does
    @Test
    public void getsKeyFromEnvironment() {
        String oldKey = System.getenv("NEXOSIS_API_KEY");
        try {
            HashMap<String, String> envVar = new HashMap<String, String>();
            envVar.put("NEXOSIS_API_KEY", "abcdefg");

            setEnv(envVar);
            NexosisClient target = new NexosisClient();
            Assert.assertEquals("abcdefg", target.getApiKey());
        } finally {
            // Put back old env Var so as to not mess up other tests
            // that may rely on different key.
            HashMap<String, String> envVar = new HashMap<String, String>();
            envVar.put("NEXOSIS_API_KEY", oldKey);
            setEnv(envVar);
        }
    }
    */

    @Test
    public void canGiveKeyWhenConstructing() {
        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        Assert.assertEquals(fakeApiKey, target.getApiKey());
    }

    @Test
    public void addsTrailingSlashWhenNeeded() {
        NexosisClient target = new NexosisClient("alpha-bravo-delta-charlie", "https://should.have.a.slash");
        Assert.assertEquals("https://should.have.a.slash/", target.getConfiguredUrl());
    }

    @Test
    public void addsApiKeyHeaderToRequest() throws Exception {
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
        target.getAccountQuotas();

        Assert.assertTrue(request.getHeaders().containsKey(NEXOSIS_API_KEY));
        Assert.assertEquals(fakeApiKey, request.getFirstHeaderValue(NEXOSIS_API_KEY));
    }

    @Test
    public void addsUserAgentToRequest() throws Exception {
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
        target.getAccountQuotas();

        Assert.assertTrue(request.getHeaders().containsKey("user-agent"));
        Assert.assertEquals(NexosisClient.CLIENT_VERSION, request.getFirstHeaderValue("user-agent"));
    }

    @Test
    public void processesCostAndBalance() throws Exception {
        final ArrayList<String> fakeHeaders = new ArrayList<>();
        fakeHeaders.add(NEXOSIS_ACCOUNT_DATASET_COUNT_ALLOTTED);
        fakeHeaders.add(NEXOSIS_ACCOUNT_DATASET_COUNT_CURRENT);
        fakeHeaders.add(NEXOSIS_ACCOUNT_PREDICTION_COUNT_ALLOTTED);
        fakeHeaders.add(NEXOSIS_ACCOUNT_PREDICTION_COUNT_CURRENT);
        fakeHeaders.add(NEXOSIS_ACCOUNT_SESSION_COUNT_ALLOTTED);
        fakeHeaders.add(NEXOSIS_ACCOUNT_SESSION_COUNT_CURRENT);

        final ArrayList<String> fakeHeaderValues = new ArrayList<>();
        fakeHeaderValues.add("0");
        fakeHeaderValues.add("1");
        fakeHeaderValues.add("2");
        fakeHeaderValues.add("3");
        fakeHeaderValues.add("4");
        fakeHeaderValues.add("5");

        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent("{}");
                response.setHeaderNames(fakeHeaders);
                response.setHeaderValues(fakeHeaderValues);
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
        AccountQuotas result = target.getAccountQuotas();

        // Test Cost
        Assert.assertEquals(0, result.getDataSetCountAllotted());
        Assert.assertEquals(1, result.getDataSetCountCurrent());
        Assert.assertEquals(2, result.getPredictionCountAllotted());
        Assert.assertEquals(3, result.getPredictionCountCurrent());
        Assert.assertEquals(4, result.getSessionCountAllotted());
        Assert.assertEquals(5, result.getSessionCountCurrent());
    }

    @Test
    public void canHandleErrorResponse() throws Exception {
        final UUID activityId = UUID.randomUUID();
        final ErrorResponse errorData = new ErrorResponse() {{
            setStatusCode(500);
            setErrorType("SomethingWentWrong");
            setMessage("An error occurred.");
            setErrorDetails(new HashMap<String, Object>() {{
                put("error", "details");
            }});
            setActivityId(activityId);
        }};

        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(500);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent(mapper.writeValueAsString(errorData));
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

        try {
            NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
            target.getAccountQuotas();
        } catch (NexosisClientException exception) {
            Assert.assertEquals(HttpStatusCodes.STATUS_CODE_SERVER_ERROR, exception.getStatusCode());
            Assert.assertNotNull(exception.getErrorResponse());
            Assert.assertEquals(errorData.getErrorType(), exception.getErrorResponse().getErrorType());
            Assert.assertEquals(errorData.getMessage(), exception.getErrorResponse().getMessage());
            Assert.assertEquals(errorData.getActivityId(), exception.getErrorResponse().getActivityId());
            Assert.assertEquals(errorData.getErrorDetails(), exception.getErrorResponse().getErrorDetails());
        }
    }

    private static void setEnv(Map<String, String> newenv) {
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        } catch (NoSuchFieldException e) {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for (Class cl : classes) {
                    if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newenv);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
