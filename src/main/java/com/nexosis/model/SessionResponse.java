package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sessionId",
        "type",
        "status",
        "availablePredictionIntervals",
        "requestedDate",
        "statusHistory",
        "extraParameters",
        "columns",
        "dataSetName",
        "targetColumn",
        "startDate",
        "endDate",
        "callbackUrl",
        "resultInterval",
        "links"
})
// JSON Name Result
@JsonSubTypes({
    @JsonSubTypes.Type(value = SessionResult.class)
})
public class SessionResponse extends ReturnsQuotas implements Serializable
{
    @JsonProperty("sessionId")
    private UUID sessionId;
    @JsonProperty("type")
    private SessionType type;
    @JsonProperty("status")
    private SessionStatus status;
    @JsonProperty("statusHistory")
    private List<SessionStatusHistory> statusHistory = null;
    @JsonProperty("messages")
    public List<StatusMessage> messages = null;
    @JsonProperty("extraParameters")
    private ExtraParameters extraParameters;
    @JsonProperty("dataSourceName")
    private String dataSourceName;
    @JsonProperty("targetColumn")
    private String targetColumn;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("predictionDomain")
    private PredictionDomain predictionDomain;
    @JsonProperty("modelId")
    private UUID modelId;
    @JsonProperty("requestedDate")
    private DateTime requestedDate;
    @JsonProperty("startDate")
    private DateTime startDate;
    @JsonProperty("endDate")
    private DateTime endDate;
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    @JsonProperty("resultInterval")
    private ResultInterval resultInterval;
    @JsonProperty("links")
    private List<Link> links = null;
    @JsonProperty("columns")
    private Columns columns;
    @JsonProperty("availablePredictionIntervals")
    private String[] availablePredictionIntervals = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8794088706239187049L;

    @JsonProperty("sessionId")
    public UUID getSessionId() {
        return sessionId;
    }

    @JsonProperty("sessionId")
    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("type")
    public SessionType getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(SessionType type) {
        this.type = type;
    }

    @JsonProperty("status")
    public SessionStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    @JsonProperty("statusHistory")
    public List<SessionStatusHistory> getStatusHistory() {
        return statusHistory;
    }

    @JsonProperty("statusHistory")
    public void setStatusHistory(List<SessionStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    @JsonProperty("messages")
    public List<StatusMessage> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<StatusMessage> messages) {
        this.messages = messages;
    }

    @JsonProperty("extraParameters")
    public ExtraParameters getExtraParameters() {
        return extraParameters;
    }

    @JsonProperty("extraParameters")
    public void setExtraParameters(ExtraParameters extraParameters) {
        this.extraParameters = extraParameters;
    }

    @JsonProperty("dataSourceName")
    public String getDataSourceName() {
        return dataSourceName;
    }
    @JsonProperty("dataSourceName")
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @JsonProperty("targetColumn")
    public String getTargetColumn() {
        return targetColumn;
    }

    @JsonProperty("targetColumn")
    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    @JsonProperty("eventName")
    public String getEventName() {
        return eventName;
    }

    @JsonProperty("eventName")
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @JsonProperty("predictionDomain")
    public PredictionDomain getPredictionDomain() {
        return predictionDomain;
    }

    @JsonProperty("predictionDomain")
    public void setPredictionDomain(PredictionDomain predictionDomain) {
        this.predictionDomain = predictionDomain;
    }

    @JsonProperty("modelId")
    public UUID getModelId() {
        return modelId;
    }

    @JsonProperty("modelId")
    public void setModelId(UUID modelId) {
        this.modelId = modelId;
    }

    @JsonProperty("requestedDate")
    public DateTime getRequestedDate() {
        return requestedDate;
    }

    @JsonProperty("requestedDate")
    public void setRequestedDate(DateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    @JsonProperty("startDate")
    public DateTime getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public DateTime getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("callbackUrl")
    public String getCallbackUrl() {
        return callbackUrl;
    }

    @JsonProperty("callbackUrl")
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @JsonProperty("resultInterval")
    public ResultInterval getResultInterval() {
        return resultInterval;
    }

    @JsonProperty("resultInterval")
    public void setResultInterval(ResultInterval resultInterval) {
        this.resultInterval = resultInterval;
    }

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    @JsonProperty("columns")
    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    @JsonProperty("availablePredictionIntervals")
    public String[] getAvailablePredictionIntervals(){
        return this.availablePredictionIntervals;
    }

    @JsonProperty("availablePredictionIntervals")
    public void setAvailablePredictionIntervals(String[] values){
        this.availablePredictionIntervals = values;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sessionId).append(type).append(status).append(statusHistory).append(extraParameters).append(columns).append(dataSourceName).append(targetColumn).append(requestedDate).append(startDate).append(endDate).append(callbackUrl).append(resultInterval).append(links).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SessionResponse) == false) {
            return false;
        }
        SessionResponse rhs = ((SessionResponse) other);
        return new EqualsBuilder().append(sessionId, rhs.sessionId).append(type, rhs.type).append(status, rhs.status).append(statusHistory, rhs.statusHistory).append(extraParameters, rhs.extraParameters).append(columns, rhs.columns).append(dataSourceName, rhs.dataSourceName).append(targetColumn, rhs.targetColumn).append(requestedDate, rhs.requestedDate).append(startDate, rhs.startDate).append(endDate, rhs.endDate).append(callbackUrl, rhs.callbackUrl).append(resultInterval, rhs.resultInterval).append(links, rhs.links).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}