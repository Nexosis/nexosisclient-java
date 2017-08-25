package com.nexosis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.AccountBalance;
import com.nexosis.model.ErrorResponse;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiConnection.class})
public class NexosisClientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private CloseableHttpClient httpClient;
    @Mock
    private CloseableHttpResponse httpResponse;
    @Mock
    private HttpEntity httpEntity;
    @Mock
    private StatusLine statusLine;
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private URI apiFakeEndpointUri;

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        apiFakeEndpointUri = new URI(fakeEndpoint);
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
        NexosisClient target = new NexosisClient("asdfasdfasdf");
        Assert.assertEquals("asdfasdfasdf", target.getApiKey());
    }

    @Test
    public void addsTrailingSlashWhenNeeded() {
        NexosisClient target = new NexosisClient("alpha-bravo-delta-charlie", "https://should.have.a.slash");
        Assert.assertEquals("https://should.have.a.slash/", target.getConfiguredUrl());
    }

    @Test
    public void addsApiKeyHeaderToRequest() throws Exception {
        HttpGet get = new HttpGet(apiFakeEndpointUri);

        PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        PowerMockito.whenNew(HttpGet.class).withAnyArguments().thenReturn(get);
        PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        NexosisClient target = new NexosisClient("abcdefg", fakeEndpoint, httpClientFactory);
        AccountBalance balance = target.getAccountBalance();

        Assert.assertTrue(get.getHeaders("api-key") != null);
        Assert.assertTrue(get.getHeaders("api-key").length == 1);
        Assert.assertEquals("abcdefg", get.getHeaders("api-key")[0].getValue());
    }

    @Test
    public void addsUserAgentToRequest() throws Exception {
        HttpGet get = new HttpGet(apiFakeEndpointUri);

        PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        PowerMockito.when(httpClient.execute(any(HttpRequestBase.class))).thenReturn(httpResponse);
        PowerMockito.whenNew(HttpGet.class).withAnyArguments().thenReturn(get);
        PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        NexosisClient target = new NexosisClient("abcdefg", fakeEndpoint, httpClientFactory);
        AccountBalance balance = target.getAccountBalance();

        Assert.assertTrue(get.getHeaders("User-Agent") != null);
        Assert.assertTrue(get.getHeaders("User-Agent").length == 1);
        Assert.assertEquals(NexosisClient.CLIENT_VERSION, get.getHeaders("User-Agent")[0].getValue());
    }

    @Test
    public void processesCostAndBalance() throws Exception {
        Header[] fakeHeaders = {
                new BasicHeader("nexosis-request-cost", "123.12 USD"),
                new BasicHeader("nexosis-account-balance", "999.99 USD")
        };

        PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        PowerMockito.when(httpResponse.getAllHeaders()).thenReturn(fakeHeaders);
        PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        NexosisClient target = new NexosisClient("abcdefg", fakeEndpoint, httpClientFactory);
        AccountBalance result = target.getAccountBalance();

        // Test Cost
        Assert.assertEquals(new BigDecimal("123.12"), result.getCost().getAmount());
        Assert.assertEquals(Currency.getInstance("USD"), result.getCost().getCurrency());
        // Test Balance
        Assert.assertEquals(new BigDecimal("999.99"), result.getBalance().getAmount());
        Assert.assertEquals(Currency.getInstance("USD"), result.getBalance().getCurrency());
    }

    @Test
    public void canHandleErrorResponse() throws Exception {

        final UUID activityId = UUID.randomUUID();
        ErrorResponse errorData = new ErrorResponse() {{
            setStatusCode(500);
            setErrorType("SomethingWentWrong");
            setMessage("An error occurred.");
            setErrorDetails(new HashMap<String, Object>() {{
                put("error", "details");
            }});
            setActivityId(activityId);
        }};

        ObjectMapper om = mock(ObjectMapper.class);

        PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(500);
        PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(om);
        PowerMockito.when(om.readValue("{}", ErrorResponse.class)).thenReturn(errorData);

        try {
            NexosisClient target = new NexosisClient("abcdefg", fakeEndpoint, httpClientFactory);
            target.getAccountBalance();
        } catch (NexosisClientException exception) {
            Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, exception.getStatusCode());
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
