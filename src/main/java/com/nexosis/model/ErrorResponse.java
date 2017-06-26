package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "statusCode",
        "errorType",
        "message",
        "activityId",
        "companyId"
})
public class ErrorResponse
{
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("errorType")
    private String errorType;
    @JsonProperty("message")
    private String message;
    @JsonProperty("activityId")
    private UUID activityId;
    @JsonProperty("companyId")
    private UUID companyId;
    @JsonProperty("errorDetails")
    private HashMap<String, Object> errorDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ErrorResponse()
    {
        errorDetails = new HashMap<String, Object>();
    }

    @JsonProperty("statusCode")
    public int getStatusCode() {
        return statusCode;
    }
    @JsonProperty("statusCode")
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("errorType")
    public String getErrorType() {
        return errorType;
    }
    @JsonProperty("errorType")
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonProperty("errorDetails")
    public HashMap<String, Object> getErrorDetails() {
        return errorDetails;
    }
    @JsonProperty("errorDetails")
    public void setErrorDetails(HashMap<String, Object> errorDetails) {
        this.errorDetails = errorDetails;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("companyId")
    public UUID getCompanyId() {
        return companyId;
    }
    @JsonProperty("companyId")
    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    @JsonProperty("activityId")
    public UUID getActivityId() {
        return activityId;
    }
    @JsonProperty("activityId")
    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}