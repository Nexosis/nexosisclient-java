package com.nexosis.DataSetTests;


import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.DataSetDeleteOptions;
import com.nexosis.util.HttpMethod;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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
import java.util.EnumSet;

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
    public void removeRequiresDataSetName() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        target.getDataSets().remove(null, DataSetDeleteOptions.CASCADE_NONE);
    }

    @Test
    public void generatesCascadeValuesFromDeleteOptions() throws Exception
    {
        HttpDelete delete = new HttpDelete();

        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);
        target.getDataSets().remove("sierra", DataSetDeleteOptions.CASCASE_BOTH);

        Assert.assertEquals(new URI(fakeEndpoint + "/data/sierra?cascade=forecast&cascade=sessions"), delete.getURI());
    }

    @Test public void doesNotSetCascadeWhenNoneOptionGiven() throws Exception
    {
        HttpDelete delete = new HttpDelete();

        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);
        target.getDataSets().remove("november", DataSetDeleteOptions.CASCADE_NONE);

        Assert.assertEquals(new URI( fakeEndpoint+"/data/november"), delete.getURI());
    }

    @Test
    public void removeForDateRangeQueriesThatRange() throws Exception
    {
        HttpDelete delete = new HttpDelete();

        PowerMockito.whenNew(HttpDelete.class).withNoArguments().thenReturn(delete);

        target.getDataSets().remove(
                "oscar",
                DateTime.parse("2015-10-12T00:00:00Z"),
                DateTime.parse("2015-10-31T19:47:00Z"),
                DataSetDeleteOptions.CASCADE_NONE
        );

        Assert.assertEquals(new URI(fakeEndpoint + "/data/oscar?startDate=2015-10-12T00%3A00%3A00.000Z&endDate=2015-10-31T19%3A47%3A00.000Z"), delete.getURI());
    }
}
