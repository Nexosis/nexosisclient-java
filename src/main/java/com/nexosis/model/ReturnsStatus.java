package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.http.HttpHeaders;

import static com.nexosis.util.NexosisHeaders.NEXOSIS_SESSION_STATUS;

public class ReturnsStatus {
    @JsonIgnore
    private SessionStatus sessionStatus;

    public void AssignStatus(HttpHeaders headersArray)
    {
        if (headersArray != null) {
            if (headersArray.containsKey(NEXOSIS_SESSION_STATUS)) {
                this.sessionStatus = SessionStatus.fromValue(headersArray.getFirstHeaderStringValue(NEXOSIS_SESSION_STATUS));
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
