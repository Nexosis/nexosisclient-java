package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClassificationModelPredictionRequest extends ModelPredictionRequest {
    @JsonIgnore
    private boolean includeClassScores;

    /**
     *For classification models, whether or not to include class scores for each possible class (default is false)
     */
    public boolean isIncludeClassScores() {
        return Boolean.valueOf(getExtraParameters().get("includeClassScores"));
    }

    public void setIncludeClassScores(boolean includeClassScores) {
        getExtraParameters().put("includeClassScores", Boolean.toString(includeClassScores));
    }
}