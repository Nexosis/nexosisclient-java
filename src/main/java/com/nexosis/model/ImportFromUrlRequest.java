package com.nexosis.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "url",
        "auth",
})
public class ImportFromUrlRequest extends ImportRequest  {
    @JsonProperty("url")
    private String url;
    @JsonProperty("auth")
    private ImportFromUrlAuthentication auth;

    /**
     * The URL to retrieve
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Authentication parameters to be used to retrieve the data at the given url
     */
    public ImportFromUrlAuthentication getAuth() {
        return auth;
    }

    public void setAuth(ImportFromUrlAuthentication auth) {
        this.auth = auth;
    }
}
