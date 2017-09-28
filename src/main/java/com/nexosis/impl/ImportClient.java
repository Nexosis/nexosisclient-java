package com.nexosis.impl;

import com.nexosis.IImportClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;

import java.util.*;

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
        return this.list(null, null, null, page, pageSize, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, int page, int pageSize) throws NexosisClientException {
        return this.list(dataSetName, null, null, page, pageSize, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, DateTime requestedAfterDate, DateTime requestedBeforeDate, int page, int pageSize) throws NexosisClientException {
        return this.list(dataSetName, requestedAfterDate, requestedBeforeDate, page, pageSize, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, DateTime requestedAfterDate, DateTime requestedBeforeDate, int page, int pageSize, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        String path = "/imports";
        Map<String, Object> parameters = new HashMap<>();
        if (dataSetName != null && !dataSetName.isEmpty()) {
            parameters.put("dataSetName", dataSetName);
        }
        if (requestedAfterDate != null) {
            parameters.put("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString());
        }
        if (requestedBeforeDate != null) {
            parameters.put("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString());
        }
        parameters.put("page", Integer.toString(page));
        parameters.put("pageSize", Integer.toString(pageSize));
        return apiConnection.get(ImportDetails.class, path, parameters, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail get(UUID id) throws NexosisClientException {
        return this.get(id, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail get(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(id, "id");
        String path = String.format("imports/%s", id.toString());
        return apiConnection.get(ImportDetail.class, path, null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region) throws NexosisClientException {
        return this.importFromS3(dataSetName, bucket, path, region, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Columns columns) throws NexosisClientException {
        return this.importFromS3(dataSetName, bucket, path, region, columns, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return this.importFromS3(dataSetName, bucket, path, region, null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Columns columns, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        ImportData body = new ImportData();
        body.setDataSetName(dataSetName);
        body.setBucket(bucket);
        body.setPath(path);
        body.setRegion(region);
        body.setColumns(columns);
        return apiConnection.post(ImportDetail.class, "imports/s3", null, body, httpMessageTransformer);
    }
}
