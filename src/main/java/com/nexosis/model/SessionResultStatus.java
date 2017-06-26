package com.nexosis.model;

import java.util.UUID;

public class SessionResultStatus {
    private UUID sessionId;
    private SessionStatus status;
    public UUID getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(UUID id) {
        this.sessionId = id;
    }

    public SessionStatus getStatus()
    {
        return status;
    }

    public void setStatus(SessionStatus status)
    {
        this.status = status;
    }
}