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
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/models
     */
    ModelList list() throws NexosisClientException;

    /**
     * Gets the list of all models that have been created.
     *
     * @param page zero index page of the results to get
     * @param pageSize number of items per page
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/model
     */
    ModelList list(int page, int pageSize) throws NexosisClientException;

    /**
     * Gets the list of all models that have been created.
     *
     * @param dataSourceName Limits models to those for a particular data source.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/model
     */
    ModelList list(String dataSourceName) throws NexosisClientException;

    /**
     * Gets the list of all models that have been created.
     *
     * @param dataSourceName Limits models to those for a particular data source.
     * @param page zero index page of the results to get
     * @param pageSize number of items per page
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/model
     */
    ModelList list(String dataSourceName, int page, int pageSize) throws NexosisClientException;

    /**
     * Gets the list of all models that have been created.
     *
     * @param dataSourceName Limits models to those for a particular data source.
     * @param createdAfterDate Limits sessions to those requested on or after the specified date.
     * @param createdBeforeDate Limits sessions to those requested on or before the specified date.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/model
     */
    ModelList list(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate) throws NexosisClientException;

    /**
     * Gets the list of all models that have been created.
     *
     * @param dataSourceName Limits models to those for a particular data source.
     * @param createdAfterDate Limits sessions to those requested on or after the specified date.
     * @param createdBeforeDate Limits sessions to those requested on or before the specified date.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return A list of {@link com.nexosis.model.ModelSummary ModelSummary}.
     * GET of https://ml.nexosis.com/api/model
     */
    ModelList list(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Predicts target values for a set of features using the specified model.
     *
     * @param modelId The identifier of the model to use for prediction.
     * @param data Column and value pairs of the features which will be used in this prediction.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return {@link com.nexosis.model.ModelPredictionResult ModelSummary} containing the predicted results and features that were sent.
     * POST of https://ml.nexosis.com/api/model/{modelId}/predict
     */
    ModelPredictionResult predict(UUID modelId, List<Map<String, String>> data) throws NexosisClientException;

    /**
     * Predicts target values for a set of features using the specified model.
     *
     * @param modelId The identifier of the model to use for prediction.
     * @param data Column and value pairs of the features which will be used in this prediction.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return {@link com.nexosis.model.ModelPredictionResult ModelSummary} containing the predicted results and features that were sent.
     * POST of https://ml.nexosis.com/api/model/{modelId}/predict
     */
    ModelPredictionResult predict(UUID modelId, List<Map<String, String>> data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Removes a single Model from your account
     *
     * @param modelId The identifier of the model which should be deleted.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * DELETE of https://ml.nexosis.com/api/model/{modelId}
     **/
    void remove(UUID modelId) throws NexosisClientException;

     /**
     * Removes a single Model from your account
     *
     * @param modelId The identifier of the model which should be deleted.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * DELETE of https://ml.nexosis.com/api/model/{modelId}
     */
    void remove(UUID modelId, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Removes a Models from your account which match the specified parameters
     *
     * @param dataSourceName Limits models removed to those with the given data source name.
     * @param createdAfterDate Limits sessions to those requested on or after the specified date.
     * @param createdBeforeDate Limits sessions to those requested on or before the specified date.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * DELETE of https://ml.nexosis.com/api/model
     */
    void remove(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate) throws NexosisClientException;

    /**
     * Removes a Models from your account which match the specified parameters
     *
     * @param dataSourceName Limits models removed to those with the given data source name.
     * @param createdAfterDate Limits sessions to those requested on or after the specified date.
     * @param createdBeforeDate Limits sessions to those requested on or before the specified date.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * DELETE of https://ml.nexosis.com/api/model
     */
    void remove(String dataSourceName, org.joda.time.DateTime createdAfterDate, org.joda.time.DateTime createdBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;
}