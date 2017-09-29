package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "date",
        "status"
})
// Name in JSON is StatusHistory
public class SessionStatusHistory implements Serializable
{

    @JsonProperty("date")
    private DateTime date;
    @JsonProperty("status")
    private SessionStatus status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6155431656643651347L;

    @JsonProperty("date")
    public DateTime getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(DateTime date) {
        this.date = date;
    }

    @JsonProperty("status")
    public SessionStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(SessionStatus status) {
        this.status = status;
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
        return new HashCodeBuilder().append(date).append(status).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SessionStatusHistory) == false) {
            return false;
        }
        SessionStatusHistory rhs = ((SessionStatusHistory) other);
        return new EqualsBuilder().append(date, rhs.date).append(status, rhs.status).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}