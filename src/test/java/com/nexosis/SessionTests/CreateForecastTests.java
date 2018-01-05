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
import com.nexosis.model.*;
import com.nexosis.util.JodaTimeHelper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CreateForecastTests {
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
    public void SetsDataSetNameWhenGiven() throws Exception {
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

        ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
        forecastRequest.setDataSourceName("data-set-name");
        forecastRequest.setTargetColumn("target-column");
        forecastRequest.setStartDate(DateTime.parse("2017-12-12T10:11:12Z"));
        forecastRequest.setEndDate(DateTime.parse("2017-12-22T22:23:24Z"));
        forecastRequest.setResultInterval(ResultInterval.DAY);
        forecastRequest.setCallbackUrl("http://this.is.a.callback.url");

        target.getSessions().createForecast(forecastRequest);

        Assert.assertEquals(fakeEndpoint + "/sessions/forecast", request.getUrl());
        Assert.assertEquals(mapper.writeValueAsString(forecastRequest), request.getContentAsString());
    }

    @Test
    public void SetsColumnsOnRequestWhenGiven() throws Exception {
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

        ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
        forecastRequest.setDataSourceName("data-set-name");
        forecastRequest.setTargetColumn("target-column");
        forecastRequest.setStartDate(DateTime.parse("2017-12-12T10:11:12Z"));
        forecastRequest.setEndDate(DateTime.parse("2017-12-22T22:23:24Z"));
        forecastRequest.setResultInterval(ResultInterval.DAY);
        forecastRequest.setCallbackUrl("http://this.is.a.callback.url");

        target.getSessions().createForecast(forecastRequest);

        Assert.assertEquals(request.getUrl(), fakeEndpoint + "/sessions/forecast");
        Assert.assertEquals(mapper.writeValueAsString(forecastRequest), request.getContentAsString());
    }

    @Test
    public void RequiresNotNullDataSet() throws NexosisClientException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object ForecastSessionRequest cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        target.getSessions().createForecast(null);
    }

    @Test
    public void RequiresNotNullOrEmptyDataSourceName() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value ForecastSessionRequest.dataSourceName cannot be null or empty.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
        forecastRequest.setTargetColumn("target-column");
        forecastRequest.setStartDate(JodaTimeHelper.START_OF_TIME);
        forecastRequest.setEndDate(JodaTimeHelper.END_OF_TIME);
        forecastRequest.setResultInterval(ResultInterval.DAY);

        target.getSessions().createForecast(forecastRequest);
    }
}