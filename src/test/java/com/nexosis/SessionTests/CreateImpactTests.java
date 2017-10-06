package com.nexosis.SessionTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.JodaTimeHelper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;

public class CreateImpactTests {

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

    @Test
    public void SetsDataSetNameWhenGiven() throws Exception
    {
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
        target.getSessions().analyzeImpact(
                "data-set-name",
                "event-name",
                "target-column",
                DateTime.parse("2017-12-12T10:11:12Z"),
                DateTime.parse("2017-12-22T22:23:24Z"),
                ResultInterval.DAY,
                "http://this.is.a.callback.url"
        );

        Assert.assertEquals(fakeEndpoint + "/sessions/impact?resultInterval=day&endDate=2017-12-22T22:23:24.000Z&isEstimate=false&eventName=event-name&" +
                "callbackUrl=http://this.is.a.callback.url&dataSourceName=data-set-name&startDate=2017-12-12T10:11:12.000Z", request.getUrl());
    }

    @Test
    public void SetsColumnsOnRequestWhenGiven() throws Exception
    {
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData data = new SessionData();
        data.setDataSourceName("data-set-name");
        data.setColumns(cols);

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
        target.getSessions().analyzeImpact(
                data,
                "event-name",
                DateTime.parse("2017-12-12T10:11:12Z"),
                DateTime.parse("2017-12-22T22:23:24Z"),
                ResultInterval.DAY,
                "http://this.is.a.callback.url"
        );

        Assert.assertEquals(fakeEndpoint + "/sessions/impact?resultInterval=day&endDate=2017-12-22T22:23:24.000Z&isEstimate=false&eventName=event-name&callbackUrl=http://this.is.a.callback.url&dataSourceName=data-set-name&startDate=2017-12-12T10:11:12.000Z", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(data), request.getContentAsString());
    }

    @Test
    public void ReqiresNotNullDataSet() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object data cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().analyzeImpact(
                (SessionData) null,
                "event",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }

    @Test
    public void ReqiresNotNullOrEmptyDataSourceName() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value dataSourceName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().analyzeImpact(
                (String) null,
                "event",
                "",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }

    @Test
    public void ReqiresNotNullOrEmptyTargetColumn() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value targetColumn cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().analyzeImpact(
                "dataSet",
                "event",
                "",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }

    @Test
    public void ReqiresNotNullOrEmptyEventName() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value eventName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().analyzeImpact(
                "dataSet",
                "",
                "targetCol",
                JodaTimeHelper.START_OF_TIME,
                JodaTimeHelper.END_OF_TIME,
                ResultInterval.DAY
        );
    }
}