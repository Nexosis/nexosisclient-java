package com.nexosis.model;

public class ColumnMetadata {
    private DataType dataType;
    private DataRole role;
    private ImputationStrategy imputation;
    private AggregationStrategy aggregation;

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataRole getRole() {
        return role;
    }

    public void setRole(DataRole role) {
        this.role = role;
    }

    public ImputationStrategy getImputation() {
        return imputation;
    }

    public void setImputation(ImputationStrategy imputation) {
        this.imputation = imputation;
    }

    public AggregationStrategy getAggregation() {
        return aggregation;
    }

    public void setAggregation(AggregationStrategy aggregation) {
        this.aggregation = aggregation;
    }
}
