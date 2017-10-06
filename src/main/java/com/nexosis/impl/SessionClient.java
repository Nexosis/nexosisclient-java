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

        return createSessionInternal("sessions/forecast", data, null /* eventName */, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
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
        return analyzeImpact(data, eventName, startDate, endDate, resultInterval, statusCallbackUrl,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(SessionData data, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "dataSourceName");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", data, eventName, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
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

        return createSessionInternal("sessions/model", data, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(SessionData data, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateForecast(data, startDate, endDate, resultInterval,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(SessionData data, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "dataSourceName");

        return createSessionInternal("sessions/forecast", data, null /* eventName */, startDate, endDate, resultInterval,null, httpMessageTransformer, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(String dataSourceName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateForecast(dataSourceName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(String dataSourceName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSourceName, "dataSourceName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        Columns columns = new Columns();
        columns.setColumnMetadata(targetColumn, DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData data = new SessionData();
        data.setDataSourceName(dataSourceName);
        data.setColumns(columns);

        return estimateForecast(data, startDate, endDate, resultInterval, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(SessionData data, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException  {
        return estimateImpact(data, eventName, startDate, endDate, resultInterval,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(SessionData data, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "dataSourceName");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", data, eventName, startDate, endDate, resultInterval,null, httpMessageTransformer, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(String dataSourceName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateImpact(dataSourceName, eventName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(String dataSourceName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSourceName, "dataSourceName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        Columns columns = new Columns();
        columns.setColumnMetadata(targetColumn, DataType.NUMERIC, DataRole.TARGET, ImputationStrategy.ZEROES, AggregationStrategy.SUM);

        SessionData data = new SessionData();
        data.setDataSourceName(dataSourceName);
        data.setColumns(columns);

        return estimateImpact(data, eventName, startDate, endDate, resultInterval, httpMessageTransformer);
    }

    public SessionResponse estimateTrainModel(ModelSessionDetail data) throws NexosisClientException
    {
        return this.estimateTrainModel(data, null);
    }

    public SessionResponse estimateTrainModel(ModelSessionDetail data, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(data.getDataSourceName(), "data.DataSetName");
        data.setIsEstimate(true);
        return this.createSessionInternal("sessions/model", data, httpMessageTransformer, true);
    }

    private SessionResponse createSessionInternal(String path, SessionData data, String eventName, DateTime startDate,
                                                   DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl,
                                                  Action<HttpRequest, HttpResponse> httpMessageTransformer, boolean isEstimate) throws NexosisClientException
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dataSourceName", data.getDataSourceName());
        parameters.put("startDate", startDate.toDateTimeISO().toString());
        parameters.put("endDate", endDate.toDateTimeISO().toString());
        parameters.put("isEstimate", Boolean.toString(isEstimate));

        if (!StringUtils.isEmpty(eventName))
        {
            parameters.put("eventName", eventName);
        }

        parameters.put("resultInterval", resultInterval.value());
        if (!StringUtils.isEmpty((statusCallbackUrl)))
        {
            parameters.put("callbackUrl", statusCallbackUrl);
        }

        return apiConnection.post(SessionResponse.class, path, parameters, data, httpMessageTransformer);
    }

    private SessionResponse createSessionInternal(String path, ModelSessionDetail data, Action<HttpRequest, HttpResponse> httpMessageTransformer,
                                                  boolean isEstimate) throws NexosisClientException
    {
        data.setIsEstimate(isEstimate);
        return apiConnection.post(SessionResponse.class, path, null, data, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list() throws NexosisClientException {
        return listSessionsInternal(new HashMap<String,Object>(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName) throws NexosisClientException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dataSourceName", dataSourceName);

        return listSessionsInternal(parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, String eventName) throws NexosisClientException {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("dataSourceName", dataSourceName);
        parameters.put("eventName", eventName);

        return listSessionsInternal(parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, String eventName, DateTime requestedAfterDate, DateTime requestedBeforeDate) throws NexosisClientException {
        return list(dataSourceName, eventName, requestedAfterDate, requestedBeforeDate, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, String eventName, DateTime requestedAfterDate, DateTime requestedBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("dataSourceName", dataSourceName);
        parameters.put("eventName", eventName);
        parameters.put("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString());
        parameters.put("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString());

        return listSessionsInternal(parameters, httpMessageTransformer);
    }

    private SessionResponses listSessionsInternal(Map<String, Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        return apiConnection.get(SessionResponses.class, "sessions", parameters, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() throws NexosisClientException {
        remove((String)null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSourceName) throws NexosisClientException {
        remove(dataSourceName, (SessionType)null);
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
        if (!StringUtils.isEmpty(dataSourceName))
        {
            parameters.put("dataSourceName", dataSourceName);
        }
        if (!StringUtils.isEmpty(eventName))
        {
            parameters.put("eventName", eventName);
        }
        if (type != null)
        {
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

        if (!StringUtils.isEmpty(dataSourceName))
        {
            parameters.put("dataSourceName", dataSourceName);
        }
        if (!StringUtils.isEmpty(eventName))
        {
            parameters.put("eventName", eventName);
        }
        if (type != null)
        {
            parameters.put("type", type.value());
        }

        removeSessionsInternal(parameters, httpMessageTransformer);
    }

    private void removeSessionsInternal(Map<String, Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
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
        return getResults(id, (Action<HttpRequest, HttpResponse>)null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResult getResults(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.get(SessionResult.class, "sessions/" + id + "/results", null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnsStatus getResults(UUID id, OutputStream output) throws NexosisClientException
    {
        return getResults(id, output, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnsStatus getResults(UUID id, OutputStream output, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
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
}
