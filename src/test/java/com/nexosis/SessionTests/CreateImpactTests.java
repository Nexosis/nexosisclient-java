package com.nexosis.SessionTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.impl.Sessions;
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

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setDataSourceName("data-set-name");
        impactRequest.setEventName("event-name");
        impactRequest.setTargetColumn("target-column");
        impactRequest.setStartDate(DateTime.parse("2017-12-12T10:11:12Z"));
        impactRequest.setEndDate(DateTime.parse("2017-12-22T22:23:24Z"));
        impactRequest.setResultInterval( ResultInterval.DAY);
        impactRequest.setCallbackUrl("http://this.is.a.callback.url");

        target.getSessions().analyzeImpact(impactRequest);

        Assert.assertEquals(fakeEndpoint + "/sessions/impact", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(impactRequest), request.getContentAsString());
    }

    @Test
    public void SetsColumnsOnRequestWhenGiven() throws Exception
    {
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

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

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setCallbackUrl("http://this.is.a.callback.url");

        target.getSessions().analyzeImpact(
                Sessions.Impact(
                        "data-set-name",
                        DateTime.parse("2017-12-12T10:11:12Z"),
                        DateTime.parse("2017-12-22T22:23:24Z"),
                        ResultInterval.DAY,
                        "event-name",
                        "target-column",
                        impactRequest
                )
        );

        Assert.assertEquals(fakeEndpoint + "/sessions/impact", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(impactRequest), request.getContentAsString());
    }

    @Test
    public void RequiresNotNullDataSet() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object ImpactSessionRequest cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        target.getSessions().analyzeImpact(null);
    }

    @Test
    public void RequiresNotNullOrEmptyDataSourceName() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ImpactSessionRequest.dataSourceName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setEventName("event");
        impactRequest.setTargetColumn("target");
        impactRequest.setStartDate(JodaTimeHelper.START_OF_TIME);
        impactRequest.setEndDate(JodaTimeHelper.END_OF_TIME);
        impactRequest.setResultInterval(ResultInterval.DAY);

        target.getSessions().analyzeImpact(impactRequest);
    }

    @Test
    public void RequiresNotNullOrEmptyEventName() throws NexosisClientException
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ImpactSessionRequest.eventName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setDataSourceName("datasource");
        impactRequest.setTargetColumn("target");
        impactRequest.setStartDate(JodaTimeHelper.START_OF_TIME);
        impactRequest.setEndDate(JodaTimeHelper.END_OF_TIME);
        impactRequest.setResultInterval(ResultInterval.DAY);

        target.getSessions().analyzeImpact(impactRequest);
    }
}