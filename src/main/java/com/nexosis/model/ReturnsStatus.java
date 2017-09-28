package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.http.HttpHeaders;

public class ReturnsStatus {
    @JsonIgnore
    private SessionStatus sessionStatus;

    public void AssignStatus(HttpHeaders headersArray)
    {
        if (headersArray != null) {
            if (headersArray.containsKey("Nexosis-Session-Status")) {
                this.sessionStatus = SessionStatus.fromValue(((String)headersArray.get("Nexosis-Session-Status")).toLowerCase());
            }
        }
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
