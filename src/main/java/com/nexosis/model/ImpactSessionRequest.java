package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "eventName",
        "columns",
        "callbackUrl",
        "startDate",
        "endDate",
        "resultInterval"

})
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
