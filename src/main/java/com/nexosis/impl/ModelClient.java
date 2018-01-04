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
        return apiConnection.get(ModelSummary.class, "models/" + id.toString(),null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    public ModelList list(ModelSummaryQuery params) throws NexosisClientException, IllegalArgumentException {
        return apiConnection.get(ModelList.class, "models", params.toParameters(), httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelPredictionResult predict(ModelPredictionRequest request) throws NexosisClientException {
        Argument.IsNotNull(request.getData(), "data");
        Argument.IsNotNull(request.getModelId(), "modelId");

        PredictRequest requestBody = new PredictRequest();
        requestBody.setData(request.getData());

        return apiConnection.post(ModelPredictionResult.class, "models/" + request.getModelId().toString() + "/predict", null, requestBody, this.httpMessageTransformer);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(ModelRemoveCriteria criteria) throws NexosisClientException {
        Argument.IsNotNull(criteria, "ModelRemoveCriteria");

        Argument.OneOfIsNotNullOrEmpty(
                new AbstractMap.SimpleEntry<Object, String>(criteria.getDataSourceName(), "ModelRemoveCriteria.DataSourceName"),
                new AbstractMap.SimpleEntry<Object, String>(criteria.getModelId(), "ModelRemoveCriteria.ModelId")
        );

        if (criteria.getModelId() != null) {
            apiConnection.delete("models/" + criteria.getModelId().toString(), null, httpMessageTransformer);
        } else {
            Map<String,Object> params = criteria.toParameters();
            apiConnection.delete("models", params, httpMessageTransformer);
        }
    }
}
