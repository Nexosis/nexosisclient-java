package com.nexosis.SessionTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.DataSetData;
import com.nexosis.model.ResultInterval;
import com.nexosis.util.HttpMethod;
import com.nexosis.util.JodaTimeHelper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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
import org.joda.time.DateTime;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ApiConnection.class })
public class CreateForecastTests {
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
        target = new NexosisClient(fakeApiKey, fakeEndpoint, httpClientFactory);
        apiFakeEndpointUri = new URI(fakeEndpoint);

        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
    }

    @Test
    public void SetsDataSetNameWhenGiven() throws Exception {
        HttpPost post = new HttpPost();

        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPost.class).withNoArguments().thenReturn(post);

        target.getSessions().createForecast(
                "data-set-name",
                "target-column",
                DateTime.parse("2017-12-12T10:11:12Z"),
                DateTime.parse("2017-12-22T22:23:24Z"),
                ResultInterval.DAY,
                "http://this.is.a.callback.url"
        );

        Assert.assertEquals(new URI(fakeEndpoint + "/sessions/forecast?dataSetName=data-set-name&targetColumn=target-column&startDate=2017-12-12T10%3A11%3A12.000Z&endDate=2017-12-22T22%3A23%3A24.000Z&isEstimate=false&resultInterval=day&callbackUrl=http%3A%2F%2Fthis.is.a.callback.url"), post.getURI());
    }

    @Test
    public void SetsDataOnRequestWhenGiven() throws Exception {
        HttpPost post = new HttpPost();

        Map<String, String> row1 = new HashMap<>();
        row1.put("timestamp", DateTime.now().toDateTimeISO().toString());
        row1.put("sales", Double.toString(0.23));
        Map<String, String> row2 = new HashMap<>();
        row2.put("timestamp", DateTime.now().toDateTimeISO().toString());
        row2.put("sales", Double.toString(123.23));

        List<Map<String, String>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        DataSetData data = new DataSetData();
        data.setData(rows);

        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPost.class).withNoArguments().thenReturn(post);


        target.getSessions().createForecast(
                data,
                "target-column",
                DateTime.parse("2017-12-12T10:11:12Z"),
                DateTime.parse("2017-12-22T22:23:24Z"),
                ResultInterval.DAY,
                "http://this.is.a.callback.url"
        );

        Assert.assertEquals(post.getURI(), new URI(fakeEndpoint + "/sessions/forecast?targetColumn=target-column&startDate=2017-12-12T10%3A11%3A12.000Z&endDate=2017-12-22T22%3A23%3A24.000Z&isEstimate=false&resultInterval=day&callbackUrl=http%3A%2F%2Fthis.is.a.callback.url"));
        Assert.assertEquals(mapper.writeValueAsString(data), EntityUtils.toString(post.getEntity()));
    }

    @Test
    public void SerializesCSVToBodyWhenGiven() throws Exception {
        HttpPost post = new HttpPost();

        String fileContent = "timestamp,alpha,beta\r\n2017-01-01,10,14\r\n2017-01-02,11,13\r\n2017-01-03,12,12";
        InputStream stream = new ByteArrayInputStream(fileContent.getBytes("UTF8"));

        PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        PowerMockito.whenNew(HttpPost.class).withNoArguments().thenReturn(post);

        target.getSessions().createForecast(
                stream,
                "beta",
                DateTime.parse("2017-12-12T10:11:12Z"),
                DateTime.parse("2017-12-22T22:23:24Z"),
                ResultInterval.DAY,
                "http://this.is.a.callback.url"
        );

        Assert.assertEquals(new URI(fakeEndpoint + "/sessions/forecast?targetColumn=beta&startDate=2017-12-12T10%3A11%3A12.000Z&endDate=2017-12-22T22%3A23%3A24.000Z&isEstimate=false&resultInterval=day&callbackUrl=http%3A%2F%2Fthis.is.a.callback.url"),
                post.getURI());
        Assert.assertEquals(fileContent, EntityUtils.toString(post.getEntity()));
    }

    @Test
    public void ReqiresNotNullDataSet() throws NexosisClientException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object data cannot be null.");

        target.getSessions().createForecast(
                (DataSetData) null,
                "",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }

    @Test
    public void ReqiresNotNullOrEmptyDataSetName() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSetName cannot be null or empty.");

        target.getSessions().createForecast(
                (String) null,
                "",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }

    @Test
    public void ReqiresNotNullStreamReader() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object input cannot be null.");

        target.getSessions().createForecast(
                (InputStream) null,
                "",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }

    @Test
    public void ReqiresNotNullOrEmptyTargetColumn() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value targetColumn cannot be null or empty.");

        target.getSessions().createForecast(
                "dataSet",
                "",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }
}