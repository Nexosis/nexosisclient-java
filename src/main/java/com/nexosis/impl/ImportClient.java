package com.nexosis.impl;

import com.nexosis.IImportClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import org.joda.time.DateTime;

import java.util.*;

public class ImportClient implements IImportClient {
    private ApiConnection apiConnection;
    private Action<HttpRequest, HttpResponse> httpMessageTransformer = null;

    public ImportClient(ApiConnection apiConnection) {
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
    public ImportDetails list(int page, int pageSize) throws NexosisClientException {
        return this.list(null, null, null, page, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, int page, int pageSize) throws NexosisClientException {
        return this.list(dataSetName, null, null, page, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetails list(String dataSetName, DateTime requestedAfterDate, DateTime requestedBeforeDate, int page, int pageSize) throws NexosisClientException {
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
        Argument.IsNotNull(id, "id");
        String path = String.format("imports/%s", id.toString());
        return apiConnection.get(ImportDetail.class, path, null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region) throws NexosisClientException {
        return this.importFromS3(dataSetName, bucket, path, region, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Columns columns) throws NexosisClientException {
        ImportData body = new ImportData();
        body.setDataSetName(dataSetName);
        body.setBucket(bucket);
        body.setPath(path);
        body.setRegion(region);
        body.setColumns(columns);
        return apiConnection.post(ImportDetail.class, "imports/s3", null, body, httpMessageTransformer);
    }
}
