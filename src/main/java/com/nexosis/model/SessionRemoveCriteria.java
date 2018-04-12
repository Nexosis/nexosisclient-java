package com.nexosis.model;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class SessionRemoveCriteria {
    private String dataSourceName;
    private SessionType type;
    private DateTime requestedAfterDate;
    private DateTime requestedBeforeDate;
    private String eventName;
    /**
     * The sessions removed should be only those based on this DataSource
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * Only sessions of this type should be removed
     */
    public SessionType getType() {
        return type;
    }

    public void setType(SessionType type) {
        this.type = type;
    }

    /**
     * Only sessions requested after this date should be removed
     */
    public DateTime getRequestedAfterDate() {
        return requestedAfterDate;
    }

    public void setRequestedAfterDate(DateTime requestedAfterDate) {
        this.requestedAfterDate = requestedAfterDate;
    }

    /**
     * Only sessions requested before this date should be removed
     */
    public DateTime getRequestedBeforeDate() {
        return requestedBeforeDate;
    }

    public void setRequestedBeforeDate(DateTime requestedBeforeDate) {
        this.requestedBeforeDate = requestedBeforeDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Map<String, Object> toParameters() {
        Map<String, Object> parameters = new HashMap<>();
        if (dataSourceName != null && !dataSourceName.equals("")) {
            parameters.put("dataSourceName", dataSourceName);
        }

        if (type != null) {
            parameters.put("type", type.value());
        }

        if (requestedAfterDate != null) {
            parameters.put("requestedAfterDate", requestedAfterDate.toDateTimeISO().toString());
        }

        if (requestedBeforeDate != null) {
            parameters.put("requestedBeforeDate", requestedBeforeDate.toDateTimeISO().toString());
        }

        if (eventName != null) {
            parameters.put("eventName", eventName);
        }

        return parameters;
    }


}
