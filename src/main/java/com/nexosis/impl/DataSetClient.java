package com.nexosis.impl;

import com.nexosis.IDataSetClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;

public class DataSetClient implements IDataSetClient {
    private ApiConnection apiConnection;

    public DataSetClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetSummary create(String dataSetName, DataSetData data) throws NexosisClientException {
        return create(dataSetName, data, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetSummary create(String dataSetName, DataSetData data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(data, "data");

        return apiConnection.put(DataSetSummary.class, "data/" + dataSetName, null, data, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetSummary create(String dataSetName, InputStream input) throws NexosisClientException
    {
        return create(dataSetName, input, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetSummary create(String dataSetName, InputStream input, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException  {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(input, "input");

        return apiConnection.put(DataSetSummary.class, "data/" + dataSetName, null, input, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetList list() throws NexosisClientException {
        return this.list(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetList list(String nameFilter) throws NexosisClientException {
        return this.list(nameFilter, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetList list(String nameFilter, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String,Object> queryParams = new HashMap<>();

        if (!StringUtils.isEmpty(nameFilter)) {
            queryParams.put("partialName", nameFilter);
        }

        return apiConnection.get(DataSetList.class, "data", queryParams, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetData get(String dataSetName) throws NexosisClientException {
        return get(dataSetName, 0, NexosisClient.getMaxPageSize(), new ArrayList<String>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetData get(String dataSetName, int pageNumber, int pageSize, Iterable<String> includeColumns) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(includeColumns, "includeColumns");

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("page", Integer.toString(pageNumber));
        parameters.put("pageSize", Integer.toString(pageSize));
        parameters.put("include",includeColumns);

        return apiConnection.get(DataSetData.class,"data/" + dataSetName, parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetData get(String dataSetName, int pageNumber, int pageSize, DateTime startDate, DateTime endDate, Iterable<String> includeColumns) throws NexosisClientException {
        return get(dataSetName, pageNumber, pageSize, startDate, endDate, includeColumns, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetData get(String dataSetName, int pageNumber, int pageSize, DateTime startDate, DateTime endDate, Iterable<String> includeColumns, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String, Object> parameters = ProcessDataSetGetParameters(dataSetName, pageNumber, pageSize, startDate, endDate, includeColumns);
        return apiConnection.get(DataSetData.class, "data/" + dataSetName, parameters, httpMessageTransformer);
    }

    private static Map<String,Object> ProcessDataSetGetParameters(String dataSetName, int pageNumber, int pageSize,
                                                                                         DateTime startDate, DateTime endDate, Iterable<String> includeColumns)
    {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(includeColumns, "includeColumns");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", Integer.toString(pageNumber));
        parameters.put("pageSize", Integer.toString(pageSize));
        parameters.put("startDate", startDate.toDateTimeISO().toString());
        parameters.put("endDate", endDate.toDateTimeISO().toString());
        parameters.put("include", includeColumns);
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void get(String dataSetName, OutputStream output) throws NexosisClientException
    {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(output, "output");

        apiConnection.get("data/" + dataSetName, null, null, output, "text/csv");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void get(String dataSetName, OutputStream output, int pageNumber, int pageSize, Iterable<String> includeColumns) throws NexosisClientException
    {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(includeColumns, "includeColumns");
        Argument.IsNotNull(output, "output");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", Integer.toString(pageNumber));
        parameters.put("pageSize", Integer.toString(pageSize));
        parameters.put("include",includeColumns);

        apiConnection.get("data/" + dataSetName, parameters, null, output, "text/csv");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void get(String dataSetName, OutputStream output, int pageNumber, int pageSize, DateTime startDate,
                          DateTime endDate, Iterable<String> includeColumns) throws NexosisClientException
    {
        get(dataSetName, output, pageNumber, pageSize, startDate, endDate, includeColumns, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void get(String dataSetName, OutputStream output, int pageNumber, int pageSize, DateTime startDate,
                          DateTime endDate, Iterable<String> includeColumns, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        Argument.IsNotNull(output, "output");
        Map<String, Object> parameters = ProcessDataSetGetParameters(dataSetName, pageNumber, pageSize, startDate, endDate, includeColumns);

        apiConnection.get("data/" + dataSetName, parameters, httpMessageTransformer, output, "text/csv");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSetName, EnumSet<DataSetDeleteOptions> options) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");

        Map<String, Object> parameters = new HashMap<>();
        ArrayList<String> set = new ArrayList<>();

        if (options.contains(DataSetDeleteOptions.CASCADE_FORECAST))
            set.add("forecast");
        if (options.contains(DataSetDeleteOptions.CASCADE_SESSION))
            set.add( "session");
        if (options.contains(DataSetDeleteOptions.CASCADE_VIEW))
            set.add("view");

        if (set.size() > 0) {
            parameters.put("cascade", set);
        }

        apiConnection.delete("data/" + dataSetName, parameters, null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSetName, DateTime startDate, DateTime endDate, EnumSet<DataSetDeleteOptions> options) throws NexosisClientException {
        remove(dataSetName, startDate, endDate, options, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSetName, DateTime startDate, DateTime endDate, EnumSet<DataSetDeleteOptions> options, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("startDate", startDate.toDateTimeISO().toString());
        parameters.put("endDate", endDate.toDateTimeISO().toString());

        ArrayList<String> set = new ArrayList<>();
        if (options.contains(DataSetDeleteOptions.CASCADE_FORECAST))
            set.add("forecast");
        if (options.contains(DataSetDeleteOptions.CASCADE_SESSION))
            set.add( "sessions");

        if (set.size() > 0) {
            parameters.put("cascade",set);
        }
        apiConnection.delete("data/" + dataSetName, parameters, httpMessageTransformer);
    }
}
