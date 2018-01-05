package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "severity",
        "message"
})

public class StatusMessage {
    @JsonProperty("severity")
    private MessageSeverity Severity;
    @JsonProperty("message")
    private String Message;

    @JsonProperty("severity")
    public void setMessageSeverity(MessageSeverity severity) {
        Severity = severity;
    }

    @JsonProperty("message")
    public void setMessage(String message) { Message = message; }
}
