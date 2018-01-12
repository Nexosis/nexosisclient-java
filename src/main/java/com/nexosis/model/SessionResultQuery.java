package com.nexosis.model;

import com.google.api.client.json.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionResultQuery extends Paged {
    private UUID sessionId;
    private String predictionInterval;
    private String contentType = Json.MEDIA_TYPE;
    private PagingInfo page;
    /**
     * Session identifier for which to retrieve results
     */
    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    /**
     *  The results returned will be from the given prediction interval.
     */
    public String getPredictionInterval() {
        return predictionInterval;
    }

    public void setPredictionInterval(String predictionInterval) {
        this.predictionInterval = predictionInterval;
    }

    public PagingInfo getPage() {
        return page;
    }

    public void setPage(PagingInfo page) {
        this.page = page;
    }

    /**
     *
     * @param contentType
     */
    public void setContentType(String contentType) throws IllegalArgumentException {
        if (!contentType.equalsIgnoreCase("text/csv") && !contentType.equalsIgnoreCase(Json.MEDIA_TYPE)) {
            throw new IllegalArgumentException("contentType must be set to text/csv or application/json");
        }
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Map<String, Object> toParameters() {
        Map<String, Object> parameters = new HashMap<>();

        if (page != null) {
            parameters.putAll(page.toParameters());
        }

        parameters.put("sessionId", this.sessionId.toString());

        if (predictionInterval != null) {
            parameters.put("predictionInterval", predictionInterval);
        }

        return parameters;
    }

}
