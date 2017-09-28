package com.nexosis.ImportTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.nexosis.impl.ApiConnection;
//import com.nexosis.impl.HttpClientFactory;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
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

import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiConnection.class})
public class PostTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@Mock
    //private HttpClientFactory httpClientFactory;
    //@Mock
    //private CloseableHttpClient httpClient;
    //@Mock
    //private CloseableHttpResponse httpResponse;
    //@Mock
    //private HttpEntity httpEntity;
    //@Mock
    //private StatusLine statusLine;

    private ObjectMapper mapper = new ObjectMapper();
    private NexosisClient target;
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";
    private URI apiFakeEndpointUri;

    @Before
    public void setUp() throws Exception {
        target = new NexosisClient(fakeApiKey, fakeEndpoint);
        apiFakeEndpointUri = new URI(fakeEndpoint);

        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //PowerMockito.when(httpClientFactory.createClient()).thenReturn(httpClient);
        //PowerMockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        //PowerMockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("{}".getBytes()));
        //PowerMockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        //PowerMockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
    }

    @Test
    public void importSendsDataAsBody() throws Exception {
        String dataSetName, bucket, region, path;
        dataSetName = "TestData";
        bucket = "bucket";
        region = "region-1";
        path = "path.csv";
        //HttpPost post = new HttpPost();
        ImportData data = new ImportData();
        data.setDataSetName(dataSetName);
        data.setPath(path);
        data.setBucket(bucket);
        data.setRegion(region);
        //PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
        //PowerMockito.whenNew(HttpPost.class).withNoArguments().thenReturn(post);
        Columns columns = new Columns();
        columns.setColumnMetadata("TestColumn", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        data.setColumns(columns);
        ImportDetail actual = target.getImports().importFromS3(dataSetName, bucket, path, region, columns);
        //Assert.assertEquals(mapper.writeValueAsString(data), EntityUtils.toString(post.getEntity()));
    }
}
