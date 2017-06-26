package com.nexosis;

import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.joda.time.DateTime;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

/**
 *
 */
public interface IDataSetClient {
    /**
     * Save data in a data set.
     * <P>
     * PUT of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName Name of the dataset to which to add data.
     * @param data        A DataSetSummary containing the data.
     * @return A {@link DataSetSummary DataSetSummary} object
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetSummary create(String dataSetName, DataSetData data) throws NexosisClientException;

    /**
     * Save data in a data set.
     * <P>
     * PUT of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName             Name of the dataset to which to add data.
     * @param data                    A DataSetSummary containing the data.
     * @param httpMessageTransformer  A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link DataSetSummary DataSetSummary} object
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetSummary create(String dataSetName, DataSetData data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Save data in a data set.
     * <P>
     * PUT of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName   Name of the dataset to which to add data.
     * @param input         A stream containing the dataset to send to the server
     * @return              A {@link DataSetSummary DataSetSummary} object
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetSummary create(String dataSetName, InputStream input) throws NexosisClientException;

    /**
     * Save data in a data set.
     * <P>
     * PUT of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName             Name of the dataset to which to add data.
     * @param input                   A stream containing the dataset to send to the server
     * @param httpMessageTransformer  A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return                        A {@link DataSetSummary DataSetSummary} object
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetSummary create(String dataSetName, InputStream input, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Gets the list of all data sets that have been saved to the system.
     * <P>
     * GET of https://ml.nexosis.com/api/data
     * <P>
     * @return A {@link DataSetList DataSetList} object c
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetList list() throws NexosisClientException;

    /**
     * Gets the list of data sets that have been saved to the system, filtering by partial name match.
     * <P>
     * GET of https://ml.nexosis.com/api/data
     * <P>
     * @param nameFilter Limits results to only those datasets with names containing the specified value
     * @return The List&lt;T&gt; of {@link DataSetSummary DataSetSummary} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetList list(String nameFilter) throws NexosisClientException;

    /**
     * Gets the list of data sets that have been saved to the system, filtering by partial name match.
     * <P>
     * GET of https://ml.nexosis.com/api/data
     * <P>
     * @param nameFilter             Limits results to only those datasets with names containing the specified value
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return The List&lt;T&gt; of {@link DataSetSummary DataSetSummary} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetList list(String nameFilter, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Get the data in the set, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName Name of the dataset for which to retrieve data.
     * @return A {@link DataSetData DataSetData} object containing the data by name filter.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetData get(String dataSetName) throws NexosisClientException;

    /**
     * Get the data in the set, filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName    Name of the dataset for which to retrieve data.
     * @param pageNumber     Zero-based page number of results to retrieve.
     * @param pageSize       Count of results to retrieve in each page (max 100).
     * @param includeColumns Limits results to the specified columns of the data set.
     * @return A {@link DataSetData DataSetData} object containing the data by name filter.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetData get(String dataSetName, int pageNumber, int pageSize, Iterable<String> includeColumns) throws NexosisClientException;

    /**
     * Get the data in the set, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName    Name of the dataset for which to retrieve data.
     * @param pageNumber     Zero-based page number of results to retrieve.
     * @param pageSize       Count of results to retrieve in each page (max 100).
     * @param startDate      Limits results to those on or after the specified date.
     * @param endDate        Limits results to those on or before the specified date.
     * @param includeColumns Limits results to the specified columns of the data set.
     * @return A {@link DataSetData DataSetData} object containing filtered data
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetData get(String dataSetName, int pageNumber, int pageSize, org.joda.time.DateTime startDate, org.joda.time.DateTime endDate, Iterable<String> includeColumns) throws NexosisClientException;

    /**
     * Get the data in the set, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName            Name of the dataset for which to retrieve data.
     * @param pageNumber             Zero-based page number of results to retrieve.
     * @param pageSize               Count of results to retrieve in each page (max 100).
     * @param startDate              Limits results to those on or after the specified date.
     * @param endDate                Limits results to those on or before the specified date.
     * @param includeColumns         Limits results to the specified columns of the data set.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link DataSetData DataSetData} object containing filtered data
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    DataSetData get(String dataSetName, int pageNumber, int pageSize, org.joda.time.DateTime startDate, org.joda.time.DateTime endDate, Iterable<String> includeColumns, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Get the data in the set, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName            Name of the dataset for which to retrieve data.
     * @param output                 An output stream to write the data set to
     * @param pageNumber             Zero-based page number of results to retrieve.
     * @param pageSize               Count of results to retrieve in each page (max 100).
     * @param includeColumns         Limits results to the specified columns of the data set.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void get(String dataSetName, OutputStream output, int pageNumber, int pageSize, Iterable<String> includeColumns) throws NexosisClientException;

    /**
     * Get the data in the set and write it to the output stream.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName            Name of the dataset for which to retrieve data.
     * @param output                 An output stream to write the data set to
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void get(String dataSetName, OutputStream output) throws NexosisClientException;

    /**
     * Get the data in the set and write it to the output stream, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName            Name of the dataset for which to retrieve data.
     * @param output                 An output stream to write the data set to
     * @param pageNumber             Zero-based page number of results to retrieve.
     * @param pageSize               Count of results to retrieve in each page (max 100).
     * @param startDate              Limits results to those on or after the specified date.
     * @param endDate                Limits results to those on or before the specified date.
     * @param includeColumns         Limits results to the specified columns of the data set.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void get(String dataSetName, OutputStream output, int pageNumber, int pageSize, DateTime startDate, DateTime endDate, Iterable<String> includeColumns) throws NexosisClientException;

    /**
     * Get the data in the set and write it to the output stream, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName            Name of the dataset for which to retrieve data.
     * @param output                 An output stream to write the data set to
     * @param pageNumber             Zero-based page number of results to retrieve.
     * @param pageSize               Count of results to retrieve in each page (max 100).
     * @param startDate              Limits results to those on or after the specified date.
     * @param endDate                Limits results to those on or before the specified date.
     * @param includeColumns         Limits results to the specified columns of the data set.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException
     */
    void get(String dataSetName, OutputStream output, int pageNumber, int pageSize, DateTime startDate, DateTime endDate, Iterable<String> includeColumns, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Remove data from a data set or the entire set.
     * <P>
     * DELETE to https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName Name of the dataset from which to remove data.
     * @param options     Controls the options associated with the removal.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(String dataSetName, EnumSet<DataSetDeleteOptions> options) throws NexosisClientException;

    /**
     * Remove data from a data set or the entire set.
     * <P>
     * DELETE to https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName   Name of the dataset from which to remove data.
     * @param startDate     Limits data removed to those on or after the specified date.
     * @param endDate       Limits data removed to those on or before the specified date.
     * @param options       Controls the options associated with the removal.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(String dataSetName, org.joda.time.DateTime startDate, org.joda.time.DateTime endDate, EnumSet<DataSetDeleteOptions> options) throws NexosisClientException;

    /**
     * Remove data from a data set or the entire set.
     * <P>
     * DELETE to https://ml.nexosis.com/api/data/{dataSetName}
     * <P>
     * @param dataSetName            Name of the dataset from which to remove data.
     * @param startDate              Limits data removed to those on or after the specified date.
     * @param endDate                Limits data removed to those on or before the specified date.
     * @param options                Controls the options associated with the removal.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(String dataSetName, org.joda.time.DateTime startDate, org.joda.time.DateTime endDate, EnumSet<DataSetDeleteOptions> options,  Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;
}
