package com.nexosis.impl;

import com.nexosis.IImportClient;
import com.nexosis.model.Columns;
import com.nexosis.model.ImportDetail;
import com.nexosis.model.ImportDetails;
import com.nexosis.model.SessionResponse;
import com.nexosis.util.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImportClient implements IImportClient {
    private ApiConnection apiConnection;

    public ImportClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(int page, int pageSize) throws NexosisClientException {
        return this.list(null, null, null,page,pageSize, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, int page, int pageSize) throws NexosisClientException {
        return this.list(dataSetName,null,null,page,pageSize, null );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, DateTime requestedAfterDate, DateTime requestedBeforeDate, int page, int pageSize) throws NexosisClientException {
        return this.list(dataSetName,requestedAfterDate,requestedBeforeDate,page,pageSize,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, DateTime requestedAfterDate, DateTime requestedBeforeDate, int page, int pageSize, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        String path = "/imports";
        List<NameValuePair> parameters = new ArrayList<>();
        if (dataSetName != null && !dataSetName.isEmpty()) {
            parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
        }
        if (requestedAfterDate != null) {
            parameters.add(new BasicNameValuePair("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString()));
        }
        if (requestedBeforeDate != null) {
            parameters.add(new BasicNameValuePair("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString()));
        }
        parameters.add(new BasicNameValuePair("page", Integer.toString(page)));
        parameters.add(new BasicNameValuePair("pageSize",Integer.toString(pageSize)));
        return apiConnection.get(ImportDetails.class, path, parameters, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail get(UUID id) throws NexosisClientException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail get(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region) throws NexosisClientException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Columns columns) throws NexosisClientException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Columns columns, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return null;
    }
}
