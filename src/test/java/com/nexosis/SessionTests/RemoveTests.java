package com.nexosis.SessionTests;

import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.SessionType;
import com.nexosis.util.HttpMethod;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
import java.util.UUID;

import static org.mockito.Matchers.any;


@RunWith(PowerMockRunner.class)
@PrepareForTest( {ApiConnection.class })
public class RemoveTests {
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
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
    }

    @Test
    public void HandlerDoesNotIncludeOptionalArgsIfTheyAreNotSet() throws Exception
    {
        HttpDelete delete = new HttpDelete();
        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);

        target.getSessions().remove();

        Assert.assertEquals(HttpMethod.DELETE.name(), delete.getMethod());
        Assert.assertEquals(new URI(fakeEndpoint + "/sessions"), delete.getURI());
    }

        @Test
    public void HandlerIncludesOptionalArgsIfTheyAreSet() throws Exception
    {
        HttpDelete delete = new HttpDelete();
        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);

        target.getSessions().remove("data-set-name", "event-name", SessionType.FORECAST);

        Assert.assertEquals(HttpMethod.DELETE.name(), delete.getMethod());
        Assert.assertEquals(new URI(fakeEndpoint+ "/sessions?dataSetName=data-set-name&eventName=event-name&type=forecast"), delete.getURI());
    }

        @Test
    public void IncludesDatesInUrlWhenGiven() throws Exception
    {
        HttpDelete delete = new HttpDelete();
        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);

        target.getSessions().remove(
                null,
                null,
                null,
                DateTime.parse("2017-02-02 20:20:12 -00:00", DateTimeFormat.forPattern("yyyy-MM-dd H:m:s Z")),
                DateTime.parse("2017-02-22 21:12:00 -00:00", DateTimeFormat.forPattern("yyyy-MM-dd H:m:s Z"))
        );

        Assert.assertEquals(HttpMethod.DELETE.name(), delete.getMethod());
        Assert.assertEquals(new URI(fakeEndpoint + "/sessions?requestedAfterDate=2017-02-02T20%3A20%3A12.000&requestedBeforeDate=2017-02-22T21%3A12%3A00.000"), delete.getURI());
    }

    @Test
    public void IdIsUsedInUrl() throws Exception
    {
        UUID sessionId = UUID.randomUUID();
        HttpDelete delete = new HttpDelete();
        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);

        target.getSessions().remove(sessionId);

        Assert.assertEquals(HttpMethod.DELETE.name(), delete.getMethod());
        Assert.assertEquals(new URI(fakeEndpoint + "/sessions/" + sessionId), delete.getURI());
    }
}
