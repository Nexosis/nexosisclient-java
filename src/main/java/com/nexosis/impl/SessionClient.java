package com.nexosis.impl;

import com.nexosis.ISessionClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;

import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private SessionResponse createSessionInternal(String path, SessionData data, String eventName, DateTime startDate,
                                                   DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer, boolean isEstimate) throws NexosisClientException
    {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSourceName", data.getDataSourceName()));
        parameters.add(new BasicNameValuePair("startDate", startDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("endDate", endDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("isEstimate", Boolean.toString(isEstimate)));

        if (!StringUtils.isEmpty(eventName))
        {
            parameters.add(new BasicNameValuePair("eventName", eventName));
        }

        parameters.add(new BasicNameValuePair("resultInterval", resultInterval.value()));
        if (!StringUtils.isEmpty((statusCallbackUrl)))
        {
            parameters.add(new BasicNameValuePair("callbackUrl", statusCallbackUrl));
        }

        return apiConnection.post(SessionResponse.class, path, parameters, data, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list() throws NexosisClientException {
        return listSessionsInternal(new ArrayList<NameValuePair>(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName) throws NexosisClientException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSourceName", dataSourceName));

        return listSessionsInternal(parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSourceName, String eventName) throws NexosisClientException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSourceName", dataSourceName));
        parameters.add(new BasicNameValuePair("eventName", eventName));

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
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSourceName", dataSourceName));
        parameters.add(new BasicNameValuePair("eventName", eventName));
        parameters.add(new BasicNameValuePair("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString() ));
        parameters.add(new BasicNameValuePair("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString()));

        return listSessionsInternal(parameters, httpMessageTransformer);
    }

    private SessionResponses listSessionsInternal(List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
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

        List<NameValuePair> parameters = new ArrayList<>();
        if (!StringUtils.isEmpty(dataSourceName))
        {
            parameters.add(new BasicNameValuePair("dataSourceName", dataSourceName));
        }
        if (!StringUtils.isEmpty(eventName))
        {
            parameters.add(new BasicNameValuePair("eventName", eventName));
        }
        if (type != null)
        {
            parameters.add(new BasicNameValuePair("type", type.value()));
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
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString()));

        if (!StringUtils.isEmpty(dataSourceName))
        {
            parameters.add(new BasicNameValuePair("dataSourceName", dataSourceName));
        }
        if (!StringUtils.isEmpty(eventName))
        {
            parameters.add(new BasicNameValuePair("eventName", eventName));
        }
        if (type != null)
        {
            parameters.add(new BasicNameValuePair("type", type.value()));
        }

        removeSessionsInternal(parameters, httpMessageTransformer);
    }

    private void removeSessionsInternal(List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
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
        AddSessionResultStatus localTransform = new AddSessionResultStatus(id, httpMessageTransformer);
        return apiConnection.head(SessionResultStatus.class, "sessions/" + id, null, localTransform);
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

    public class AddSessionResultStatus implements Action<HttpRequest, HttpResponse> {
        private UUID sessionId;
        private Action<HttpRequest, HttpResponse> httpMessageTransformer;

        public AddSessionResultStatus(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) {
            this.sessionId = id;
            this.httpMessageTransformer = httpMessageTransformer;
        }

        @Override
        public void invoke(HttpRequest request, HttpResponse response) throws Exception {
            if (
                    (response != null) &&
                            (ApiConnection.isSuccessStatusCode(response.getStatusLine().getStatusCode())) &&
                            (response.getHeaders("Nexosis-Session-Status") != null)
                    ) {

                SessionResultStatus sessionStatus = new SessionResultStatus();
                sessionStatus.setSessionId(sessionId);
                sessionStatus.setStatus(SessionStatus.fromValue(response.getHeaders("Nexosis-Session-Status")[0].getValue()));

                HttpEntity entity = new StringEntity(apiConnection.getObjectMapper().writeValueAsString(sessionStatus));
                response.setEntity(entity);
            }

            if (httpMessageTransformer != null) {
                httpMessageTransformer.invoke(request, response);
            }
        }


    }


}
