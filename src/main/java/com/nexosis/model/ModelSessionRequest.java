package com.nexosis.model;

public class ModelSessionRequest extends SessionRequest {
    private PredictionDomain predictionDomain;

    /**
     * The {@link PredictionDomain PredictionDomain} of the Model to be built
     */
    public PredictionDomain getPredictionDomain() {
        return predictionDomain;
    }

    public void setPredictionDomain(PredictionDomain predictionDomain) {
        this.predictionDomain = predictionDomain;
    }
}