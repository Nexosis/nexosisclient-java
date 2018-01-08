package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "predictionDomain",
        "columns",
        "callbackUrl",
        "extraParameters"
})
public class ModelSessionRequest extends SessionRequest {
    private PredictionDomain predictionDomain;
    public HashMap<String, String> extraParameters  = new HashMap<>();

    /**
     * The {@link PredictionDomain PredictionDomain} of the Model to be built
     */
    public PredictionDomain getPredictionDomain() {
        return predictionDomain;
    }

    public void setPredictionDomain(PredictionDomain predictionDomain) {
        this.predictionDomain = predictionDomain;
    }

    /**
     * Extra parameters to alter the results returned from predicting
     */
    public  HashMap<String, String>  getExtraParameters() {
        return extraParameters;
    }

    public void setExtraParameters( HashMap<String, String>  extraParameters) {
        this.extraParameters = extraParameters;
    }

    protected void setExtraParameter(String name, Boolean value) {
        if (this.extraParameters == null)
            this.extraParameters = new  HashMap<>();

        if (value != null)
            this.extraParameters.put("name", value.toString());
    }

    protected Boolean getExtraParameter(String name) {
        if (this.extraParameters == null) {
            return null;
        }

        if (this.extraParameters.containsKey(name)) {
            if (Boolean.parseBoolean(this.extraParameters.get(name))) {
                return Boolean.valueOf(this.extraParameters.get(name));
            }
        }
        return null;
    }
}