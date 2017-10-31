package com.nexosis;

import com.google.api.client.http.HttpStatusCodes;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.junit.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class DataSetIntegrationTests {
    private static final String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private static final String absolutePath = System.getProperty("user.dir") + "/src/test/java/com/nexosis";
    private static final String productFilePath = absolutePath + "/CsvFiles/producttest.csv";
    private static final UUID savedSessionId = UUID.fromString("015ce643-f899-405f-8115-7f91ab59e7fa");

    private static final String savedDataSet = "alpha.persistent";
    private NexosisClient nexosisClient;

    @Before
    public void beforeClass() {
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);
    }

    @Test
    public void canSaveDataSet() throws NexosisClientException
    {
        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "foxtrot"
        );
        DataSetSummary result = nexosisClient.getDataSets().create("whiskey", data);

        Assert.assertEquals("whiskey", result.getDataSetName());
    }

    @Test
    public void gettingDataSetGivesBackLinks() throws NexosisClientException
    {
        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "hotel"
        );
        nexosisClient.getDataSets().create("whiskey", data);
        DataSetData result = nexosisClient.getDataSets().get("whiskey");

        Assert.assertEquals("whiskey",result.getDataSetName());
        Assert.assertEquals(4, result.getLinks().size());
        Assert.assertEquals("self", result.getLinks().get(0).getRel());
        Assert.assertEquals(baseURI + "/data/whiskey?pageSize=1000&page=0", result.getLinks().get(0).getHref());
    }

    @Test
    public void canGetDataSetThatHasBeenSaved() throws NexosisClientException {
        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "hotel"
        );

        nexosisClient.getDataSets().create("india2", data);

        DataSetData result = nexosisClient.getDataSets().get("india2");

        Assert.assertTrue(result.getData().get(0).containsKey("hotel"));
        Assert.assertTrue(result.getData().get(0).containsKey("timestamp"));

        DateTime receivedDt = DateTime.parse(result.getData().get(0).get("timestamp"));
        Assert.assertTrue(DateTime.parse("2017-01-01T00:00Z").compareTo(receivedDt) == 0);
    }

    @Test
    public void canPutMoreDataToSameDataSet() throws NexosisClientException
    {
        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00:00Z"),
                DateTime.parse("2017-01-31T00:00:00Z"),
                "hotel");

        nexosisClient.getDataSets().create("charley", data);

        DataSetData moreData = DataSetGenerator.Run(DateTime.parse("2017-02-01T00:00Z"), DateTime.parse("2017-03-01T00:00Z"), "hotel");
        nexosisClient.getDataSets().create("charley", moreData);

        DataSetData result = nexosisClient.getDataSets().get("charley");

        // TODO - these should probably be sorted??
        //Collections.sort(result.getData());

        DateTime firstDate = DateTime.parse(result.getData().get(0).get("timestamp"));
        Assert.assertTrue(DateTime.parse("2017-01-01T00:00Z").compareTo(firstDate) == 0);
        DateTime lastDate = DateTime.parse(result.getData().get(result.getData().size()-1).get("timestamp"));
        Assert.assertTrue(DateTime.parse("2017-02-28T00:00Z").compareTo(lastDate) == 0);
    }

    @Test
    public void listsDataSets() throws NexosisClientException
    {
        DataSetList list = nexosisClient.getDataSets().list();
        Assert.assertTrue(list.getItems().size() > 0);
    }

    @Test
    public void canRemoveDataSet() throws NexosisClientException
    {
        String id = UUID.randomUUID().toString(); //.ToString("N");

        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "hotel");

        nexosisClient.getDataSets().create(id, data);
        nexosisClient.getDataSets().remove(id, DataSetDeleteOptions.CASCADE_NONE);

        try {
            nexosisClient.getDataSets().get(id);
        } catch (NexosisClientException exception) {
            Assert.assertEquals(HttpStatusCodes.STATUS_CODE_NOT_FOUND, exception.getStatusCode());
            return;
        }

        Assert.fail();
    }


    @Ignore("Only run once on a new account to setup the data for integration tests.")
    public void PopulateDataForTesting() throws FileNotFoundException, NexosisClientException
    {
        // loads a data set and creates a forecast so we can query it when running the tests
        String dataSetName = savedDataSet;

        File initialFile = new File(productFilePath);
        InputStream inputStream = new FileInputStream(initialFile);

        nexosisClient.getDataSets().create(dataSetName, inputStream);
        nexosisClient.getSessions().createForecast(
                dataSetName,
                "sales",
                DateTime.parse("2017-03-25T0:00:00Z"),
                DateTime.parse("2017-04-24T0:00:00Z"),
                ResultInterval.DAY
        );

        DataSetList dataSets = nexosisClient.getDataSets().list(dataSetName);
        String names = "";
        for (DataSetSummary summary : dataSets.getItems()) {
            names += names + summary.getDataSetName() + ", ";
        }

        names = names.substring(0,names.length()-2);
        System.out.println(names);
    }

}
