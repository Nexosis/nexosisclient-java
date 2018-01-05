package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "columns",
        "callbackUrl"
})
public abstract class SessionRequest
{
    @JsonProperty("dataSourceName")
    private String dataSourceName;
    @JsonProperty("targetColumn")
    private String targetColumn;
    @JsonProperty("columns")
    private Columns columns;
    @JsonProperty("callbackUrl")
    private String callbackUrl;

    /**
     *  The data source to use for the session
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * The column within the Data Source to be used as the target
     */
    public String getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    /**
     * Metadata about each column in the dataset, for purposes of the session
     *
     * This is initialized as a case-insensitive dictionary. The API ignores case for column names.
     */
    public Columns getColumns() {
        return columns;
    }

    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    /**
     * A url to receive a callback when the status of the Session changes
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Map<String, Object> toParameters() {
        Map<String,Object> parameters = new HashMap<>();

        if (dataSourceName != null) {
            parameters.put("dataSourceName",dataSourceName);
        }

        if (targetColumn != null) {
            parameters.put("targetColumn",targetColumn);
        }

        if (callbackUrl != null) {
            parameters.put("callbackUrl",callbackUrl);
        }

        return parameters;
    }
}
