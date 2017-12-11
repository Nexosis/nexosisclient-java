package com.nexosis;

import com.neovisionaries.i18n.CountryCode;
import com.nexosis.impl.NexosisClient;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.*;

public class ViewIntegrationTests {
    private static final String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private static final String absolutePath = System.getProperty("user.dir") + "/src/test/java/com/nexosis";
    private static final String dataSetName = "TestJava";
    private static final String rightDatasetName = "TestJava_Right";
    private static final String preExistingView = "Views_TestJava";

    private static final String savedDataSet = "alpha.persistent";
    private static NexosisClient nexosisClient;

    @BeforeClass
    public static void beforeClass() throws Exception{
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);
        DataSetData data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "foxtrot"
        );
        nexosisClient.getDataSets().create(dataSetName, data);
        nexosisClient.getDataSets().create(rightDatasetName,data);
        nexosisClient.getViews().create(preExistingView,dataSetName,rightDatasetName,null);
    }

    @AfterClass
    public static void afterClass() throws Exception{
        nexosisClient.getDataSets().remove(dataSetName, DataSetDeleteOptions.CASCADE_ALL);
        nexosisClient.getDataSets().remove(rightDatasetName, DataSetDeleteOptions.CASCADE_ALL);
    }

    @Test
    public void canCreateView() throws Exception{
        ViewDefinition actual = nexosisClient.getViews().create("TestJavaView",dataSetName,rightDatasetName,null);
        Assert.assertNotNull(actual);
        Assert.assertEquals("TestJavaView",actual.getViewName());
    }

    @Test
    public void canListViews() throws Exception {
        ViewDefinitionList actual = nexosisClient.getViews().list();
        Assert.assertNotNull(actual);
        Boolean viewListed = false;
        for (Iterator<ViewDefinition> i = actual.getItems().iterator(); i.hasNext();){
            viewListed = i.next().getViewName().equals(preExistingView);
        }
        Assert.assertTrue(viewListed);
    }

    @Test
    public void canFilterViewList() throws Exception {
        ViewDefinitionList actual = nexosisClient.getViews().list(preExistingView, null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.getItems().size());
        Assert.assertEquals(preExistingView, actual.getItems().get(0).getViewName());
    }

    @Test
    public void canGetViewData() throws Exception {
        ViewData actual = nexosisClient.getViews().get(preExistingView);
        Assert.assertNotNull(actual);
        Assert.assertEquals(50, actual.getData().size());
        Assert.assertEquals(preExistingView,actual.getViewName());
    }

    @Test
    public void canGetSubsetOfViewData() throws Exception {
        ListQuery query = new ListQuery();
        query.setPageSize(10);
        DateTime startDate = DateTime.parse("2017-02-01T00:00:00.000-00:00");
        query.setStartDate(startDate);
        ViewData actual = nexosisClient.getViews().get(preExistingView, query);
        Assert.assertNotNull(actual);
        Assert.assertEquals(10, actual.getData().size());
        DateTime actualStart = DateTime.parse(actual.getData().get(0).get("timestamp"));
        Assert.assertEquals(startDate, actualStart);
    }

    @Test
    public void canRemoveView() throws Exception {
        ViewDefinition view = nexosisClient.getViews().create("JavaView_ToRemove", dataSetName, rightDatasetName, null);
        nexosisClient.getViews().remove(view.getViewName());
        ViewDefinitionList actual = nexosisClient.getViews().list("JavaView_ToRemove", null);
        Assert.assertEquals(0,actual.getItems().size());
    }

    @Test
    public void canCreateSessionOnView() throws Exception {
        DateTime sessionStart = DateTime.parse("2017-02-01T00:00:00.000-00:00");
        DateTime sessionEnd = DateTime.parse("2017-02-05T00:00:00.000-00:00");
        SessionResponse session = nexosisClient.getSessions().createForecast(preExistingView,"foxtrot",sessionStart,sessionEnd,ResultInterval.DAY );
        Assert.assertNotNull(session);
    }

    @Test
    public void canCreateWithCalendar() throws Exception{
        String viewName = "TestJavaCalendarView";
        ViewDefinition actual = nexosisClient.getViews().create(viewName, dataSetName, CountryCode.CA, null,null);
        Assert.assertNotNull(actual);
        Assert.assertEquals("Nexosis.Holidays-CA", actual.getJoins().get(0).getCalendar().getName());
        nexosisClient.getViews().remove(viewName);
    }
}
