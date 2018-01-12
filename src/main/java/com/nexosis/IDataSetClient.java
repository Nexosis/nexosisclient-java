package com.nexosis;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.impl.DataSet;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.Action;

import java.io.OutputStream;

/**
 *
 */
public interface IDataSetClient {
    Action<HttpRequest, HttpResponse> getHttpMessageTransformer();
    void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer);

    /**
     * Save data in a dataset.
     * <P>
     * PUT to https://ml.nexosis.com/v1/v1/{dataSetName}
     * <P>
     * @param source A {@link IDataSetSource IDataSetSource} containing the data.  Create one of these with {@link DataSet DataSet.From}.
     * @return A {@link DataSetSummary DataSetSummary} object
     * @throws NexosisClientException
     */
    DataSetSummary create(IDataSetSource source) throws NexosisClientException;

    /**
     * Gets the list of all data sets that have been saved to the system.
     * <P>
     * GET of https://ml.nexosis.com/v1/data
     * <P>
     * @return A {@link DataSetList DataSetList} object
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetList list() throws NexosisClientException;

    /**
     * Gets the list of data sets that have been saved to the system, filtering by partial name match.
     * <P>
     * GET of https://ml.nexosis.com/v1/data
     * <P>
     * @param query A {@link DataSetSummaryQuery DataSetSummaryQuery} with the filter criteria for the DataSets to retrieve.
     * @return The List&lt;T&gt; of {@link DataSetSummary DataSetSummary} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetList list(DataSetSummaryQuery query) throws NexosisClientException;

    /**
     * Get the data in the set, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param query A DataSetDataQuery with the filter criteria for retrieving data from the DataSet.  Create one of these with DataSet.Where
     * @return A {@link DataSetData DataSetData} object containing the data by name filter.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetData get(DataSetDataQuery query) throws NexosisClientException;

    /**
     *  Get the data in the set, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param query A DataSetDataQuery with the filter criteria for retrieving data from the DataSet.  Create one of these with DataSet.Where
     * @param output Output stream to write output to.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void get(DataSetDataQuery query, OutputStream output) throws NexosisClientException;

    /**
     * Remove data from a data set or the entire set.
     * <P>
     * DELETE to https://ml.nexosis.com/v1/data/{dataSetName}
     * <P>
     * @param criteria   A {@link DataSetRemoveCriteria DataSetRemoveCriteria} with the criteria for which data to remove
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(DataSetRemoveCriteria criteria) throws NexosisClientException;
}
