package com.nexosis;

import com.neovisionaries.i18n.CountryCode;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.Sessions;
import com.nexosis.impl.Views;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ViewIntegrationTests {
    private static final String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private static final String dataSetName = "TestJava";
    private static final String rightDatasetName = "TestJava_Right";
    private static final String preExistingView = "Views_TestJava";

    private static NexosisClient nexosisClient;

    @BeforeClass
    public static void beforeClass() throws Exception{
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);
        DataSetDetail data = DataSetGenerator.Run(
                DateTime.parse("2017-01-01T00:00Z"),
                DateTime.parse("2017-03-31T00:00Z"),
                "foxtrot"
        );
        nexosisClient.getDataSets().create(new DataSetDetailSource(dataSetName, data));
        nexosisClient.getDataSets().create(new DataSetDetailSource(rightDatasetName,data));

        nexosisClient.getViews().create(preExistingView, Views.createWithDataSetNames(dataSetName, rightDatasetName));
    }

    @AfterClass
    public static void afterClass() throws Exception{
        DataSetRemoveCriteria criteria = new DataSetRemoveCriteria(dataSetName);
        criteria.setOption(DataSetDeleteOptions.CASCADE_ALL);
        nexosisClient.getDataSets().remove(criteria);

        DataSetRemoveCriteria criteria2 = new DataSetRemoveCriteria(rightDatasetName);
        criteria2.setOption(DataSetDeleteOptions.CASCADE_ALL);
        nexosisClient.getDataSets().remove(criteria2);
    }

    @Test
    public void canCreateView() throws Exception{
        ViewDefinition actual = nexosisClient.getViews().create("TestJavaView", Views.createWithDataSetNames(dataSetName, rightDatasetName));
        Assert.assertNotNull(actual);
        Assert.assertEquals("TestJavaView",actual.getViewName());
    }

    @Test
    public void canListViews() throws Exception {
        ViewDefinitionList actual = nexosisClient.getViews().list(new ViewQuery());
        Assert.assertNotNull(actual);
        Boolean viewListed = false;

        for (ViewDefinition v : actual.getItems()) {
            viewListed |= v.getViewName().equals(preExistingView);
        }
        Assert.assertTrue(viewListed);
    }

    @Test
    public void canFilterViewList() throws Exception {
        ViewQuery query = new ViewQuery();
        query.setPartialName(preExistingView);
        ViewDefinitionList actual = nexosisClient.getViews().list(query);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.getItems().size());
        Assert.assertEquals(preExistingView, actual.getItems().get(0).getViewName());
    }

    @Test
    public void canGetViewData() throws Exception {
        ViewDetail actual = nexosisClient.getViews().get(new ViewDataQuery(preExistingView));
        Assert.assertNotNull(actual);
        Assert.assertEquals(50, actual.getData().size());
        Assert.assertEquals(preExistingView,actual.getViewName());
    }

    @Test
    public void canGetSubsetOfViewData() throws Exception {
        DateTime startDate = DateTime.parse("2017-02-01T00:00:00.000-00:00");

        ViewDataQuery query = new ViewDataQuery(preExistingView);
        query.setPage(new PagingInfo(0,10));
        query.setStartDate(startDate);

        ViewDetail actual = nexosisClient.getViews().get(query);

        Assert.assertNotNull(actual);
        Assert.assertEquals(10, actual.getData().size());
        DateTime actualStart = DateTime.parse(actual.getData().get(0).get("timestamp"));
        Assert.assertEquals(startDate, actualStart);
    }

    @Test
    public void canRemoveView() throws Exception {

        ViewDefinition view = nexosisClient.getViews().create("JavaView_ToRemove", Views.createWithDataSetNames( dataSetName, rightDatasetName) );
        nexosisClient.getViews().remove(new ViewDeleteCriteria(view.getViewName()));

        ViewQuery query = new ViewQuery();
        query.setDataSetName("JavaView_ToRemove");
        ViewDefinitionList actual = nexosisClient.getViews().list(query);

        Assert.assertEquals(0, actual.getItems().size());
    }

    @Test
    public void canCreateSessionOnView() throws Exception {
        DateTime sessionStart = DateTime.parse("2017-02-01T00:00:00.000-00:00");
        DateTime sessionEnd = DateTime.parse("2017-02-05T00:00:00.000-00:00");

        SessionResponse session = nexosisClient.getSessions().createForecast(
                Sessions.Forecast(
                        preExistingView,
                        sessionStart,
                        sessionEnd,
                        ResultInterval.DAY,
                        "foxtrot",
                        null
                )
        );
        Assert.assertNotNull(session);
    }

    @Test
    public void canCreateWithCalendar() throws Exception{
        String viewName = "TestJavaCalendarView";

        ViewDefinition actual = nexosisClient.getViews().create(viewName,
                Views.createWithHolidayCalendarForCountry(dataSetName, CountryCode.US, null,null));

        Assert.assertNotNull(actual);
        Assert.assertEquals("Nexosis.Holidays-US", actual.getJoins().get(0).getCalendar().getName());

        nexosisClient.getViews().remove(new ViewDeleteCriteria(viewName));
    }
}
