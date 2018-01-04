package com.nexosis;

import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface IModelClient {
    Action<HttpRequest, HttpResponse> getHttpMessageTransformer();
    void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer);

    /**
     * Gets a model
     *
     * @param id The id of the model.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A {@link com.nexosis.model.ModelSummary ModelSummary} with information about the model.
     * GET of https://ml.nexosis.com/api/model/{modelId}
     */
    ModelSummary get(UUID id) throws NexosisClientException;

    /**
     * Gets the list of all models that have been created.
     *
     * @param query The query criteria for retrieving the models
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/models
     */
    ModelList list(ModelSummaryQuery query) throws NexosisClientException;

    /**
     * Predicts target values for a set of features using the specified model.
     *
     * @param request Parameters to be used when predicting from a model
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return {@link com.nexosis.model.ModelPredictionResult ModelSummary} containing the predicted results and features that were sent.
     * POST of https://ml.nexosis.com/api/model/{modelId}/predict
     */
    ModelPredictionResult predict(ModelPredictionRequest request) throws NexosisClientException;

    /**
     * Removes a Models from your account which match the specified parameters
     *
     * DELETE of https://ml.nexosis.com/api/model
     *
     * @param criteria Limits models removed to those with the given data source name.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(ModelRemoveCriteria criteria) throws NexosisClientException;
}