package com.nexosis.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.IModelClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ModelClient implements IModelClient {
    private ApiConnection apiConnection;
    private Action<HttpRequest, HttpResponse> httpMessageTransformer = null;


    public ModelClient(ApiConnection apiConnection) {
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
    public ModelSummary get(UUID id) throws NexosisClientException {
        return apiConnection.get(ModelSummary.class, "model/" + id.toString(),null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    public ModelList list() throws NexosisClientException, IllegalArgumentException {
        return this.list(new ModelClientParams());
    }

    /**
     * {@inheritDoc}
     */
    public ModelList list(ModelClientParams params) throws NexosisClientException, IllegalArgumentException {
        return apiConnection.get(ModelList.class, "models", params.buildParameter(), httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    public ModelPredictionResult predict(UUID modelId, List<Map<String, String>> data) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNull(modelId, "modelId");

        PredictRequest requestBody = new PredictRequest();
        requestBody.setData(data);

        return apiConnection.post(ModelPredictionResult.class, "models/" + modelId.toString() + "/predict", null, requestBody, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(UUID modelId) throws NexosisClientException {
        apiConnection.delete("models/" + modelId.toString(), null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate) throws NexosisClientException {
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("createdAfterDate", createdAfterDate.toDateTimeISO().toString());
        parameters.put("createdBeforeDate", createdBeforeDate.toDateTimeISO().toString());

        if (!StringUtils.isEmpty(dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }

        this.removeModelsInternal(parameters);
    }

    private void removeModelsInternal(Map<String,Object> parameters) throws NexosisClientException {
        apiConnection.delete("models", parameters, httpMessageTransformer);
    }
}
