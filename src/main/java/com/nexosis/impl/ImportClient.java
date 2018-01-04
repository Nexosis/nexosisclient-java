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
    public ImportDetails list(ImportDetailQuery query) throws NexosisClientException {
        Map<String,Object> parameters = query.toParameters();
        return apiConnection.get(ImportDetails.class,"/imports", parameters, this.httpMessageTransformer);
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

    @Override
    public ImportDetail importFromS3(ImportFromS3Request detail) throws NexosisClientException {
       return apiConnection.post(ImportDetail.class, "imports/s3", null, detail, httpMessageTransformer);
    }

    @Override
    public ImportDetail ImportFromUrl(ImportFromUrlRequest detail) throws NexosisClientException {
        return apiConnection.post(ImportDetail.class, "imports/s3", null, detail, httpMessageTransformer);
    }

    @Override
    public ImportDetail ImportFromAzure(ImportFromAzureRequest detail) throws NexosisClientException {
        return apiConnection.post(ImportDetail.class, "imports/s3", null, detail, httpMessageTransformer);
    }

}
