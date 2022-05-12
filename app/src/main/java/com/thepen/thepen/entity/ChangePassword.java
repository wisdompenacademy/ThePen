package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ChangePassword  {
    @JsonProperty("oldPassword")
    String oldPassword;
    @JsonProperty("newPassword")
    String newPassword;
    @JsonProperty("userName")
    String userName;

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
