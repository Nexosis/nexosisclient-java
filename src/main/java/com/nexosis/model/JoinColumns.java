package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSet",
        "columnsOptions",
        "joins"
})
public class JoinColumns implements Serializable{
    @JsonIgnore
    private Map<String, ColumnOptions> columnMetadata = new HashMap<>();
    @JsonAnyGetter
    public Map<String, ColumnOptions> getsetColumnMetadata() {
        return this.columnMetadata;
    }

    @JsonAnySetter
    public void setColumnMetadata(String columnName, ColumnOptions columnOptions) {
        this.columnMetadata.put(columnName, columnOptions);
    }

    @JsonIgnore
    public void setColumnMetadata(String columnName, String alias, ResultInterval interval) {
        ColumnOptions prop = new ColumnOptions();
        prop.setAlias(alias);
        prop.setJoinInterval(interval);
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
        if ((other instanceof JoinColumns) == false) {
            return false;
        }
        JoinColumns rhs = ((JoinColumns) other);
        return new EqualsBuilder().append(columnMetadata, rhs.columnMetadata).isEquals();
    }
}
