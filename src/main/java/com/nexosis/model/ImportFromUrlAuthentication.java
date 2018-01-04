package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Url Authentication parameters
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "basic"
})
public class ImportFromUrlAuthentication
{
    @JsonProperty("basic")
    private BasicAuthentication basic;

    /**
     * Basic Authentication
     */
    public BasicAuthentication getBasic() {
        return basic;
    }

    public void setBasic(BasicAuthentication basic) {
        this.basic = basic;
    }
}