package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The data sets that the user has uploaded.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "items"
})
public class DataSetList extends Paged implements Serializable
{
    /**
     * Summaries of the data sets that have been uploaded
     *
     */
    @JsonProperty("items")
    @JsonPropertyDescription("Summaries of the data sets that have been uploaded")
    private List<DataSetSummary> items = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -9201827645013946314L;

    /**
     * Summaries of the data sets that have been uploaded
     * @return Returns a List&lt;T&gt; of {@link DataSetSummary DataSetSummary} objects.
     */
    @JsonProperty("dataSets")
    public List<DataSetSummary> getItems() {
        return items;
    }

    /**
     * Summaries of the data sets that have been uploaded
     *
     */
    @JsonProperty("dataSets")
    public void setItems(List<DataSetSummary> items) {
        this.items = items;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(items).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataSetList) == false) {
            return false;
        }
        DataSetList rhs = ((DataSetList) other);
        return new EqualsBuilder().append(items, rhs.items).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}