package com.nexosis.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModelRemoveCriteria {

    private UUID modelId;
    private String dataSourceName;
    private DateTime createdAfterDate;
    private DateTime createdBeforeDate;

    /**
     * The Id of the model to be removed
     */
    public UUID getModelId() {
        return modelId;
    }

    public void setModelId(UUID modelId) {
        this.modelId = modelId;
    }

    /**
     * All models built from this DataSource should be removed
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * Models created after this date should be removed
     */
    public DateTime getCreatedAfterDate() {
        return createdAfterDate;
    }

    public void setCreatedAfterDate(DateTime createdAfterDate) {
        this.createdAfterDate = createdAfterDate;
    }

    /**
     * Models created before this date should be removed
     */
    public DateTime getCreatedBeforeDate() {
        return createdBeforeDate;
    }

    public void setCreatedBeforeDate(DateTime createdBeforeDate) {
        this.createdBeforeDate = createdBeforeDate;
    }

    public Map<String,Object> toParameters() {

        Map<String,Object> parameters = new HashMap<>();
        if (createdAfterDate!=null) {
            parameters.put("createdAfterDate", createdAfterDate.toDateTimeISO().toString());
        }
        if (createdBeforeDate !=null) {
            parameters.put("createdBeforeDate", createdBeforeDate.toDateTimeISO().toString());
        }

        if (!StringUtils.isEmpty(dataSourceName)) {
            parameters.put("dataSourceName", dataSourceName);
        }

        return parameters;
    }
}
