package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "items"
})
public class ViewDefinitionList implements Serializable {
    @JsonProperty("items")
    private List<ViewDefinition> items;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("items")
    public List<ViewDefinition> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<ViewDefinition> items) {
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
        if ((other instanceof ViewDefinitionList) == false) {
            return false;
        }
        ViewDefinitionList rhs = ((ViewDefinitionList) other);
        return new EqualsBuilder().append(items, rhs.items).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
