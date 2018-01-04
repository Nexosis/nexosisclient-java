package com.nexosis;

import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;
import java.util.Iterator;

public class ImportIntegrationTests {
    private static String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private static final String absolutePath = System.getProperty("user.dir") + "/src/test/java/com/nexosis";
    private NexosisClient nexosisClient;
    private static final String EXISTING_DATASET = "Location-A";
    private static UUID existingId;

    @Before
    public void beforeClass() throws NexosisClientException {
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);

        ImportDetailQuery query = new ImportDetailQuery();
        query.setDataSetName(EXISTING_DATASET);
        query.setPage(new PagingInfo(0,1));

        ImportDetails existingList = nexosisClient.getImports().list(query);
        ImportDetail existing = null;
        if (existingList.getItems().size() <= 0) {
            ImportFromS3Request detail = new ImportFromS3Request();
            detail.setDataSetName(EXISTING_DATASET);
            detail.setBucket("nexosis-sample-data");
            detail.setPath("LocationA.csv");
            detail.setRegion("us-east-1");
            existing = nexosisClient.getImports().importFromS3(detail);
        } else {
            existing = existingList.getItems().get(0);
        }
        existingId = existing.getImportId();
    }

    @Test
    public void shouldReturnAllImports() throws NexosisClientException {
        ImportDetailQuery query = new ImportDetailQuery();
        query.setPage(new PagingInfo(0,1));
        ImportDetails actual = nexosisClient.getImports().list(query);
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.getItems().isEmpty());
        Assert.assertEquals(1, actual.getItems().size());
    }

    @Test
    public void shouldReturnOnlyForDataset() throws NexosisClientException {

        ImportDetailQuery query = new ImportDetailQuery();
        query.setDataSetName(EXISTING_DATASET);
        query.setPage(new PagingInfo(0,30));

        ImportDetails actual = nexosisClient.getImports().list(query);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.getItems().size());
        Assert.assertEquals(EXISTING_DATASET, actual.getItems().get(0).getDataSetName());
    }

    @Test
    public void shouldBeOnlyBetweenDates() throws NexosisClientException {
        DateTime afterDate = DateTime.parse("2017-07-10");
        DateTime beforeDate = DateTime.parse("2017-07-15");

        ImportDetailQuery query = new ImportDetailQuery();
        query.setRequestedAfterDate(afterDate);
        query.setRequestedBeforeDate(beforeDate);
        query.setPage(new PagingInfo(0,30));

        ImportDetails actual = nexosisClient.getImports().list(query);
        for (Iterator<ImportDetail> id = actual.getItems().iterator(); id.hasNext(); ) {
            ImportDetail current = id.next();
            Assert.assertTrue(current.getRequestedDate().isAfter(afterDate));
            Assert.assertTrue(current.getRequestedDate().isBefore(beforeDate));
        }
    }

    @Test
    public void getShouldReturnExistingImport() throws NexosisClientException {
        ImportDetail actual = nexosisClient.getImports().get(existingId);
        Assert.assertNotNull(actual);
        Assert.assertEquals(existingId, actual.getImportId());
    }

    @Test
    public void postNewImportReturnsNewId() throws NexosisClientException {
        ImportFromS3Request detail = new ImportFromS3Request();
        detail.setDataSetName("JavaTest-WillFail");
        detail.setBucket("NoBucket");
        detail.setPath("NoPath.csv");
        detail.setRegion("No-Region");

        ImportDetail actual = nexosisClient.getImports().importFromS3(detail);
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getImportId());
    }
}