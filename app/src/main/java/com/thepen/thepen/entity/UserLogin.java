package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)

public class UserLogin {
    @JsonProperty("username")
    public String userName;
    @JsonProperty("password")
    public String password;
    @JsonProperty("grant_type")
    public String grant_type;
    @JsonProperty("access_token")
    public String access_token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getAccess_token() {
        return access_token;
    }
}
