package com.nexosis.model;


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
public class RegressionModelSessionRequest extends ModelSessionRequest {
    public RegressionModelSessionRequest()
    {
        this.setPredictionDomain(PredictionDomain.REGRESSION);
    }
}
