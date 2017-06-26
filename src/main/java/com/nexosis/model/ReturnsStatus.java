package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.http.Header;

public class ReturnsStatus {
    @JsonIgnore
    private SessionStatus sessionStatus;

    public void AssignStatus(Header[] headersArray)
    {
        if (headersArray != null) {
            for (Header h : headersArray) {
                if (h.getName().equalsIgnoreCase("Nexosis-Session-Status")) {
                    this.sessionStatus  = SessionStatus.fromValue(h.getValue().toLowerCase());
                    break;
                }
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
