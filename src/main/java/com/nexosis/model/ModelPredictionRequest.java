package com.nexosis.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelPredictionRequest {
    private UUID modelId;
    private List<Map<String, String>> data;
    private HashMap<String, String> extraParameters;

    public ModelPredictionRequest() {
        extraParameters = new  HashMap<>();
    }

    public ModelPredictionRequest(UUID modelId, List<Map<String, String>> data) {
        this.setModelId(modelId);
        this.setData(data);
        extraParameters = new HashMap<>();
    }

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
