package com.nexosis.model;

public class ImpactSessionRequest extends TimeSeriesSessionRequest {
    private String eventName;

    /**
     * The name of the event whose impact we are trying to determine
     */
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
