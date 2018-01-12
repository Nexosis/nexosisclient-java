package com.nexosis;

import com.google.api.client.http.HttpStatusCodes;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SessionIntegrationTests {
    private static final String baseURI = System.getenv("NEXOSIS_API_TESTURI");
    private static final String absolutePath = System.getProperty("user.dir") + "/src/test/java/com/nexosis";
    private static String savedSessionData;
    private static UUID savedSessionId;
    private static NexosisClient nexosisClient;

    @BeforeClass
    public static void beforeClass() throws NexosisClientException, InterruptedException {
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);

        SessionResponses responses = nexosisClient.getSessions().list(new SessionQuery());
        for (SessionResponse session : responses.getItems()) {
            if (session.getStatus().equals(SessionStatus.COMPLETED)
                    && session.getType().equals(SessionType.FORECAST)) {
                savedSessionId = session.getSessionId();
                savedSessionData = session.getDataSourceName();
            }
        }
        if (savedSessionId == null) {
            savedSessionData = "testJavaSessionData";
            DataSetDetail dataSet = DataSetGenerator.Run(DateTime.now().plusDays(-120), DateTime.now(), "instances");
            nexosisClient.getDataSets().create(new DataSetDetailSource(savedSessionData, dataSet));

            ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
            forecastRequest.setDataSourceName(savedSessionData);
            forecastRequest.setTargetColumn("instances");
            forecastRequest.setStartDate(DateTime.now().plusDays(-30));
            forecastRequest.setEndDate(DateTime.now().plusDays(-20));
            forecastRequest.setResultInterval(ResultInterval.DAY);

            SessionResponse response = nexosisClient.getSessions().createForecast(forecastRequest);
            while (true) {
                SessionResultStatus sessionStatus = nexosisClient.getSessions().getStatus(response.getSessionId());
                if (sessionStatus.getStatus().equals(SessionStatus.COMPLETED)
                        || sessionStatus.getStatus().equals(SessionStatus.COMPLETED)) {
                    savedSessionId = response.getSessionId();
                    break;
                }
                TimeUnit.SECONDS.sleep(10);
            }
        }

        Assert.assertNotNull(savedSessionId);
        Assert.assertNotNull(savedSessionData);
    }

    @Test
    public void GetQuotaWillGiveItBack() throws NexosisClientException {
        AccountQuotas actual = nexosisClient.getAccountQuotas();

        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getDataSetCountAllotted());
        Assert.assertNotNull(actual.getDataSetCountCurrent());
        Assert.assertNotNull(actual.getPredictionCountAllotted());
        Assert.assertNotNull(actual.getPredictionCountCurrent());
        Assert.assertNotNull(actual.getSessionCountAllotted());
        Assert.assertNotNull(actual.getSessionCountCurrent());
    }

    @Test
    public void CreateForecastWithDataStartsNewSession() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetDetail dataSet = DataSetGenerator.Run(DateTime.now().plusDays(-120), DateTime.now(), "instances");

        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, dataSet));

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
        forecastRequest.setDataSourceName(dataSetName);
        forecastRequest.setColumns(cols);
        forecastRequest.setTargetColumn("instances");
        forecastRequest.setStartDate(DateTime.now().plusDays(-30));
        forecastRequest.setEndDate(DateTime.now().plusDays(-10));
        forecastRequest.setResultInterval(ResultInterval.DAY);

        // 2016-08-01 to 2017-03-26
        SessionResponse actual = nexosisClient.getSessions().createForecast(forecastRequest);

        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void ForcastFromSavedDataSetStartsNewSession() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetDetail dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances");

        // setup column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        // create dataset
        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, dataSet));

        ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
        forecastRequest.setDataSourceName(dataSetName);
        forecastRequest.setTargetColumn("instances");
        forecastRequest.setStartDate(DateTime.parse("2017-03-26T00:00:00Z"));
        forecastRequest.setEndDate(DateTime.parse("2017-04-25T00:00:00Z"));
        forecastRequest.setResultInterval(ResultInterval.DAY);

        // create Forecast
        SessionResponse actual = nexosisClient.getSessions().createForecast(forecastRequest);

        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void StartImpactWithDataStartsNewSession() throws NexosisClientException {
        String dataSourceName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetDetail dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances"
        );

        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSourceName, dataSet));

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setDataSourceName(dataSourceName);
        impactRequest.setTargetColumn("instances");
        impactRequest.setEventName("charlie-delta-" + DateTime.now().toString("yMsHms"));
        impactRequest.setColumns(cols);
        impactRequest.setStartDate(DateTime.parse("2016-11-26T00:00:00Z"));
        impactRequest.setEndDate(DateTime.parse("2016-12-25T00:00:00Z"));
        impactRequest.setResultInterval(ResultInterval.DAY);

        SessionResponse actual = nexosisClient.getSessions().analyzeImpact(impactRequest);
        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void StartImpactFromSavedDataSetStartsNewSession() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();

        DataSetDetail dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances");

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, dataSet));

        ForecastSessionRequest forecastRequest = new ForecastSessionRequest();
        forecastRequest.setDataSourceName(dataSetName);
        forecastRequest.setTargetColumn("instances");
        forecastRequest.setColumns(cols);
        forecastRequest.setStartDate(DateTime.parse("2017-03-26T00:00:00Z"));
        forecastRequest.setEndDate(DateTime.parse("2017-04-25T00:00:00Z"));
        forecastRequest.setResultInterval(ResultInterval.DAY);

        SessionResponse actual = nexosisClient.getSessions().createForecast(forecastRequest);

        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void GetSessionListHasItems() throws NexosisClientException {
        SessionResponses sessions = nexosisClient.getSessions().list(new SessionQuery());

        Assert.assertNotNull(sessions);
        Assert.assertTrue(sessions.getItems().size() > 0);
    }

    @Test
    public void GetSessionListRespectsPaging() throws NexosisClientException {
        SessionQuery query = new SessionQuery();
        query.setPage(new PagingInfo(1,2));
        SessionResponses sessions = nexosisClient.getSessions().list(query);

        Assert.assertNotNull(sessions);
        Assert.assertTrue(sessions.getPageNumber() == 1);
        Assert.assertTrue(sessions.getPageSize() == 2);
    }

    @Test
    public void GetSessionResultsHasResults() throws NexosisClientException {
        SessionResultQuery query = new SessionResultQuery();
        query.setSessionId(savedSessionId);
        SessionResult results = nexosisClient.getSessions().getResults(query);

        if (results.getStatus() != SessionStatus.COMPLETED) {
            Assert.fail("Session is not completed. Current status is " + results.getStatus() + ". Make sure to run SessionIntegrationTests.PopulateDataForTesting() and wait for the session to complete first.");
        }

        Assert.assertNotNull(results);
        Assert.assertEquals(savedSessionId, results.getSessionId());
        Assert.assertEquals(SessionStatus.COMPLETED, results.getStatus());
    }

    @Test
    public void GetSessionResultsHasLinks() throws NexosisClientException {
        SessionResultQuery query = new SessionResultQuery();
        query.setSessionId(savedSessionId);
        SessionResult result = nexosisClient.getSessions().getResults(query);
        if (result.getStatus() != SessionStatus.COMPLETED) {
            Assert.fail("Session is not completed. Current status is " + result.getStatus() + ". Make sure to run SessionIntegrationTests.PopulateDataForTesting() and wait for the session to complete first.");
        }

        Assert.assertNotNull(result);
        //NOTE: former tests covering counts are invalid. Results includes links for prediction intervals; which are not deterministic
        //across different sessions.
        Assert.assertEquals("self", result.getLinks().get(0).getRel());
        Assert.assertEquals(baseURI + "/sessions/" + savedSessionId + "/results", result.getLinks().get(0).getHref());
    }

    @Test
    public void GetSessionResultsWillWriteFile() throws NexosisClientException, IOException {
        String filename = absolutePath
                + "\\CsvFiles\\test-ouput-"
                + DateTimeFormat.forPattern("yyyyMMddhhmmss").withZoneUTC().print(DateTime.now())
                + ".csv";

        OutputStream output = null;
        try {
            File workingFile = new File(filename);
            workingFile.createNewFile();

            output = new FileOutputStream(filename);

            SessionResultQuery query = new SessionResultQuery();
            query.setSessionId(savedSessionId);
            query.setContentType("text/csv");
            ReturnsStatus status = nexosisClient.getSessions().getResults(query, output);

            if (status.getSessionStatus() != SessionStatus.COMPLETED) {
                Assert.fail("Session is not completed. Current status is " + status.getSessionStatus() + ". Make sure to run DataSetIntegrationTests.PopulateDataForTesting() and wait for the session to complete first.");
                return;
            }

            File file = new File(filename);
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            String results = new String(encoded, StandardCharsets.UTF_8);

            Assert.assertTrue(file.length() > 0);
            Assert.assertTrue(results.toLowerCase().startsWith("timestamp,".toLowerCase()));
        } finally {
            if (output != null) {
                output.flush();
                output.close();
            }
            if (Files.exists(Paths.get(filename))) {
                Files.delete(Paths.get(filename));
            }
        }
    }

    @Test
    public void DeletingSessionThenQueryingReturns404() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetDetail dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01", DateTimeFormat.forPattern("yyyy-MM-dd")),
                DateTime.parse("2017-03-26", DateTimeFormat.forPattern("yyyy-MM-dd")),
                "instances"
        );

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, dataSet));

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setDataSourceName(dataSetName);
        impactRequest.setTargetColumn("instances");
        impactRequest.setEventName("charlie-delta-" + DateTime.now().toString("yMsHms"));
        impactRequest.setColumns(cols);
        impactRequest.setStartDate(DateTime.parse("2016-11-26", DateTimeFormat.forPattern("yyyy-MM-dd")));
        impactRequest.setEndDate(DateTime.parse("2016-12-25", DateTimeFormat.forPattern("yyyy-MM-dd")));
        impactRequest.setResultInterval(ResultInterval.DAY);

        SessionResponse actual = nexosisClient.getSessions().analyzeImpact(impactRequest);

        nexosisClient.getSessions().remove(actual.getSessionId());

        try {
            nexosisClient.getSessions().get(actual.getSessionId());
        } catch (NexosisClientException nce) {
            // SHOULD FAIL
            Assert.assertEquals(nce.getStatusCode(), HttpStatusCodes.STATUS_CODE_NOT_FOUND);
            return;
        }

        Assert.fail();
    }

    @Test
    public void CheckingSessionStatusReturnsExpectedValue() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetDetail dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances"
        );

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, dataSet));

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setDataSourceName(dataSetName);
        impactRequest.setTargetColumn("instances");
        impactRequest.setEventName("charlie-delta-" + DateTime.now().toString("yMsHms"));
        impactRequest.setColumns(cols);
        impactRequest.setStartDate(DateTime.parse("2016-11-26", DateTimeFormat.forPattern("yyyy-MM-dd")));
        impactRequest.setEndDate(DateTime.parse("2016-12-25", DateTimeFormat.forPattern("yyyy-MM-dd")));
        impactRequest.setResultInterval(ResultInterval.DAY);

        SessionResponse actual = nexosisClient.getSessions().analyzeImpact(impactRequest);
        SessionResultStatus status = nexosisClient.getSessions().getStatus(actual.getSessionId());

        Assert.assertTrue((status.getStatus() == SessionStatus.REQUESTED || status.getStatus() == SessionStatus.STARTED) );
    }

    @Test
    public void CanRemoveMultipleSessions() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();

        DataSetDetail dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances"
        );

        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, dataSet));

        ImpactSessionRequest impactRequest = new ImpactSessionRequest();
        impactRequest.setDataSourceName(dataSetName);
        impactRequest.setTargetColumn("instances");
        impactRequest.setEventName("juliet-juliet-echo-1");
        impactRequest.setStartDate(DateTime.parse("2016-11-26T00:00:00Z"));
        impactRequest.setEndDate(DateTime.parse("2016-12-25T00:00:00Z"));
        impactRequest.setResultInterval(ResultInterval.DAY);

        SessionResponse first = nexosisClient.getSessions().analyzeImpact(impactRequest);

        ImpactSessionRequest impactRequest2 = new ImpactSessionRequest();
        impactRequest2.setDataSourceName(dataSetName);
        impactRequest2.setTargetColumn("instances");
        impactRequest2.setEventName("juliet-juliet-echo-2");
        impactRequest2.setStartDate(DateTime.parse("2016-11-26T00:00:00Z"));
        impactRequest2.setEndDate(DateTime.parse("2016-12-25T00:00:00Z"));
        impactRequest2.setResultInterval(ResultInterval.DAY);
        SessionResponse second = nexosisClient.getSessions().analyzeImpact(impactRequest2);

        SessionRemoveCriteria criteria = new SessionRemoveCriteria();
        criteria.setType(SessionType.IMPACT);
        criteria.setEventName("juliet-juliet-echo-");
        nexosisClient.getSessions().remove(criteria);

        NexosisClientException exceptionTheFirst = null;
        NexosisClientException exceptionTheSecond = null;

        try {
            nexosisClient.getSessions().get(first.getSessionId());
        } catch (NexosisClientException nce) {
            exceptionTheFirst = nce;
        }

        try {
            nexosisClient.getSessions().get(second.getSessionId());
        } catch (NexosisClientException nce) {
            exceptionTheSecond = nce;
        }

        Assert.assertEquals(exceptionTheFirst.getStatusCode(), HttpStatusCodes.STATUS_CODE_NOT_FOUND);
        Assert.assertEquals(exceptionTheSecond.getStatusCode(), HttpStatusCodes.STATUS_CODE_NOT_FOUND);
    }

    @Test
    public void SessionsHavePredictionIntervals() throws NexosisClientException {
        SessionResponse session = nexosisClient.getSessions().get(savedSessionId);
        Assert.assertNotNull(session.getAvailablePredictionIntervals());
    }

    public void CanRequestDifferentPredictionIntervals() throws NexosisClientException {
        SessionResponses sessions = nexosisClient.getSessions().list(new SessionQuery());
        SessionResponse targetSession = null;
        for (SessionResponse r : sessions.getItems()) {
            if (r.getType().equals(SessionType.FORECAST) &&
                    r.getAvailablePredictionIntervals().length > 1) {
                targetSession = r;
                break;
            }
        }
        if (targetSession == null) {
            Assert.fail("No session exists with more than default prediction interval. Test target should to be setup.");
            return;
        }

        SessionResultQuery query1 = new SessionResultQuery();
        query1.setSessionId(targetSession.getSessionId());
        query1.setPredictionInterval(targetSession.getAvailablePredictionIntervals()[0]);

        SessionResultQuery query2 = new SessionResultQuery();
        query2.setSessionId(targetSession.getSessionId());
        query2.setPredictionInterval(targetSession.getAvailablePredictionIntervals()[2]);

        SessionResult pi1 = nexosisClient.getSessions().getResults(query1);
        SessionResult pi2 = nexosisClient.getSessions().getResults(query2);

        String key = pi1.getTargetColumn();
        for (Integer index = 0; index < pi1.getData().size(); index++) {
            Assert.assertNotEquals(pi1.getData().get(index).get(key),
                                    pi2.getData().get(index).get(key));
        }
    }
}