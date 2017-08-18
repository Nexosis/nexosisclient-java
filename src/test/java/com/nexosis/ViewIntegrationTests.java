package com.nexosis;

import com.nexosis.impl.NexosisClient;
import com.nexosis.model.DataSetData;
import com.nexosis.model.DataSetDeleteOptions;
import com.nexosis.model.ViewDefinition;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ViewIntegrationTests {
    private static final String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private static final String absolutePath = System.getProperty("user.dir") + "/src/test/java/com/nexosis";
    private static final String dataSetName = "TestJava";
    private static final String rightDatasetName = "TestJava_Right";

    private static final String savedDataSet = "alpha.persistent";
    private NexosisClient nexosisClient;

    @Before
    public void beforeClass() throws Exception{
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);
        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "foxtrot"
        );
        nexosisClient.getDataSets().create(dataSetName, data);
        nexosisClient.getDataSets().create(rightDatasetName,data);
    }

    @After
    public void afterClass() throws Exception{
        nexosisClient.getDataSets().remove(dataSetName, DataSetDeleteOptions.CASCASE_ALL);
        nexosisClient.getDataSets().remove(rightDatasetName, DataSetDeleteOptions.CASCASE_ALL);
    }

    @Test
    public void canCreateView() throws Exception{
        ViewDefinition actual = nexosisClient.getViews().create("TestJavaView",dataSetName,rightDatasetName,null);
        Assert.assertNotNull(actual);
        Assert.assertEquals("TestJavaView",actual.getViewName());
    }
}
