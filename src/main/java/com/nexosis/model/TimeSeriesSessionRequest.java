package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "columns",
        "callbackUrl",
        "startDate",
        "endDate",
        "resultInterval"

})
public abstract class TimeSeriesSessionRequest extends SessionRequest {
    @JsonProperty("startDate")
    private DateTime startDate;
    @JsonProperty("endDate")
    private DateTime endDate;
    @JsonProperty("resultInterval")
    private ResultInterval resultInterval;

    /**
     * The Start Date of the session
     */
    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * The End Date of the session
     */
    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * /// The ResultInterval that Nexosis should use for forecasting
     */
    public ResultInterval getResultInterval() {
        return resultInterval;
    }

    public void setResultInterval(ResultInterval resultInterval) {
        this.resultInterval = resultInterval;
    }

    public Map<String,Object> toParameters() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.putAll(super.toParameters());

        if (startDate != null) {
            parameters.put("startDate", startDate.toDateTimeISO().toString());
        }
        if (endDate != null) {
            parameters.put("endDate", endDate.toDateTimeISO().toDateTimeISO());
        }

        if (resultInterval != null) {
            parameters.put("resultInterval", resultInterval);
        }

        return parameters;
    }
}