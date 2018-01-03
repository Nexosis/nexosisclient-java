package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "columns",
        "data"
})
public class DataSetDetail {
    @JsonProperty("data")
    private List<Map<String, String>> data = null;
    @JsonProperty("columns")
    private Columns columns;
    private final static long serialVersionUID = -645924934242566958L;

    public DataSetDetail() {
        columns = new Columns();
        data = new ArrayList<>();
    }

    /**
     * Get The data. The dictionaries added to the list should treat the keys case insensitive. The API ignores case on column names.
     *
     * @return the Data
     */

    /**
     * Metadata about each column in the dataset. This is initialized as a case-insensitive dictionary. The API ignores case for column names.
     *
     * @return Metadata about each column in the dataset.
     */
    public Columns getColumns() {
        return columns;
    }

    /**
     *
     * @param columns
     */
    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    /**
     *
     * @param data
     */
    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public List<Map<String, String>> getData() {
        return this.data;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(columns).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataSetDetail) == false) {
            return false;
        }
        DataSetDetail rhs = ((DataSetDetail) other);
        return new EqualsBuilder().append(columns, rhs.columns).append(data, rhs.data).isEquals();
    }
}
