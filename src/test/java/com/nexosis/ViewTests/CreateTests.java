package com.nexosis.ViewTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.neovisionaries.i18n.CountryCode;
import com.nexosis.impl.ApiConnection;
import com.nexosis.impl.NexosisClient;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.TimeZone;

public class CreateTests {

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
    public void addsCalendarIntoJoinByName() throws Exception {
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getViews().create("TestCalendarView", "TestDataSet", CountryCode.US, null, null);

        Assert.assertNotNull(request.getStreamingContent());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"calendar\":{\"name\":\"Nexosis.Holidays-US\"}}]}",
                mapper.writeValueAsString( ((JsonHttpContent)request.getStreamingContent()).getData()));
    }

    @Test
    public void addsCalendarIntoJoinByUri() throws Exception{
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getViews().create("TestCalendarView", "TestDataSet", URI.create("http://nothere.com/somecal/holidays.ical"), null,null);

        Assert.assertNotNull(request.getStreamingContent());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"calendar\":{\"url\":\"http://nothere.com/somecal/holidays.ical\"}}]}",
                mapper.writeValueAsString( ((JsonHttpContent)request.getStreamingContent()).getData()));
    }

    @Test
    public void usesTimeZoneIfPresent() throws Exception{
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");

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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getViews().create("TestCalendarView", "TestDataSet", URI.create("http://nothere.com/somecal/holidays.ical"), tz,null);

        Assert.assertNotNull(request.getStreamingContent());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"calendar\":{\"url\":\"http://nothere.com/somecal/holidays.ical\",\"timeZone\":\"America/Los_Angeles\"}}]}",
                mapper.writeValueAsString( ((JsonHttpContent)request.getStreamingContent()).getData()));
    }

    @Test
    public void addsDataSetIntoJoinByName() throws Exception{
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                request.setUrl(url);
                return request;
            }
        };

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint, transport);
        target.getViews().create("TestCalendarView", "TestDataSet", "RightDataSet", null);

        Assert.assertNotNull(request.getStreamingContent());
        Assert.assertEquals("{\"viewName\":\"TestCalendarView\",\"dataSetName\":\"TestDataSet\",\"joins\":[{\"dataSet\":{\"name\":\"RightDataSet\"}}]}",
                mapper.writeValueAsString( ((JsonHttpContent)request.getStreamingContent()).getData()));
    }
}
