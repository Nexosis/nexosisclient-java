package com.nexosis.DataSetTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.nexosis.DataSetGenerator;
import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.DataSetData;
import com.nexosis.util.HttpMethod;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
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
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ApiConnection.class })

public class SaveTests {
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

    private ObjectMapper mapper = new ObjectMapper();
    private NexosisClient target;
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";
    private URI apiFakeEndpointUri;

    @Before
    public void setUp() throws Exception {
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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
    public void requiresDataSetNameToBeGiven() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        target.getDataSets().create((String)null, (DataSetData)null);
    }

    @Test
    public void requiresDataSetListToBeGiven() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object data cannot be null.");

        target.getDataSets().create("foxtrot", (DataSetData)null);
    }

    @Test
    public void requiresFileToBeGiven() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object input cannot be null.");

        target.getDataSets().create("whiskey", (InputStream)null);
    }

    @Test
    public void willUploadDataGivenAsFile() throws Exception {
        HttpPut put = new HttpPut();
        String fileContent = "timestamp,alpha,beta\r\n2017-01-01,10,14\r\n2017-01-02,11,13\r\n2017-01-03,12,12";
        InputStream stream = new ByteArrayInputStream(fileContent.getBytes("UTF8"));

        PowerMockito.whenNew(HttpPut.class).withNoArguments().thenReturn(put);

        target.getDataSets().create("tango", stream);

        Assert.assertEquals(new URI(fakeEndpoint + "/data/tango"), put.getURI());

        Assert.assertEquals(fileContent, EntityUtils.toString(put.getEntity()));
    }

    @Test
    public void willSaveDataGivenDirectly() throws Exception
    {
        HttpPut put = new HttpPut();
        PowerMockito.whenNew(HttpPut.class).withNoArguments().thenReturn(put);

        DataSetData data = DataSetGenerator.Run(DateTime.now().plusDays(-90), DateTime.now(), "something");
        target.getDataSets().create("yankee", data);

        Assert.assertEquals(new URI(fakeEndpoint + "/data/yankee"), put.getURI());

        Assert.assertEquals(mapper.writeValueAsString(data), EntityUtils.toString(put.getEntity()));
    }
}

