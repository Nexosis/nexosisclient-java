package com.nexosis.SessionTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.ConfusionMatrixResponse;
import com.nexosis.model.DistanceMetricResponse;
import com.nexosis.model.SessionResultQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.UUID;

public class GetResultsTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String fakeEndpoint = "https://nada.nexosis.com/not-here";
    private String fakeApiKey = "abcdefg";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getResultsReturnsThem() throws Exception
    {
        UUID sessionId = UUID.randomUUID();

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
        SessionResultQuery query = new SessionResultQuery();
        query.setSessionId(sessionId);
        target.getSessions().getResults(query);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results", request.getUrl());
    }

    @Test
    public void getResultToFileThrowsWithNullWriter() throws Exception
    {
        UUID sessionId = UUID.randomUUID();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Object output cannot be null.");

        NexosisClient target = new NexosisClient(fakeApiKey, fakeEndpoint);
        SessionResultQuery query = new SessionResultQuery();
        query.setSessionId(sessionId);
        target.getSessions().getResults(query, null);
    }

    @Test
    public void getConfusionMatrixBuildsClass() throws Exception{
        final UUID sessionId = UUID.randomUUID();
        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent("{\"sessionId\":\"629f4d80-ac9d-416d-9b4a-61db74ed504a\", \"confusionMatrix\": [[0,1],[1,0]], \"classes\": [0,1]}");
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
        ConfusionMatrixResponse response = target.getSessions().getConfusionMatrix(sessionId);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getClasses()[0], "0");
        Assert.assertEquals(response.getConfusionMatrix()[0][1], 1);
    }

    @Test
    public void getConfusionMatrixUrlIsCorrect() throws Exception
    {
        UUID sessionId = UUID.randomUUID();

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
        target.getSessions().getConfusionMatrix(sessionId);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/confusionmatrix", request.getUrl());
    }

    @Test
    public void GetClassScoresReturnsThen() throws Exception
    {
        UUID sessionId = UUID.randomUUID();

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
        target.getSessions().getResultClassScores(sessionId, null);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/classscores", request.getUrl());
    }

    @Test
    public void getAnomalyScoresReturnsThen() throws Exception {
        UUID sessionId = UUID.randomUUID();

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
        target.getSessions().getResultAnomalyScores(sessionId, null);

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/anomalyscores", request.getUrl());
    }

    @Test
    public void getDistanceMetricsReturnsThen() throws Exception {
        UUID sessionId = UUID.randomUUID();
        final String responseString = "{\"metrics\":{\"percentAnomalies\":0.101123594},\"data\":[{\"anomaly\":\"0.0487072268545709\",\"Ash\":\"2\",\"Hue\":\"0.93\",\"Alcohol\":\"12\",\"ODRatio\":\"3.05\",\"Proline\":\"564\",\"Magnesium\":\"87\",\"MalicAcid\":\"3.43\",\"Aclalinity\":\"19\",\"Flavanoids\":\"1.64\",\"TotalPhenols\":\"2\",\"ColorIntensity\":\"1.28\",\"Proanthocyanins\":\"1.87\",\"NonFlavanoidPhenols\":\"0.37\",\"mahalanobis_distance\":\"143.312589889491\"},{\"anomaly\":\"0.000317797613019206\",\"Ash\":\"2.28\",\"Hue\":\"1.25\",\"Alcohol\":\"12.33\",\"ODRatio\":\"1.67\",\"Proline\":\"680\",\"Magnesium\":\"101\",\"MalicAcid\":\"1.1\",\"Aclalinity\":\"16\",\"Flavanoids\":\"1.09\",\"TotalPhenols\":\"2.05\",\"ColorIntensity\":\"3.27\",\"Proanthocyanins\":\"0.41\",\"NonFlavanoidPhenols\":\"0.63\",\"mahalanobis_distance\":\"156.112291933161\"}],\"pageNumber\":0,\"totalPages\":89,\"pageSize\":2,\"totalCount\":178,\"sessionId\":\"0162b0ff-7adb-4c38-bca0-89794b759f14\",\"type\":\"model\",\"status\":\"completed\",\"predictionDomain\":\"anomalies\",\"supportsFeatureImportance\":false,\"availablePredictionIntervals\":[],\"modelId\":\"5c874326-6b38-4c0e-86ef-c0b50689a1a1\",\"requestedDate\":\"2018-04-10T19:19:15.894879+00:00\",\"statusHistory\":[{\"date\":\"2018-04-10T19:19:15.894879+00:00\",\"status\":\"requested\"},{\"date\":\"2018-04-10T19:19:16.07002+00:00\",\"status\":\"started\"},{\"date\":\"2018-04-10T19:19:33.1405011+00:00\",\"status\":\"completed\"}],\"extraParameters\":{\"containsAnomalies\":true},\"messages\":[{\"severity\":\"informational\",\"message\":\"178 observations were found in the dataset.\"}],\"name\":\"Anomalies on Wine\",\"dataSourceName\":\"Wine\",\"dataSetName\":\"Wine\",\"targetColumn\":\"anomaly\",\"algorithm\":{\"name\":\"Isolation Forest\",\"description\":\"Isolation Forest Outlier Detection\",\"key\":\"\"},\"isEstimate\":false,\"links\":[]}";
        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                response.setStatusCode(200);
                response.setContentType(Json.MEDIA_TYPE);
                response.setContent(responseString);
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
        DistanceMetricResponse response = target.getSessions().getDistanceMetrics(sessionId, null);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/mahalanobisdistances", request.getUrl());
    }
}
