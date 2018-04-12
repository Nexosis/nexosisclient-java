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
public class ClassificationModelSessionRequest extends ModelSessionRequest  {
    public ClassificationModelSessionRequest()
    {
        setPredictionDomain(PredictionDomain.CLASSIFICATION);
    }

    /**
     * For classification models, whether or not to balance classes during model building (default is true)
     */
    @JsonIgnore
    private Boolean balance;

    public Boolean getBalance() {
        if (getExtraParameters().get("balance") != null) {
            return Boolean.valueOf(getExtraParameters().get("balance"));
        } else {
            return null;
        }
    }

    public void setBalance(Boolean balance) {
        getExtraParameters().put("balance", balance.toString());
    }
}
