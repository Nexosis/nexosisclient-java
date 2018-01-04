package com.nexosis.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelPredictionRequest {

    public ModelPredictionRequest(UUID modelId, List<Map<String, String>> data)
    {
        this.setModelId(modelId);
        this.setData(data);
    }

    private UUID modelId;

    private List<Map<String, String>> data;

    /**
     * The Model that we're using for predictions
     */
    public UUID getModelId() {
        return modelId;
    }

    public void setModelId(UUID modelId) {
        this.modelId = modelId;
    }

    /**
     * The Feature data to use when predicting
     */
    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }
}
