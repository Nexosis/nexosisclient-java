package com.nexosis;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.Action;

import java.io.OutputStream;
import java.util.UUID;

/**
 *
 */
public interface ISessionClient {

    Action<HttpRequest, HttpResponse> getHttpMessageTransformer();
    void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer);

    /**
     * A client for getting information about how Nexosis determined the algorithm to use for a session.
     *
     * Only available to customers on our paid tiers
     */
    IContestClient getContest ();
    /**
     * Forecast from data posted in the request.
     * <P>
     * POST to https://ml.nexosis.com/api/sessions/forecast
     * <P>
     * @param request       The parameters for the Forecast session.  Create with Sessions.Forecast.
     * @return A {@link com.nexosis.model.SessionResponse SessionResponse} object providing information about the session.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResponse createForecast(ForecastSessionRequest request) throws NexosisClientException;

    /**
     * Analyze impact for an event with data in the request.
     * <P>
     * POST to https://ml.nexosis.com/api/sessions/impact
     * <P>
     * @param request  The {@link ImpactSessionRequest ImpactSessionRequest} describing the parameters of the Impact session. Create with Sessions.Impact
     * @return A {@link com.nexosis.model.SessionResponse SessionResponse} object providing information about the session.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResponse analyzeImpact(ImpactSessionRequest request) throws NexosisClientException;

    /** 
      * Train a model from data already saved to the API.
      *
      * @param request Information about the datasource to be used to train the model.
      * @return A {@link com.nexosis.model.SessionResponse SessionResponse} object  providing information about the session.
      * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
      * POST to https://ml.nexosis.com/api/sessions/model
     **/
    SessionResponse trainModel(ModelSessionRequest request) throws NexosisClientException;

    /**
     * List all sessions that have been run. This will show the information about them such as the id, status, and the analysis date range.
     * All parameters are optional and will be used to limit the list returned.
     * <P>
     * GET of https://ml.nexosis.com/api/sessions
     * <P>
     * @param query The {@link SessionQuery SessionQuery} with the criteria for which sessions to return
     * @return A {@link com.nexosis.model.SessionResponse SessionResponse} object providing information about the session.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResponses list(SessionQuery query) throws NexosisClientException;

    /**
     * Remove sessions that have been run. All parameters are optional and will be used to limit the sessions removed.
     * <P>
     * DELETE to https://ml.nexosis.com/api/sessions
     * <P>
     * @param criteria The criteria to be used to determine which sessions are removed.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(SessionRemoveCriteria criteria) throws NexosisClientException;

    /**
     * Remove the session.
     * <P>
     * DELETE to https://ml.nexosis.com/api/sessions
     * <P>
     * @param id The identifier of the session to remove.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(UUID id) throws NexosisClientException;

    /**
     * Get a specific session by id.
     * <P>
     * GET of https://ml.nexosis.com/api/sessions/{id}
     * <P>
     * @param id The identifier of the session.
     * @return A SessionResponse containing the reqeusted session.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResponse get(UUID id) throws NexosisClientException;

    /**
     * Lookup the status of the session.
     * <P>
     * HEAD of https://ml.nexosis.com/api/sessions/{id}
     * <P>
     * @param id The identifier of the session.
     * @return A {@link com.nexosis.model.SessionResultStatus} object.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResultStatus getStatus(UUID id) throws NexosisClientException;

    /**
     * Get the results of the session.
     * <P>
     * GET of https://ml.nexosis.com/api/sessions/{id}/results
     * <P>
     * @param query The {@link SessionResultQuery SessionQuery} with the criteria for which what result to return
     * @return A {@link SessionResult SessionResult} which contains the results of the run.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResult getResults(SessionResultQuery query) throws NexosisClientException;

    /**
     * Get the results of the session and writes it to the output stream. Defaults to JSON. To write CSV, use
     * SessionResultQuery.setContentType("text/csv").
     * <P>
     * GET of https://ml.nexosis.com/api/sessions/{id}/results
     * <P>
     * @param query     The {@link SessionResultQuery SessionQuery} with the criteria for which what result to return
     * @param output    Output stream to write the session results to.
     * @param query     Returns the Status of the job
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ReturnsStatus getResults(SessionResultQuery query, OutputStream output) throws NexosisClientException;

    /**
     * Gets the confusion matrix for the classification model generated by a model-building session
     * <p>
     * GET of https://ml.nexosis.com/api/sessions/{id}/results/confusionmatrix
     * <P>
     * Gets the confusion matrix for the model generated by a model-building session
     * A confusion matrix describes the performance of the classification model generated by this session by
     * showing how each record in the test set was classified by the model. The rows in the confusion matrix
     * are actual classes from the test set, and the columns are classes predicted by the model for those rows.
     * Each cell in the matrix contains the count of records in the test set with a particular actual value and
     * predicted value. The headers for both rows and columns of the matrix can be found in the `classes`
     * property of the response.
     *
     * @param id the identifier of a classification model building session
     * @return A {@link ConfusionMatrixResponse ConfusionMatrixResult} which contains the results of the run.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ConfusionMatrixResponse getConfusionMatrix(UUID id) throws NexosisClientException;

    /**
     * Gets the scores of the entire dataset generated by a particular completed anomalies session
     * <P>
     *
     * Gets the scores of the entire dataset generated by a particular completed anomalies session
     * Anomaly detection scores are generated for every row in the data source used by the session. The target value in each row is negative if the row was identified as
     * an outlier, and positive if the row was identified as "normal". The magnitude of the value provides a relative indicator of "how anomalous" or "how normal" the row is.
     * <P>
     * GET of https://ml.nexosis.com/api/sessions/{id}/results/anomalyscores
     * <P>
     * @param id
     * @param pagingInfo Paging instructions. PageNumber: 0 and PageSize: 50 by default
     * @return A {@link SessionResult SessionResult} which contains the results of the run.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResult getResultAnomalyScores(UUID id, PagingInfo pagingInfo) throws NexosisClientException;

    /**
     * Gets the class scores for each result of a particular completed classification model session
     * <p>
     * GET of https://ml.nexosis.com/api/sessions/{id}/results/classscores
     * <p>
     * Gets the class scores for each result of a particular completed classification model session
     * Whereas classification session results indicate the class chosen for each row in the test set, this endpoint returns the scores for each possible class
     * for ech row in the test set. Higher scores indicate that the model is more confident that the row fits into the specified class, but the scores are not
     * strict probabilities, and they are not comparable across sessions or data sources.
     * @param id The identifier of the session.
     * @param pagingInfo Paging instructions. PageNumber: 0 and PageSize: 50 by default
     * @return A {@link SessionResult SessionResult} which contains the results of the run.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    SessionResult getResultClassScores(UUID id, PagingInfo pagingInfo) throws NexosisClientException;

    /**
     * timeseries outliers are available on forecast sessions only. A 404 will be returned for other sessions.
     *
     * @param id The identifier of the session.
     * @param pagingInfo Paging instructions. PageNumber: 0 and PageSize: 50 by default
     * @return
     * @throws NexosisClientException
     */
    OutliersResponse getTimeseriesOutliers(UUID id, PagingInfo pagingInfo) throws NexosisClientException;

    /**
     * distance metrics are only available on anomaly detection sessions. A 404 will be returned for other sessions.
     *
     * @param id The identifier of the session.
     * @param pagingInfo Paging instructions. PageNumber: 0 and PageSize: 50 by default
     * @return
     * @throws NexosisClientException
     */
    DistanceMetricResponse getDistanceMetrics(UUID id, PagingInfo pagingInfo) throws NexosisClientException;

    /**
     *
     * an indicator on the session called *supportsFeatureImportance* identifies if feature importance scores will be available
     *
     * @param id The identifier of the session.
     * @param pagingInfo Paging instructions. PageNumber: 0 and PageSize: 50 by default
     * @return
     * @throws NexosisClientException
     */
    FeatureImportanceResponse getFeatureImportanceScores(UUID id, PagingInfo pagingInfo) throws NexosisClientException;
}
