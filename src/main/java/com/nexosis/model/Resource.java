package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Resource {
    @JsonProperty("links")
    private List<Link> links = null;

    /**
     * @return Links to related resources
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * @param links Links to related resources
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
