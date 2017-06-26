package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "selectedOn",
        "selectedBySessionId",
        "champion",
        "contestants"
})
// WinningAlgorithm
public class ChampionInfo implements Serializable
{

    @JsonProperty("selectedOn")
    private String selectedOn;
    @JsonProperty("selectedBySessionId")
    private String selectedBySessionId;
    @JsonProperty("champion")
    private Champion champion;
    @JsonProperty("contestants")
    private List<Contestant> contestants = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3281400779052479308L;

    @JsonProperty("selectedOn")
    public String getSelectedOn() {
        return selectedOn;
    }

    @JsonProperty("selectedOn")
    public void setSelectedOn(String selectedOn) {
        this.selectedOn = selectedOn;
    }

    @JsonProperty("selectedBySessionId")
    public String getSelectedBySessionId() {
        return selectedBySessionId;
    }

    @JsonProperty("selectedBySessionId")
    public void setSelectedBySessionId(String selectedBySessionId) {
        this.selectedBySessionId = selectedBySessionId;
    }

    @JsonProperty("champion")
    public Champion getChampion() {
        return champion;
    }

    @JsonProperty("champion")
    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    @JsonProperty("contestants")
    public List<Contestant> getContestants() {
        return contestants;
    }

    @JsonProperty("contestants")
    public void setContestants(List<Contestant> contestants) {
        this.contestants = contestants;
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
        return new HashCodeBuilder().append(selectedOn).append(selectedBySessionId).append(champion).append(contestants).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ChampionInfo) == false) {
            return false;
        }
        ChampionInfo rhs = ((ChampionInfo) other);
        return new EqualsBuilder().append(selectedOn, rhs.selectedOn).append(selectedBySessionId, rhs.selectedBySessionId).append(champion, rhs.champion).append(contestants, rhs.contestants).append(additionalProperties, rhs.additionalProperties).isEquals();
    }
}