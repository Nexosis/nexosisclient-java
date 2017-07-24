package com.nexosis;

import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.ImportDetail;
import com.nexosis.model.ImportDetails;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.Iterator;

public class ImportIntegrationTests {
    private static String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private static final String absolutePath = System.getProperty("user.dir") + "/src/test/java/com/nexosis";
    private NexosisClient nexosisClient;
    private static final String EXISTING_DATASET = "Location-A";

    @Before
    public void beforeClass() throws NexosisClientException {
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);
        ImportDetails existing = nexosisClient.getImports().list(EXISTING_DATASET, 0, 1);
        if (existing.getItems().size() <= 0)
            nexosisClient.getImports().importFromS3(EXISTING_DATASET, "nexosis-sample-data", "LocationA.csv", "us-east-1");
    }

    @Test
    public void shouldReturnAllImports() throws NexosisClientException {
        ImportDetails actual = nexosisClient.getImports().list(0, 1);
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.getItems().isEmpty());
        Assert.assertEquals(1, actual.getItems().size());
    }

    @Test
    public void shouldReturnOnlyForDataset() throws NexosisClientException {
        ImportDetails actual = nexosisClient.getImports().list(EXISTING_DATASET, 0, 30);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.getItems().size());
        Assert.assertEquals(EXISTING_DATASET, actual.getItems().get(0).getDataSetName());
    }

    @Test
    public void shouldBeOnlyBetweenDates() throws NexosisClientException {
        DateTime afterDate = DateTime.parse("2017-07-10");
        DateTime beforeDate = DateTime.parse("2017-07-15");
        ImportDetails actual = nexosisClient.getImports().list(null, afterDate, beforeDate, 0, 30);
        for (Iterator<ImportDetail> id = actual.getItems().iterator(); id.hasNext(); ) {
            ImportDetail current = id.next();
            Assert.assertTrue(current.getRequestedDate().isAfter(afterDate));
            Assert.assertTrue(current.getRequestedDate().isBefore(beforeDate));
        }
    }

}