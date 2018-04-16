package com.nexosis.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.Json;
import com.nexosis.IContestClient;
import com.nexosis.ISessionClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;

import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import static com.nexosis.util.NexosisHeaders.NEXOSIS_SESSION_STATUS;

/**
 *
 */
public class SessionClient implements ISessionClient {
    private ApiConnection apiConnection;
    private IContestClient contestClient;
    private Action<HttpRequest, HttpResponse> httpMessageTransformer;

    public SessionClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
        this.contestClient = new ContestClient(apiConnection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IContestClient getContest() { return this.contestClient; }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action<HttpRequest, HttpResponse> getHttpMessageTransformer() {
        return httpMessageTransformer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer) {
        this.httpMessageTransformer = httpMessageTransformer;
        contestClient.setHttpMessageTransformer(httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(ForecastSessionRequest request) throws NexosisClientException {
        Argument.IsNotNull(request, "ForecastSessionRequest");
        Argument.IsNotNullOrEmpty(request.getDataSourceName(), "ForecastSessionRequest.dataSourceName");

        return apiConnection.post(SessionResponse.class, "/sessions/forecast", null, request, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(ImpactSessionRequest request) throws NexosisClientException {
        Argument.IsNotNull(request, "ImpactSessionRequest");
        Argument.IsNotNullOrEmpty(request.getDataSourceName(), "ImpactSessionRequest.dataSourceName");
        Argument.IsNotNullOrEmpty(request.getEventName(), "ImpactSessionRequest.eventName");

        return apiConnection.post(SessionResponse.class, "/sessions/impact", null, request, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse trainModel(ModelSessionRequest request) throws NexosisClientException {
        Argument.IsNotNull(request, "ModelSessionRequest");
        Argument.IsNotNullOrEmpty(request.getDataSourceName(), "ModelSessionRequest.dataSourceName");

        return apiConnection.post(SessionResponse.class,"/sessions/model", null, request, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(SessionQuery query) throws NexosisClientException {
        Argument.IsNotNull(query, "SessionQuery");

        Map<String, Object> parameters = query.toParameters();
        return apiConnection.get(SessionResponses.class, "/sessions", parameters, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(SessionRemoveCriteria criteria) throws NexosisClientException {
        Argument.IsNotNull(criteria, "SessionRemoveCriteria");

        Map<String,Object> parameters = criteria.toParameters();
        apiConnection.delete("/sessions", parameters, this.httpMessageTransformer);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(UUID id) throws NexosisClientException {
        apiConnection.delete("sessions/" + id, null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse get(UUID id) throws NexosisClientException {
        return apiConnection.get(SessionResponse.class, "sessions/" + id.toString(), null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResultStatus getStatus(UUID id) throws NexosisClientException {
        SessionResultStatus s = new SessionResultStatus();
        s.setSessionId(id);
        String headStatus = apiConnection.head("sessions/" + id, null, httpMessageTransformer).getFirstHeaderStringValue(NEXOSIS_SESSION_STATUS);
        s.setStatus(SessionStatus.fromValue(headStatus));
        return s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResults(SessionResultQuery query) throws NexosisClientException {
        Argument.IsNotNull(query, "query");

        if (query.getContentType() != Json.MEDIA_TYPE) {
            throw new IllegalArgumentException("Content Type cannot be set to CSV unless you are writing it to a file. Use ISessionClient.getResults(SessionResultQuery query, OutputStream output).");
        }

        return apiConnection.get(SessionResult.class, "/sessions/"+ query.getSessionId().toString() + "/results", null, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnsStatus getResults(SessionResultQuery query, OutputStream output) throws NexosisClientException {
        Argument.IsNotNull(query, "query");
        Argument.IsNotNull(output, "output");

        return apiConnection.get(ReturnsStatus.class, "sessions/" + query.getSessionId().toString() + "/results", null, httpMessageTransformer, output, query.getContentType());
    }

    /**
     /**
     * {@inheritDoc}
     */
    @Override
    public ConfusionMatrixResponse getConfusionMatrix(UUID id) throws NexosisClientException {
        return apiConnection.get(ConfusionMatrixResponse.class, "sessions/" + id.toString() + "/results/confusionmatrix", null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResultAnomalyScores(UUID id, PagingInfo pagingInfo) throws NexosisClientException {
        if(pagingInfo == null)
                pagingInfo = PagingInfo.Default;
        return apiConnection.get(SessionResult.class, "/sessions/" + id.toString() + "/results/anomalyscores", pagingInfo.toParameters(), this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResultClassScores(UUID id, PagingInfo pagingInfo) throws NexosisClientException {
        if(pagingInfo == null)
            pagingInfo = PagingInfo.Default;
        return apiConnection.get(SessionResult.class, "/sessions/" + id.toString() + "/results/classscores", pagingInfo.toParameters(), this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutliersResponse getTimeseriesOutliers(UUID id, PagingInfo pagingInfo) throws NexosisClientException {
        if(pagingInfo == null)
            pagingInfo = PagingInfo.Default;
        return apiConnection.get(OutliersResponse.class, "/sessions/" + id.toString() + "/results/outliers", pagingInfo.toParameters(), this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistanceMetricResponse getDistanceMetrics(UUID id, PagingInfo pagingInfo) throws NexosisClientException {
        if(pagingInfo == null)
            pagingInfo = PagingInfo.Default;
        return apiConnection.get(DistanceMetricResponse.class, "/sessions/" + id.toString() + "/results/mahalanobisdistances", pagingInfo.toParameters(), this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FeatureImportanceResponse getFeatureImportanceScores(UUID id, PagingInfo pagingInfo) throws NexosisClientException {
        if(pagingInfo == null)
            pagingInfo = PagingInfo.Default;
        return apiConnection.get(FeatureImportanceResponse.class, "/sessions/" + id.toString() + "/results/featureimportance", pagingInfo.toParameters(), this.httpMessageTransformer);
    }


}
