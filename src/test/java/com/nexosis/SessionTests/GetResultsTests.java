package com.nexosis.SessionTests;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.*;
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

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/classscores?pageSize=50", request.getUrl());
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

        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/anomalyscores?pageSize=50", request.getUrl());
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
        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/mahalanobisdistances?pageSize=50", request.getUrl());
    }

    @Test
    public void getFeatureImportanceReturnsThen() throws Exception {
        UUID sessionId = UUID.randomUUID();
        final String responseString = "{\"featureImportance\":{\"cali\":0.0792953,\"ny:0\":0.089632705,\"ny:1\":0.092574164,\"florida\":0.09888547,\"R.D.Spend\":1,\"Administration\":0.69214404,\"Marketing.Spend\":0.95147604},\"pageNumber\":0,\"totalPages\":1,\"pageSize\":50,\"totalCount\":7,\"sessionId\":\"0162b010-4e5f-48d5-a4bb-16a7cf23a02d\",\"type\":\"model\",\"status\":\"completed\",\"predictionDomain\":\"regression\",\"supportsFeatureImportance\":true,\"availablePredictionIntervals\":[\"0.5\"],\"modelId\":\"8005a303-1bdb-427e-bc3f-e26f1e52fa8e\",\"requestedDate\":\"2018-04-10T14:58:01.403083+00:00\",\"statusHistory\":[{\"date\":\"2018-04-10T14:58:01.403083+00:00\",\"status\":\"requested\"},{\"date\":\"2018-04-10T14:58:01.5409783+00:00\",\"status\":\"started\"},{\"date\":\"2018-04-10T15:36:24.2317973+00:00\",\"status\":\"completed\"}],\"extraParameters\":{},\"messages\":[{\"severity\":\"informational\",\"message\":\"200 observations were found in the dataset.\"}],\"name\":\"Regression on 50Startups\",\"dataSourceName\":\"50Startups\",\"dataSetName\":\"50Startups\",\"targetColumn\":\"Profit\",\"algorithm\":{\"name\":\"XGBoost L1\",\"description\":\"eXtreme Gradient Boosting with lasso regularization\",\"key\":\"\"},\"isEstimate\":false,\"links\":[{\"rel\":\"data\",\"href\":\"https://api.uat.nexosisdev.com/v1/data/50Startups\"},{\"rel\":\"first\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b010-4e5f-48d5-a4bb-16a7cf23a02d/results/featureimportance?page=0\"},{\"rel\":\"last\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b010-4e5f-48d5-a4bb-16a7cf23a02d/results/featureimportance?page=0\"},{\"rel\":\"self\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b010-4e5f-48d5-a4bb-16a7cf23a02d/results/featureimportance\"},{\"rel\":\"prediction-interval:0.5\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b010-4e5f-48d5-a4bb-16a7cf23a02d?predictionInterval=0.5\"},{\"rel\":\"model\",\"href\":\"https://api.uat.nexosisdev.com/v1/models/8005a303-1bdb-427e-bc3f-e26f1e52fa8e\"},{\"rel\":\"contest\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b010-4e5f-48d5-a4bb-16a7cf23a02d/contest\"},{\"rel\":\"featureImportance\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b010-4e5f-48d5-a4bb-16a7cf23a02d/results/featureimportance\"},{\"rel\":\"vocabularies\",\"href\":\"https://api.uat.nexosisdev.com/v1/vocabulary?createdFromSession=0162b010-4e5f-48d5-a4bb-16a7cf23a02d\"}]}";
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
        FeatureImportanceResponse response = target.getSessions().getFeatureImportanceScores(sessionId, null);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getScores());
        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/featureimportance?pageSize=50", request.getUrl());
    }

    @Test
    public void getTimeseriesOutliersReturnsThen() throws Exception {
        UUID sessionId = UUID.randomUUID();
        final String responseString = "{\"data\":[{\"timeStamp\":\"2011-10-05T14:48:00.000Z\",\"sales:actual\":\"229.09\",\"sales:smooth\":\"1743.42167102697\"},{\"timeStamp\":\"2011-10-07T14:48:00.000Z\",\"sales:actual\":\"0\",\"sales:smooth\":\"1920.29538270229\"}],\"pageNumber\":0,\"totalPages\":1,\"pageSize\":50,\"totalCount\":2,\"sessionId\":\"0162b017-328b-48b7-abcb-70406b3f480e\",\"type\":\"forecast\",\"status\":\"completed\",\"predictionDomain\":\"forecast\",\"supportsFeatureImportance\":true,\"availablePredictionIntervals\":[\"0.01\",\"0.05\",\"0.2\",\"0.5\",\"0.8\",\"0.95\",\"0.99\"],\"startDate\":\"2017-01-22T00:00:00+00:00\",\"endDate\":\"2017-01-30T00:00:00+00:00\",\"resultInterval\":\"day\",\"requestedDate\":\"2018-04-10T15:05:33.024901+00:00\",\"statusHistory\":[{\"date\":\"2018-04-10T15:05:33.024901+00:00\",\"status\":\"requested\"},{\"date\":\"2018-04-10T15:05:33.0633488+00:00\",\"status\":\"started\"},{\"date\":\"2018-04-10T15:34:28.9317356+00:00\",\"status\":\"completed\"}],\"extraParameters\":{},\"messages\":[{\"severity\":\"warning\",\"message\":\"You specified imputation 'mode' and aggregation 'sum' for column 'transactions'. You may wish to make these consistent\"},{\"severity\":\"informational\",\"message\":\"1476 observations were found in the dataset.\"},{\"severity\":\"informational\",\"message\":\"1476 daily observations were found in the dataset before 2017-01-30T23:59:59.9999999Z.\"}],\"name\":\"Forecast on LocationA\",\"dataSourceName\":\"LocationA\",\"dataSetName\":\"LocationA\",\"targetColumn\":\"sales\",\"algorithm\":{\"name\":\"Autoregressive Neural Network w/ Exogenous Variables, Annual\",\"description\":\"Forecasts using feed-forward neural networks with a single hidden layer and lagged inputs, with annual seasonality\",\"key\":\"\"},\"isEstimate\":false,\"links\":[{\"rel\":\"data\",\"href\":\"https://api.uat.nexosisdev.com/v1/data/LocationA\"},{\"rel\":\"first\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e/results/outliers?page=0\"},{\"rel\":\"last\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e/results/outliers?page=0\"},{\"rel\":\"self\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e/results/outliers\"},{\"rel\":\"prediction-interval:0.01\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.01\"},{\"rel\":\"prediction-interval:0.05\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.05\"},{\"rel\":\"prediction-interval:0.2\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.2\"},{\"rel\":\"prediction-interval:0.5\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.5\"},{\"rel\":\"prediction-interval:0.8\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.8\"},{\"rel\":\"prediction-interval:0.95\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.95\"},{\"rel\":\"prediction-interval:0.99\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e?predictionInterval=0.99\"},{\"rel\":\"contest\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e/contest\"},{\"rel\":\"featureImportance\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e/results/featureimportance\"},{\"rel\":\"outliers\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b017-328b-48b7-abcb-70406b3f480e/results/outliers\"},{\"rel\":\"vocabularies\",\"href\":\"https://api.uat.nexosisdev.com/v1/vocabulary?createdFromSession=0162b017-328b-48b7-abcb-70406b3f480e\"}]}";
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
        OutliersResponse response = target.getSessions().getTimeseriesOutliers(sessionId, null);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getOutliers());
        Assert.assertEquals(fakeEndpoint + "/sessions/" + sessionId + "/results/outliers?pageSize=50", request.getUrl());
    }

}
