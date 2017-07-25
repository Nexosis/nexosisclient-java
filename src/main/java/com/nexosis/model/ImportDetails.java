package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"items"})
public class ImportDetails {
    @JsonProperty("items")
    private List<ImportDetail> items = null;

    @JsonProperty("items")
    public List<ImportDetail> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<ImportDetail> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
