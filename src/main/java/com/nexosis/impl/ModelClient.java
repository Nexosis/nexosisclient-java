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

    public ModelClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }

    public ModelSummary get(UUID id) throws NexosisClientException {
        return get(id, null);
    }

    public ModelSummary get(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.get(ModelSummary.class, "model/" + id.toString(),null, httpMessageTransformer);
    }

    public ModelList list() throws NexosisClientException, IllegalArgumentException {
        return this.list(new ModelClientParams(), null);
    }

    public ModelList list(ModelClientParams params, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException, IllegalArgumentException {
        return apiConnection.get(ModelList.class, "models", params.buildParameter(), httpMessageTransformer);
    }

    public ModelPredictionResult predict(UUID modelId, List<Map<String, String>> data) throws NexosisClientException {
        return this.predict(modelId, data, null);
    }

    public ModelPredictionResult predict(UUID modelId, List<Map<String, String>> data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNull(modelId, "modelId");
        PredictRequest requestBody = new PredictRequest();
        requestBody.setData(data);
        return apiConnection.post(ModelPredictionResult.class, "models/" + modelId.toString() + "/predict", null, requestBody, httpMessageTransformer);
    }

    public void remove(UUID modelId) throws NexosisClientException {
        this.remove(modelId, null);
    }

    public void remove(UUID modelId, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        apiConnection.delete("models/" + modelId.toString(), null, httpMessageTransformer);
    }

    public void remove(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate) throws NexosisClientException {
        this.remove(dataSourceName, createdAfterDate, createdBeforeDate, null);
    }

    public void remove(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("createdAfterDate", createdAfterDate.toDateTimeISO().toString());
        parameters.put("createdBeforeDate", createdBeforeDate.toDateTimeISO().toString());

        if (!StringUtils.isEmpty(dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }

        this.removeModelsInternal(parameters, httpMessageTransformer);
    }

    private void removeModelsInternal(Map<String,Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        apiConnection.delete("models", parameters, httpMessageTransformer);
    }
}

