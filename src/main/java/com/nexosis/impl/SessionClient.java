package com.nexosis.impl;

import com.nexosis.ISessionClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import com.nexosis.util.JodaTimeHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public SessionResponse createForecast(InputStream input, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return createForecast(input, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(InputStream input, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return createForecast(input, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(InputStream input, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(input, "input");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        return createSessionInternal("sessions/forecast", input, null /* eventName */, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(DataSetData data, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return createForecast(data, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(DataSetData data, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return createForecast(data, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(DataSetData data, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        return createSessionInternal("sessions/forecast", data, null /* eventName */, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(String dataSetName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return createForecast(dataSetName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(String dataSetName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return createForecast(dataSetName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse createForecast(String dataSetName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resulltInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        return createSessionInternal("sessions/forecast", dataSetName, (String)null, targetColumn, startDate, endDate, resulltInterval, statusCallbackUrl, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(InputStream input, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return analyzeImpact(input, eventName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(InputStream input, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resulltInterval, String statusCallbackUrl) throws NexosisClientException {
        return analyzeImpact(input, eventName, targetColumn, startDate, endDate, resulltInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(InputStream input, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(input, "input");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", input, eventName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(DataSetData data, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return analyzeImpact(data, eventName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(DataSetData data, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return analyzeImpact(data, eventName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(DataSetData data, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", data, eventName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSetName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return analyzeImpact(dataSetName, eventName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSetName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return analyzeImpact(dataSetName, eventName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSetName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", dataSetName, eventName, targetColumn, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSetName, Columns metaData, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return analyzeImpact(dataSetName, metaData, eventName, startDate, endDate, resultInterval, null );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSetName, Columns metaData, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl) throws NexosisClientException {
        return analyzeImpact(dataSetName, metaData, eventName, startDate, endDate, resultInterval, statusCallbackUrl, null );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse analyzeImpact(String dataSetName, Columns metaData, String eventName, DateTime startDate, DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNull(metaData, "metaData");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        boolean foundTarget = false;
        for (Map.Entry<String, ColumnsProperty> column : metaData.getsetColumnMetadata().entrySet()) {
          if (column.getValue().getRole() == DataRole.TARGET) {
              foundTarget = true;
              break;
          }
        }

        if (!foundTarget)
            throw new IllegalArgumentException("Columns metadata must contain a TARGET column.");

        return createSessionInternal("sessions/impact", dataSetName, metaData, eventName, startDate, endDate, resultInterval, statusCallbackUrl, httpMessageTransformer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(InputStream input, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateForecast(input, targetColumn, startDate, endDate, resultInterval,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(InputStream input, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(input, "input");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        return createSessionInternal("sessions/forecast", input, null /* eventName */, targetColumn, startDate, endDate, resultInterval, null, httpMessageTransformer, true);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(DataSetData data, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateForecast(data, targetColumn, startDate, endDate, resultInterval,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(DataSetData data, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        return createSessionInternal("sessions/forecast", data, null /* eventName */, targetColumn, startDate, endDate, resultInterval,null, httpMessageTransformer, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(String dataSetName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateForecast(dataSetName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateForecast(String dataSetName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");

        return createSessionInternal("sessions/forecast", dataSetName, (String)null /* eventName */, targetColumn, startDate, endDate, resultInterval, null, httpMessageTransformer, true);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(InputStream input, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateImpact(input, eventName, targetColumn, startDate, endDate, resultInterval,null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(InputStream input, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(input, "input");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", input, eventName, targetColumn, startDate, endDate, resultInterval, null, httpMessageTransformer, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(DataSetData data, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException
    {
        return estimateImpact(data, eventName, targetColumn, startDate, endDate, resultInterval,null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(DataSetData data, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNull(data, "data");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", data, eventName, targetColumn, startDate, endDate, resultInterval,null, httpMessageTransformer, true);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(String dataSetName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval) throws NexosisClientException {
        return estimateImpact(dataSetName, eventName, targetColumn, startDate, endDate, resultInterval, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponse estimateImpact(String dataSetName, String eventName, String targetColumn, DateTime startDate, DateTime endDate, ResultInterval resultInterval, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(dataSetName, "dataSetName");
        Argument.IsNotNullOrEmpty(targetColumn, "targetColumn");
        Argument.IsNotNullOrEmpty(eventName, "eventName");

        return createSessionInternal("sessions/impact", dataSetName, eventName, targetColumn, startDate, endDate, resultInterval, null, httpMessageTransformer, true);
    }


    private SessionResponse createSessionInternal(String path, InputStream input, String eventName, String targetColumn, DateTime startDate,
                                                        DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer, boolean isEstimate) throws NexosisClientException
    {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("targetColumn",targetColumn));
        parameters.add(new BasicNameValuePair("startDate", startDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("endDate", endDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("isEstimate", Boolean.toString(isEstimate)));

        if (!StringUtils.isEmpty(eventName))
        {
            parameters.add(new BasicNameValuePair("eventName", eventName));
        }
        parameters.add(new BasicNameValuePair("resultInterval", resultInterval.value()));
        if (!StringUtils.isEmpty(statusCallbackUrl))
        {
            parameters.add(new BasicNameValuePair("callbackUrl", statusCallbackUrl));
        }

        return apiConnection.post(SessionResponse.class, path, parameters, input, httpMessageTransformer);
    }

    private SessionResponse createSessionInternal(String path, DataSetData data, String eventName, String targetColumn, DateTime startDate,
                                                   DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer, boolean isEstimate) throws NexosisClientException
    {
        List<NameValuePair> parameters = new ArrayList<>();
        if (!StringUtils.isEmpty(data.getDataSetName())) {
            parameters.add(new BasicNameValuePair("dataSetName", data.getDataSetName()));
        }
        if (!StringUtils.isEmpty(targetColumn)) {
            parameters.add(new BasicNameValuePair("targetColumn", targetColumn));
        }
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

    private SessionResponse createSessionInternal(String path, String dataSetName, Columns metadata, String eventName, DateTime startDate,
                                                  DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer, boolean isEstimate) throws NexosisClientException
    {
        DataSetData data = new DataSetData();
        data.setDataSetName(dataSetName);
        data.setColumns(metadata);

        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
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


    private SessionResponse createSessionInternal(String path, String dataSetName, String eventName, String targetColumn, DateTime startDate,
                                                  DateTime endDate, ResultInterval resultInterval, String statusCallbackUrl, Action<HttpRequest, HttpResponse> httpMessageTransformer, boolean isEstimate) throws NexosisClientException  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
        parameters.add(new BasicNameValuePair("targetColumn", targetColumn));
        parameters.add(new BasicNameValuePair("startDate", startDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("endDate", endDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("isEstimate", Boolean.toString(isEstimate)));

        if (!StringUtils.isEmpty(eventName)) {
            parameters.add(new BasicNameValuePair("eventName", eventName));
        }

        parameters.add(new BasicNameValuePair("resultInterval", resultInterval.value()));

        if (!StringUtils.isEmpty(statusCallbackUrl)) {
            parameters.add(new BasicNameValuePair("callbackUrl", statusCallbackUrl));
        }

        return apiConnection.post(SessionResponse.class, path, parameters, (Object)null, httpMessageTransformer);
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
    public SessionResponses list(String dataSetName) throws NexosisClientException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSetName", dataSetName));

        return listSessionsInternal(parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSetName, String eventName) throws NexosisClientException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
        parameters.add(new BasicNameValuePair("eventName", eventName));

        return listSessionsInternal(parameters, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSetName, String eventName, DateTime requestedAfterDate, DateTime requestedBeforeDate) throws NexosisClientException {
        return list(dataSetName, eventName, requestedAfterDate, requestedBeforeDate, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionResponses list(String dataSetName, String eventName, DateTime requestedAfterDate, DateTime requestedBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
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
    public void remove(String dataSetName) throws NexosisClientException {
        remove(dataSetName, (SessionType)null);
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
    public void remove(String dataSetName, SessionType type) throws NexosisClientException {
        remove(dataSetName, null, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSetName, String eventName, SessionType type) throws NexosisClientException {

        List<NameValuePair> parameters = new ArrayList<>();
        if (!StringUtils.isEmpty(dataSetName))
        {
            parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
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
    public void remove(String dataSetName, String eventName, SessionType type, DateTime requestedAfterDate, DateTime requestedBeforeDate) throws NexosisClientException {
        remove(dataSetName, eventName, type, requestedAfterDate, requestedBeforeDate, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String dataSetName, String eventName, SessionType type, DateTime requestedAfterDate, DateTime requestedBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString()));
        parameters.add(new BasicNameValuePair("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString()));

        if (!StringUtils.isEmpty(dataSetName))
        {
            parameters.add(new BasicNameValuePair("dataSetName", dataSetName));
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
