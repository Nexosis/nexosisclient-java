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
     *
     * HEAD of https://ml.nexosis.com/api/sessions/{id}
     *
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
     *
     * @param id the identifier of a classification model building session
     * @return an array of the classes in the classification model and a matrix of each classes results
     * @throws NexosisClientException
     */
    ConfusionMatrixResponse getConfusionMatrix(UUID id) throws NexosisClientException;
}
