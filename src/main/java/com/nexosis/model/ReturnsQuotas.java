package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.http.HttpHeaders;

import static com.nexosis.util.NexosisHeaders.*;

/**
 *
 */
public abstract class ReturnsQuotas {
    @JsonIgnore
    private int dataSetCountAllotted;
    @JsonIgnore
    private int dataSetCountCurrent;
    @JsonIgnore
    private int predictionCountAllotted;
    @JsonIgnore
    private int predictionCountCurrent;
    @JsonIgnore
    private int sessionCountAllotted;
    @JsonIgnore
    private int sessionCountCurrent;

    public int getDataSetCountAllotted() {
        return dataSetCountAllotted;
    }

    public int getDataSetCountCurrent() {
        return dataSetCountCurrent;
    }

    public int getPredictionCountAllotted() {
        return predictionCountAllotted;
    }

    public int getPredictionCountCurrent() {
        return predictionCountCurrent;
    }

    public int getSessionCountAllotted() {
        return sessionCountAllotted;
    }

    public int getSessionCountCurrent() {
        return sessionCountCurrent;
    }


    public void AssignQuotas(HttpHeaders headers)
    {
        if (headers != null) {
            if (headers.containsKey(NEXOSIS_ACCOUNT_DATASET_COUNT_ALLOTTED)) {
                this.dataSetCountAllotted = Integer.parseInt(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_DATASET_COUNT_ALLOTTED));
            }

            if (headers.containsKey(NEXOSIS_ACCOUNT_DATASET_COUNT_CURRENT)) {
                this.dataSetCountCurrent = Integer.parseInt(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_DATASET_COUNT_CURRENT));
            }

            if (headers.containsKey(NEXOSIS_ACCOUNT_PREDICTION_COUNT_ALLOTTED)) {
                this.predictionCountAllotted = Integer.parseInt(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_PREDICTION_COUNT_ALLOTTED));
            }

            if (headers.containsKey(NEXOSIS_ACCOUNT_PREDICTION_COUNT_CURRENT)) {
                this.predictionCountCurrent = Integer.parseInt(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_PREDICTION_COUNT_CURRENT));
            }

            if (headers.containsKey(NEXOSIS_ACCOUNT_SESSION_COUNT_ALLOTTED)) {
                this.sessionCountAllotted = Integer.parseInt(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_SESSION_COUNT_ALLOTTED));
            }

            if (headers.containsKey(NEXOSIS_ACCOUNT_SESSION_COUNT_CURRENT)) {
                this.sessionCountCurrent = Integer.parseInt(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_SESSION_COUNT_CURRENT));
            }
        }
    }
}
