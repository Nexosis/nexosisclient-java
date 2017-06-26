package com.nexosis.DataSetTests;

import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.DataSetData;
import com.nexosis.model.ReturnsStatus;
import com.nexosis.model.SessionStatus;
import com.nexosis.util.HttpMethod;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
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

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ApiConnection.class })
public class GetTests {
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
    public void loadsByName() throws Exception {
        HttpGet get = new HttpGet();

        PowerMockito.whenNew(HttpGet.class).withNoArguments().thenReturn(get);

        target.getDataSets().get("test");

        Assert.assertEquals(new URI(fakeEndpoint + "/data/test?page=0&pageSize=" + NexosisClient.getMaxPageSize()), get.getURI());
    }

    @Test
    public void includesAllParametersWhenGiven() throws Exception {
        HttpGet get = new HttpGet();

        PowerMockito.whenNew(HttpGet.class).withNoArguments().thenReturn(get);

        target.getDataSets().get(
                "test",
                10,
                10,
                DateTime.parse("2017-01-01T00:00:00Z"),
                DateTime.parse("2017-01-31T00:00:00Z"),
                new ArrayList<String>() {{ add("test1"); add("test2");}});

        Assert.assertEquals(new URI(fakeEndpoint + "/data/test?page=10&pageSize=10&startDate=2017-01-01T00%3A00%3A00.000Z&endDate=2017-01-31T00%3A00%3A00.000Z&include=test1&include=test2"), get.getURI());
    }

    @Test //(expected = IllegalArgumentException.class)
    public void requiresDataSetNameIsNotNullOrEmpty() throws NexosisClientException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        target.getDataSets().get("");
    }

    @Test
    public void willSaveDataSetToGivenFile() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ReturnsStatus returnsStatus = mock(ReturnsStatus.class);

        // File contents to get written to baos.
        String fileContent = "timestamp,lima\r\n2017-01-01,123\r\n2017-01-2,444\r\n2017-01-03,123";

        HttpGet get = new HttpGet();
        // Build an entity containing the expected data
        HttpEntity entity = new StringEntity(fileContent);

        PowerMockito.whenNew(HttpGet.class).withNoArguments().thenReturn(get);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(entity);
        PowerMockito.whenNew(ReturnsStatus.class).withNoArguments().thenReturn(returnsStatus);
        PowerMockito.when(returnsStatus.getSessionStatus()).thenReturn(SessionStatus.COMPLETED);

        target.getDataSets().get("kilo", baos);

        Assert.assertEquals(new URI(fakeEndpoint + "/data/kilo"), get.getURI());
        Assert.assertEquals(fileContent, new String(baos.toByteArray(), "UTF-8"));
    }
}
