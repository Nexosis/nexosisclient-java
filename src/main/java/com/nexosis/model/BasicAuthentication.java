package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "password",
})
public class BasicAuthentication
{
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("password")
    private String password;

    /**
     * If the url is secured by basic authentication, use this username to authenticate
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * If the url is secured by basic authentication, use this password to authenticate
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}