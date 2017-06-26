package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "statusCode",
        "message",
        "errorType",
        "errorDetails"})
    public class ApiErrorResponse {
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("errorType")
    private String errorType;
    @JsonProperty("errorDetails")
    private Map<String, Object> errorDetails = new HashMap<String, Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5278666337954557977L;


    public int getStatusCode(){
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorType(){
        return this.errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }


    public Map<String, Object> getErrorDetails() {
        return this.errorDetails;
    }

    public void seterrorDetails(String name, Object value) {
        this.errorDetails.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperty() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(statusCode).append(errorType).append(errorDetails).append(additionalProperties).toHashCode();
    }
}
