package com.nexosis.impl;

import com.nexosis.ISessionClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.commons.lang3.StringUtils;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpRequest;
import org.joda.time.DateTime;

import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

import static com.nexosis.util.NexosisHeaders.NEXOSIS_SESSION_STATUS;

/**
 *
 */
public class SessionClient implements ISessionClient {
    private ApiConnection apiConnection;

    public SessionClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(SessionData data, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return createForecast(data, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(SessionData data, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return createForecast(data, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(SessionData data, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "dataSourceName");

        return createSessionInternal("sessions/forecast", data, null /* eventName */, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(String dataSourceName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return createForecast(dataSourceName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(String dataSourceName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return createForecast(dataSourceName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(String dataSourceName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSourceName, "dataSourceName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        Columns columns = new Columns();
        columns.setColumnMetadata(targetColumn, DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData data = new SessionData();
        data.setDataSourceName(dataSourceName);
        data.setColumns(columns);

        return createForecast(data, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(SessionData data, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return analyzeImpact(data, eventName, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(SessionData data, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return analyzeImpact(data, eventName, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(SessionData data, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "dataSourceName");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", data, eventName, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSourceName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return analyzeImpact(dataSourceName, eventName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSourceName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return analyzeImpact(dataSourceName, eventName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSourceName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSourceName, "dataSourceName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        Columns columns = new Columns();
        columns.setColumnMetadata(targetColumn, DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData data = new SessionData();
        data.setDataSourceName(dataSourceName);
        data.setColumns(columns);

        return analyzeImpact(data, eventName, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse trainModel(ModelSessionDetail data) throws NexosisClientException {
        return trainModel(data, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse trainModel(ModelSessionDetail data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "ModelSessionDetail.getDataSourceName()");
        Argument.IsNotNullOrEmpty(data.getTargetColumn(), "ModelSessionDetail.getTargetColumn()");

        return createSessionInternal("sessions/model", data, httpMessageTransformer);
    }

    private SessionResponse createSessionInternal(String path, SessionData data, String eventName, DateTime startDate,
                                                  DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl,
                                                  Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dataSourceName", data.getDataSourceName());
        parameters.put("startDate", startDate.toDateTimeISO().toString());
        parameters.put("endDate", endDate.toDateTimeISO().toString());

        if (!StringUtils.isEmpty(eventName)) {
            parameters.put("eventName", eventName);
        }

        parameters.put("resultInterval", resultInterval.value());
        if (!StringUtils.isEmpty((statusCallbackUrl))) {
            parameters.put("callbackUrl", statusCallbackUrl);
        }

        return apiConnection.post(SessionResponse.class, path, parameters, data, httpMessageTransformer);
    }

    private SessionResponse createSessionInternal(String path, ModelSessionDetail data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.post(SessionResponse.class, path, null, data, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list() throws NexosisClientException {
        return listSessionsInternal(CreateQueryParameters(null, null, null), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(ListQuery query) throws NexosisClientException {
        return listSessionsInternal(CreateQueryParameters(null, null, query), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, ListQuery query) throws NexosisClientException {
        return listSessionsInternal(CreateQueryParameters(dataSourceName, null, query), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, String eventName, ListQuery query) throws NexosisClientException {
        return listSessionsInternal(CreateQueryParameters(dataSourceName, eventName, query), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, String eventName, ListQuery query, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return listSessionsInternal(CreateQueryParameters(dataSourceName, eventName, query), httpMessageTransformer);
    }

    private SessionResponses listSessionsInternal(Map<String, Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.get(SessionResponses.class, "sessions", parameters, httpMessageTransformer);
    }

    private Map<String, Object> CreateQueryParameters(String dataSourceName, String eventName, ListQuery query) {
        if (query == null)
            query = new ListQuery();
        Map<String, Object> parameters = new HashMap<>();
        if (dataSourceName != null && !dataSourceName.equals(""))
            parameters.put("dataSourceName", dataSourceName);
        if (eventName != null && !eventName.equals(""))
            parameters.put("eventName", eventName);
        if (query.getStartDate() != null)
            parameters.put("requestedAfterDate", query.getStartDate().toDateTimeISO().toString());
        if (query.getEndDate() != null)
            parameters.put("requestedBeforeDate", query.getEndDate().toDateTimeISO().toString());
        parameters.put("page", query.getPageNumber());
        parameters.put("pageSize", query.getPageSize());
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() throws NexosisClientException {
        remove((String) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSourceName) throws NexosisClientException {
        remove(dataSourceName, (SessionType) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(SessionType type) throws NexosisClientException {
        remove(null, null, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSourceName, SessionType type) throws NexosisClientException {
        remove(dataSourceName, null, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSourceName, String eventName, SessionType type) throws NexosisClientException {

        Map<String, Object> parameters = new HashMap<>();
        if (!StringUtils.isEmpty(dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }
        if (!StringUtils.isEmpty(eventName)) {
            parameters.put("eventName", eventName);
        }
        if (type != null) {
            parameters.put("type", type.value());
        }

        removeSessionsInternal(parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSourceName, String eventName, SessionType type, DateTime requestedAfterDate, DateTime requestedBeforeDate) throws NexosisClientException {
        remove(dataSourceName, eventName, type, requestedAfterDate, requestedBeforeDate, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSourceName, String eventName, SessionType type, DateTime requestedAfterDate, DateTime requestedBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString());
        parameters.put("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString());

        if (!StringUtils.isEmpty(dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }
        if (!StringUtils.isEmpty(eventName)) {
            parameters.put("eventName", eventName);
        }
        if (type != null) {
            parameters.put("type", type.value());
        }

        removeSessionsInternal(parameters, httpMessageTransformer);
    }

    private void removeSessionsInternal(Map<String, Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        apiConnection.delete("sessions", parameters, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse get(UUID id) throws NexosisClientException {
        return this.get(id, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse get(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.get(SessionResponse.class, "sessions/" + id.toString(), null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResultStatus getStatus(UUID id) throws NexosisClientException {
        return getStatus(id, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResultStatus getStatus(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
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
    public void remove(UUID id) throws NexosisClientException {
        remove(id, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        apiConnection.delete("sessions/" + id, null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResults(UUID id) throws NexosisClientException {
        return getResultsInternal(id, null, (Action<HttpRequest, HttpResponse>) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResults(UUID id, String predictionInterval) throws NexosisClientException {
        return getResultsInternal(id, predictionInterval, (Action<HttpRequest, HttpResponse>) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResults(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return getResultsInternal(id, null, httpMessageTransformer);
    }

    private SessionResult getResultsInternal(UUID id, String predictionInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String, Object> parameters = new HashMap<>();
        if(predictionInterval != null && predictionInterval != "")
            parameters.put("predictionInterval", predictionInterval);
        return apiConnection.get(SessionResult.class, "sessions/" + id + "/results", parameters, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnsStatus getResults(UUID id, OutputStream output) throws NexosisClientException {
        return getResults(id, output, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnsStatus getResults(UUID id, OutputStream output, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(output, "output");
        return apiConnection.get(ReturnsStatus.class, "sessions/" + id + "/results", null, httpMessageTransformer, output, "text/csv");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeResults(UUID id, Writer output) throws NexosisClientException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeResults(UUID id, Writer output, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {

    }

    /**
     * @param id the identifier of a classification model building session
     * @return an array of the classes in the classification model and a matrix of each classes results
     * @throws NexosisClientException
     */
    @Override
    public ConfusionMatrixResponse getConfusionMatrix(UUID id) throws NexosisClientException {
        return apiConnection.get(ConfusionMatrixResponse.class, "sessions/" + id + "/results/confusionmatrix", null, null);
    }
}
