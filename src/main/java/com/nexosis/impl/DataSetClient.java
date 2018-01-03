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
    private Action<HttpRequest, HttpResponse> httpMessageTransformer = null;

    public DataSetClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }

    /**
     * {@inheritDoc}
     */
    public Action<HttpRequest, HttpResponse> getHttpMessageTransformer() {
        return httpMessageTransformer;
    }

    /**
     * {@inheritDoc}
     */
    public void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer) {
        this.httpMessageTransformer = httpMessageTransformer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetSummary create(IDataSetSource source) throws NexosisClientException {
        Argument.IsNotNull(source, "IDataSetSource");
        Argument.IsNotNullOrEmpty(source.getName(), "IDataSetSource.Name");

        switch (source.getClass().getCanonicalName()) {
            case "com.nexosis.model.DataSetDetailSource":
                DataSetDetailSource detail =  (DataSetDetailSource)source;
                Argument.IsNotNull(detail.getData(), "DataSetDetailSource.Data");
                return apiConnection.put(
                        DataSetSummary.class,
                        "data/" + detail.getName(),
                        null,
                        detail.getData(),
                        this.httpMessageTransformer
                );
            case "com.nexosis.model.DataSetStreamSource":
                DataSetStreamSource stream = (DataSetStreamSource)source;
                Argument.IsNotNull(stream.getData(), "DataSetStreamSource.Data");
                return apiConnection.put(
                        DataSetSummary.class,
                        "data/" + stream.getName(),
                        null, stream.getData(),
                        stream.getContentType(),
                        this.httpMessageTransformer
                );
            default:
                throw new NexosisClientException("No DataSet create supported for " + source.getClass().getCanonicalName());
        }
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
    public DataSetList list(DataSetSummaryQuery query) throws NexosisClientException {
        Map<String, Object> parameters=null;

        if (query != null) {
            parameters= query.toParameters();
        }

        return apiConnection.get(DataSetList.class, "data", parameters, httpMessageTransformer);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DataSetData get(DataSetDataQuery query) throws NexosisClientException {
        Argument.IsNotNull(query, "DataSetDataQuery");
        Argument.IsNotNullOrEmpty(query.getName(), "DataSetDataQuery.Name");

        Map<String, Object> parameters= query.toParameters();

        return apiConnection.get(DataSetData.class, "data/" + query.getName(), parameters, this.httpMessageTransformer);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void get(DataSetDataQuery query, OutputStream output) throws NexosisClientException {
        Argument.IsNotNull(query, "DataSetDataQuery");
        Argument.IsNotNullOrEmpty(query.getName(), "DataSetDataQuery.Name");
        Argument.IsNotNull(output, "output");

        Map<String, Object> parameters = query.toParameters();
        apiConnection.get("data/" +  query.getName(), parameters, this.httpMessageTransformer, output, query.getContentType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(DataSetRemoveCriteria criteria) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(criteria.getName(), "DataSetRemoveCriteria.Name");

        Map<String, Object> parameters = criteria.toParameters();
        apiConnection.delete("data/" + criteria.getName(), parameters, this.httpMessageTransformer);
    }
}
