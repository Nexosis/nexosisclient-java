package com.nexosis.SessionTests;

import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.SessionResponse;
import com.nexosis.model.SessionResponses;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ApiConnection.class })
public class ListTests {
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

    private NexosisClient target;
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";
    private URI apiFakeEndpointUri;


    @Before
    public void setUp() throws Exception {
        target = new NexosisClient(fakeApiKey, fakeEndpoint, httpClientFactory);
        apiFakeEndpointUri = new URI(fakeEndpoint);

        PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
    }

    @Test
    public void formatsPropertiesForListSessions() throws Exception
    {
        HttpGet get = new HttpGet();
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpGet.class).withNoArguments().thenReturn(get);

        SessionResponses result = target.getSessions().list(
                "alpha",
                "zulu",
                DateTime.parse("2017-01-01T00:00:00Z"),
                DateTime.parse("2017-01-11T00:00:00Z")
        );

        Assert.assertNotNull(result);
        Assert.assertEquals(new URI(fakeEndpoint + "/sessions?dataSetName=alpha&eventName=zulu&requestedAfterDate=2017-01-01T00%3A00%3A00.000Z&requestedBeforeDate=2017-01-11T00%3A00%3A00.000Z"), get.getURI());
    }

    @Test
    public void excludesPropertiesWhenNoneGiven() throws Exception {
        HttpGet get = new HttpGet();
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpGet.class).withNoArguments().thenReturn(get);

        SessionResponses result = target.getSessions().list();

        Assert.assertNotNull(result);
        Assert.assertEquals(new URI(fakeEndpoint + "/sessions"), get.getURI());
    }
}
