package com.nexosis.ViewTests;

import com.neovisionaries.i18n.CountryCode;
import com.nexosis.impl.ApiConnection;
//import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
//import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
//import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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
import java.util.TimeZone;

import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ApiConnection.class })
public class CreateTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    //@Mock
    //private HttpClientFactory httpClientFactory;
    //@Mock
    //private CloseableHttpClient httpClient;
    //@Mock
    //private CloseableHttpResponse httpResponse;
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
        target = new NexosisClient(fakeApiKey, fakeEndpoint);
        apiFakeEndpointUri = new URI(fakeEndpoint);

        //PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        //PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        //PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        //PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
    }

    @Test
    public void addsCalendarIntoJoinByName() throws Exception {
        HttpPut put = new HttpPut();
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPut.class).withNoArguments().thenReturn(put);
        target.getViews().create("TestCalendarView", "TestDataSet", CountryCode.US, null, null);
        Assert.assertNotNull(put.getEntity());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"calendar\":{\"name\":\"Nexosis.Holidays-US\"}}]}", EntityUtils.toString(put.getEntity()));
    }

    @Test
    public void addsCalendarIntoJoinByUri() throws Exception{
        HttpPut put = new HttpPut();
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPut.class).withNoArguments().thenReturn(put);
        target.getViews().create("TestCalendarView", "TestDataSet", URI.create("http://nothere.com/somecal/holidays.ical"), null,null);
        Assert.assertNotNull(put.getEntity());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"calendar\":{\"url\":\"http://nothere.com/somecal/holidays.ical\"}}]}", EntityUtils.toString(put.getEntity()));
    }

    @Test
    public void usesTimeZoneIfPresent() throws Exception{
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        HttpPut put = new HttpPut();
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPut.class).withNoArguments().thenReturn(put);
        target.getViews().create("TestCalendarView", "TestDataSet", URI.create("http://nothere.com/somecal/holidays.ical"), tz,null);
        Assert.assertNotNull(put.getEntity());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"calendar\":{\"url\":\"http://nothere.com/somecal/holidays.ical\",\"timeZone\":\"America/Los_Angeles\"}}]}", EntityUtils.toString(put.getEntity()));
    }

    @Test
    public void addsDataSetIntoJoinByName() throws Exception{
        HttpPut put = new HttpPut();
        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPut.class).withNoArguments().thenReturn(put);
        target.getViews().create("TestCalendarView", "TestDataSet", "RightDataSet", null);
        Assert.assertNotNull(put.getEntity());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"dataSet\":{\"name\":\"RightDataSet\"}}]}", EntityUtils.toString(put.getEntity()));
    }
}
