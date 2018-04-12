package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({})
public class Columns implements Serializable
{
    public Columns() {}

    public Columns(Map<String, ColumnsProperty> columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    @JsonIgnore
    private Map<String, ColumnsProperty> columnMetadata = new HashMap<>();
    private final static long serialVersionUID = 669279373972140997L;

    @JsonAnyGetter
    public Map<String, ColumnsProperty> getsetColumnMetadata() {
        return this.columnMetadata;
    }

    @JsonAnySetter
    public void setColumnMetadata(String columnName, ColumnsProperty columnProperty) {
        this.columnMetadata.put(columnName, columnProperty);
    }

    @JsonIgnore
    public void setColumnMetadata(String columnName, DataType dataType, DataRole role
            , ImputationStrategy imputation
            , AggregationStrategy aggregation) {
        ColumnsProperty prop = new ColumnsProperty();
        prop.setDataType(dataType);
        prop.setRole(role);
        prop.setAggregation(aggregation);
        prop.setImputation(imputation);
        this.columnMetadata.put(columnName, prop);
    }

    @JsonIgnore
    public void setColumnMetadata(String columnName, DataType dataType, DataRole role) {
        ColumnsProperty prop = new ColumnsProperty();
        prop.setDataType(dataType);
        prop.setRole(role);
        this.columnMetadata.put(columnName, prop);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(columnMetadata).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Columns) == false) {
            return false;
        }
        Columns rhs = ((Columns) other);
        return new EqualsBuilder().append(columnMetadata, rhs.columnMetadata).isEquals();
    }
}