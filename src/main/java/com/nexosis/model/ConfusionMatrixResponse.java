package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ConfusionMatrixResponse extends SessionResponse {

    @JsonProperty("classes")
    private String[] classes;
    @JsonProperty("confusionMatrix")
    private int[][] confusionMatrix;

    @JsonProperty("classes")
    public String[] getClasses() {
        return this.classes;
    }
    @JsonProperty("classes")
    public void setClasses(String[] classes) {
        this.classes = classes;
    }

    @JsonProperty("confusionMatrix")
    public int[][] getConfusionMatrix() {
        return this.confusionMatrix;
    }
    @JsonProperty("confusionMatrix")
    public void setConfusionMatrix(int[][] confusionMatrix) {
        this.confusionMatrix = confusionMatrix;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(super.hashCode()).append(this.classes).append(this.confusionMatrix).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ConfusionMatrixResponse) == false) {
            return false;
        }
        if (! super.equals(other)) return false;
        ConfusionMatrixResponse rhs = ((ConfusionMatrixResponse) other);
        return new EqualsBuilder().append(classes,rhs.classes).append(confusionMatrix,rhs.confusionMatrix).isEquals();
    }
}
