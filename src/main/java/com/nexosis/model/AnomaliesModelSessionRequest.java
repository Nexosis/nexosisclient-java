package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "predictionDomain",
        "columns",
        "callbackUrl",
        "extraParameters"
})
public class AnomaliesModelSessionRequest extends ModelSessionRequest {
    public AnomaliesModelSessionRequest()
    {
        setPredictionDomain(PredictionDomain.ANOMALIES);
    }

    /**
     * For classification models, whether or not to balance classes during model building (default is true)
     */
    @JsonIgnore
    private Boolean containsAnomalies;

    public Boolean getContainsAnomalies() {
        if (getExtraParameters().get("containsAnomalies") != null) {
            return Boolean.valueOf(getExtraParameters().get("containsAnomalies"));
        } else {
            return null;
        }
    }

    public void setContainsAnomalies(Boolean balance) {
        getExtraParameters().put("containsAnomalies", balance.toString());
    }
}
