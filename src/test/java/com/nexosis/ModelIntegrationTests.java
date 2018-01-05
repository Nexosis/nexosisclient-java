package com.nexosis;

import com.nexosis.impl.DataSet;
import com.nexosis.impl.NexosisClient;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelIntegrationTests {
    private static final String baseURI = System.getenv("NEXOSIS_BASE_TEST_URL");
    private NexosisClient nexosisClient;
    private String modelDataSetName = "model.5AF982B2-C259-44C4-B635-9B095FE1B494";

    @Before
    public void beforeEach() throws Exception {
        nexosisClient = new NexosisClient(System.getenv("NEXOSIS_API_KEY"), baseURI);

        ModelSummaryQuery query = new ModelSummaryQuery();
        query.setPage(new PagingInfo(0,20));
        query.setDataSourceName(modelDataSetName);
        ModelList model = nexosisClient.getModels().list(query);

        if(model.getItems().size() == 0)
        {
            this.createSession();
        }
        else
        {
            savedModel = model.getItems().get(0);
        }
    }

    ModelSummary savedModel;

    private void createSession() throws Exception
    {
        DataSetSummaryQuery query = new DataSetSummaryQuery();
        query.setPartialName(modelDataSetName);
        // If no dataset, create it.
        if (nexosisClient.getDataSets().list(query).getItems().size() > 1) {
            DataSetDetail dataSet = DataSetGenerator.Run(90, 10, "instances");
            nexosisClient.getDataSets().create(DataSet.From(modelDataSetName, dataSet));
        }

        ModelSessionRequest request = new ModelSessionRequest();
        request.setDataSourceName(modelDataSetName);
        request.setPredictionDomain(PredictionDomain.REGRESSION);
        request.setTargetColumn("instances");
        SessionResponse session = nexosisClient.getSessions().trainModel(request);

        while (true)
        {
            SessionResultStatus status = nexosisClient.getSessions().getStatus(session.getSessionId());
            if (status.getStatus() == SessionStatus.COMPLETED || status.getStatus() == SessionStatus.FAILED)
                break;

            Thread.sleep(5000);
        }

        SessionResponse response = nexosisClient.getSessions().get(session.getSessionId());
        savedModel = nexosisClient.getModels().get(response.getModelId());
    }

    @Test
    public void modelStartsNewSession() throws Exception {
        String dataSetName = "testDataSet-" + DateTime.now().toString("yMsHms");
        DataSetDetail dataSet = DataSetGenerator.Run(90, 10, "instances");
        nexosisClient.getDataSets().create(DataSet.From(dataSetName, dataSet));

        ModelSessionRequest request = new ModelSessionRequest();
        request.setDataSourceName(dataSetName);
        request.setPredictionDomain(PredictionDomain.REGRESSION);
        request.setTargetColumn("instances");

        SessionResponse actual = nexosisClient.getSessions().trainModel(request);

        Assert.assertNotNull(actual.getSessionId());
        DataSetRemoveCriteria criteria = new DataSetRemoveCriteria(dataSetName);
        criteria.setOption(DataSetDeleteOptions.CASCADE_ALL);
        nexosisClient.getDataSets().remove(criteria);
    }

    @Test
    public void getModelListHasItems() throws NexosisClientException {
        ModelSummaryQuery query = new ModelSummaryQuery();
        query.setPage(new PagingInfo(0,1));

        ModelList actual = nexosisClient.getModels().list(query);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.getItems().isEmpty());
        Assert.assertEquals(1, actual.getItems().size());
    }

    @Test
    public void listRespectsPagingInfo() throws Exception
    {
        ModelSummaryQuery query = new ModelSummaryQuery();
        query.setPage(new PagingInfo(1,2));
        ModelList actual = nexosisClient.getModels().list(query);

        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.getPageNumber());
        Assert.assertEquals(2, actual.getPageSize());
    }

    @Test
    public void getModelDetailsHasResults() throws Exception
    {
        ModelSummary result = nexosisClient.getModels().get(savedModel.getModelId());

        Assert.assertNotNull(result);
        Assert.assertEquals(savedModel.getModelId(), result.getModelId());
    }

    @Test
    public void predictModelReturnsResults() throws Exception
    {
        DataSetDetail record = DataSetGenerator.Run(1, 10, null);

        ModelPredictionRequest request = new ModelPredictionRequest(savedModel.getModelId(), record.getData());
        ModelPredictionResult result = nexosisClient.getModels().predict(request);

        Assert.assertFalse(result.getData().isEmpty());
    }

}
