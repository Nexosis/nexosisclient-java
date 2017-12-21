package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "predictionDomain",
        "callbackUrl",
        "columns"
})
public class ModelSessionDetail implements Serializable {

    /**
     * Name of the dataset or view for which to determine impact
     */
    @JsonProperty("dataSourceName")
    @JsonPropertyDescription("Name of the dataset or view for which to determine impact")
    private String dataSourceName;
    /**
     * Column in the specified data source for which to determine impact
     */
    @JsonProperty("targetColumn")
    @JsonPropertyDescription("Column in the specified data source for which to determine impact")
    private String targetColumn;
    /**
     *
     */
    @JsonProperty("predictionDomain")
    @JsonPropertyDescription("")
    private PredictionDomain predictionDomain;
    /**
     * The Webhook url that will receive updates when the Session status changes
     * If you provide a callback url, your response will contain a header named Nexosis-Webhook-Token. You will receive this
     * same header in the request message to your Webhook, which you can use to validate that the message came from Nexosis.
     */
    @JsonProperty("callbackUrl")
    @JsonPropertyDescription("The Webhook url that will receive updates when the Session status changes\r\nIf you provide a callback url, your response will contain a header named Nexosis-Webhook-Token. You will receive this \r\nsame header in the request message to your Webhook, which you can use to validate that the message came from Nexosis.")
    private String callbackUrl;
    /**
     * Metadata about each column in the data source
     */
    @JsonProperty("columns")
    @JsonPropertyDescription("Metadata about each column in the data source")
    private Columns columns;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1353259522746067880L;

    /**
     * Name of the dataset or view to use.
     */
    @JsonProperty("dataSourceName")
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * Name of the dataset or view to use.
     */
    @JsonProperty("dataSourceName")
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * Column in the specified data source
     */
    @JsonProperty("targetColumn")
    public String getTargetColumn() {
        return targetColumn;
    }

    /**
     * Column in the specified data source
     */
    @JsonProperty("targetColumn")
    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    /**
     *
     */
    @JsonProperty("predictionDomain")
    public PredictionDomain getPredictionDomain() {
        return predictionDomain;
    }

    /**
     *
     */
    @JsonProperty("predictionDomain")
    public void setPredictionDomain(PredictionDomain predictionDomain) {
        this.predictionDomain = predictionDomain;
    }

    /**
     * The Webhook url that will receive updates when the Session status changes
     * If you provide a callback url, your response will contain a header named Nexosis-Webhook-Token. You will receive this
     * same header in the request message to your Webhook, which you can use to validate that the message came from Nexosis.
     */
    @JsonProperty("callbackUrl")
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * The Webhook url that will receive updates when the Session status changes
     * If you provide a callback url, your response will contain a header named Nexosis-Webhook-Token. You will receive this
     * same header in the request message to your Webhook, which you can use to validate that the message came from Nexosis.
     */
    @JsonProperty("callbackUrl")
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /**
     * Metadata about each column in the data source
     */
    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    /**
     * Metadata about each column in the data source
     */
    @JsonProperty("columns")
    public void setColumns(Columns columns) {
        this.columns = columns;
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
    public String toString() {
        return new ToStringBuilder(this).append("dataSourceName", dataSourceName).append("targetColumn", targetColumn).append("predictionDomain", predictionDomain).append("callbackUrl", callbackUrl).append("columns", columns).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(predictionDomain).append(callbackUrl).append(additionalProperties).append(columns).append(targetColumn).append(dataSourceName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ModelSessionDetail) == false) {
            return false;
        }
        ModelSessionDetail rhs = ((ModelSessionDetail) other);
        return new EqualsBuilder().append(predictionDomain, rhs.predictionDomain).append(callbackUrl, rhs.callbackUrl).append(additionalProperties, rhs.additionalProperties).append(columns, rhs.columns).append(targetColumn, rhs.targetColumn).append(dataSourceName, rhs.dataSourceName).isEquals();
    }
}