package com.nexosis;

import com.google.api.client.http.HttpStatusCodes;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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
        SessionResponses responses = nexosisClient.getSessions().list();
        for (SessionResponse session : responses.getItems()) {
            if (session.getStatus().equals(SessionStatus.COMPLETED)
                    && session.getType().equals(SessionType.FORECAST)) {
                savedSessionId = session.getSessionId();
                savedSessionData = session.getDataSourceName();
            }
        }
        if (savedSessionId == null) {
            savedSessionData = "testJavaSessionData";
            DataSetData dataSet = DataSetGenerator.Run(DateTime.now().plusDays(-120), DateTime.now(), "instances");
            nexosisClient.getDataSets().create(savedSessionData, dataSet);
            SessionResponse response = nexosisClient.getSessions().createForecast(savedSessionData
                    , "instances"
                    , DateTime.now().plusDays(-30)
                    , DateTime.now().plusDays(-20)
                    , ResultInterval.DAY
            );
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
        DataSetData dataSet = DataSetGenerator.Run(DateTime.now().plusDays(-120), DateTime.now(), "instances");

        nexosisClient.getDataSets().create(dataSetName, dataSet);

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData session = new SessionData();
        session.setDataSourceName(dataSetName);
        session.setColumns(cols);

        // 2016-08-01 to 2017-03-26
        SessionResponse actual = nexosisClient.getSessions().createForecast(
                session,
                DateTime.now().plusDays(-30),
                DateTime.now().plusDays(-10),
                ResultInterval.DAY
        );

        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void ForcastFromSavedDataSetStartsNewSession() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetData dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances");

        // setup column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        // create dataset
        nexosisClient.getDataSets().create(dataSetName, dataSet);

        // create Forecast
        SessionResponse actual = nexosisClient.getSessions().createForecast(
                dataSetName,
                "instances",
                DateTime.parse("2017-03-26T00:00:00Z"),
                DateTime.parse("2017-04-25T00:00:00Z"),
                ResultInterval.DAY
        );

        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void StartImpactWithDataStartsNewSession() throws NexosisClientException {
        String dataSourceName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();
        DataSetData dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances"
        );

        nexosisClient.getDataSets().create(dataSourceName, dataSet);

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData session = new SessionData();
        session.setDataSourceName(dataSourceName);
        session.setColumns(cols);

        SessionResponse actual = nexosisClient.getSessions().analyzeImpact(
                session,
                "charlie-delta-{DateTime.UtcNow:s}",
                DateTime.parse("2016-11-26T00:00:00Z"),
                DateTime.parse("2016-12-25T00:00:00Z"),
                ResultInterval.DAY
        );
        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void StartImpactFromSavedDataSetStartsNewSession() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();

        DataSetData dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances");

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        nexosisClient.getDataSets().create(dataSetName, dataSet);

        SessionResponse actual = nexosisClient.getSessions().createForecast(
                dataSetName,
                "instances",
                DateTime.parse("2017-03-26T00:00:00Z"),
                DateTime.parse("2017-04-25T00:00:00Z"),
                ResultInterval.DAY
        );

        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getSessionId());
    }

    @Test
    public void GetSessionListHasItems() throws NexosisClientException {
        SessionResponses sessions = nexosisClient.getSessions().list();

        Assert.assertNotNull(sessions);
        Assert.assertTrue(sessions.getItems().size() > 0);
    }

    @Test
    public void GetSessionListRespectsPaging() throws NexosisClientException {
        ListQuery query = new ListQuery();
        query.setPageSize(1);
        query.setPageNumber(2);
        SessionResponses sessions = nexosisClient.getSessions().list(query);

        Assert.assertNotNull(sessions);
        Assert.assertTrue(sessions.getItems().size() == 1);
    }

    @Test
    public void GetSessionResultsHasResults() throws NexosisClientException {
        SessionResult results = nexosisClient.getSessions().getResults(savedSessionId);

        if (results.getStatus() != SessionStatus.COMPLETED) {
            Assert.fail("Session is not completed. Current status is " + results.getStatus() + ". Make sure to run SessionIntegrationTests.PopulateDataForTesting() and wait for the session to complete first.");
        }

        Assert.assertNotNull(results);
        Assert.assertEquals(savedSessionId, results.getSessionId());
        Assert.assertEquals(SessionStatus.COMPLETED, results.getStatus());
    }

    @Test
    public void GetSessionResultsHasLinks() throws NexosisClientException {
        SessionResult result = nexosisClient.getSessions().getResults(savedSessionId);
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
            ReturnsStatus status = nexosisClient.getSessions().getResults(savedSessionId, output);

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
        DataSetData dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01", DateTimeFormat.forPattern("yyyy-MM-dd")),
                DateTime.parse("2017-03-26", DateTimeFormat.forPattern("yyyy-MM-dd")),
                "instances"
        );

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        nexosisClient.getDataSets().create(dataSetName, dataSet);

        SessionResponse actual = nexosisClient.getSessions().analyzeImpact(
                dataSetName,
                "charlie-delta-" + DateTime.now().toDateTimeISO().toString(),
                "instances",
                DateTime.parse("2016-11-26", DateTimeFormat.forPattern("yyyy-MM-dd")),
                DateTime.parse("2016-12-25", DateTimeFormat.forPattern("yyyy-MM-dd")),
                ResultInterval.DAY
        );

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
        DataSetData dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances"
        );

        // setup some column metadata
        Columns cols = new Columns();
        cols.setColumnMetadata("timestamp", DataType.DATE, DataRole.TIMESTAMP, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        cols.setColumnMetadata("instances", DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);
        dataSet.setColumns(cols);

        nexosisClient.getDataSets().create(dataSetName, dataSet);

        SessionResponse actual = nexosisClient.getSessions().analyzeImpact(
                dataSetName,
                "charlie-delta-{DateTime.UtcNow:s}",
                "instances",
                DateTime.parse("2016-11-26", DateTimeFormat.forPattern("yyyy-MM-dd")),
                DateTime.parse("2016-12-25", DateTimeFormat.forPattern("yyyy-MM-dd")),
                ResultInterval.DAY
        );

        SessionResultStatus status = nexosisClient.getSessions().getStatus(actual.getSessionId());

        Assert.assertEquals(actual.getStatus(), status.getStatus());
    }

    @Test
    public void CanRemoveMultipleSessions() throws NexosisClientException {
        String dataSetName = "testDataSet-" + DateTime.now().toDateTimeISO().toString();

        DataSetData dataSet = DataSetGenerator.Run(
                DateTime.parse("2016-08-01T00:00:00Z"),
                DateTime.parse("2017-03-26T00:00:00Z"),
                "instances"
        );

        nexosisClient.getDataSets().create(dataSetName, dataSet);

        SessionResponse first = nexosisClient.getSessions().analyzeImpact(
                dataSetName,
                "juliet-juliet-echo-1",
                "instances",
                DateTime.parse("2016-11-26T00:00:00Z"),
                DateTime.parse("2016-12-25T00:00:00Z"),
                ResultInterval.DAY
        );

        SessionResponse second = nexosisClient.getSessions().analyzeImpact(
                dataSetName,
                "juliet-juliet-echo-2",
                "instances",
                DateTime.parse("2016-11-26T00:00:00Z"),
                DateTime.parse("2016-12-25T00:00:00Z"),
                ResultInterval.DAY
        );

        nexosisClient.getSessions().remove(null, "juliet-juliet-echo-", SessionType.IMPACT);

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

    @Test
    public void CanRequestDifferentPredictionIntervals() throws NexosisClientException {
        SessionResponses sessions = nexosisClient.getSessions().list();
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
        SessionResult pi1 = nexosisClient.getSessions().getResults(targetSession.getSessionId(), targetSession.getAvailablePredictionIntervals()[0]);
        SessionResult pi2 = nexosisClient.getSessions().getResults(targetSession.getSessionId(), targetSession.getAvailablePredictionIntervals()[1]);
        String key = pi1.getTargetColumn();
        for (Integer index = 0; index < pi1.getData().size(); index++) {
            Assert.assertNotEquals(pi1.getData().get(index).get(key),
                                    pi2.getData().get(index).get(key));
        }
    }
}