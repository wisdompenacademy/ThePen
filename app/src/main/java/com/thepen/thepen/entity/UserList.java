package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList {
    @JsonProperty("usercontent")
    public ArrayList<User> usercontents;
    public UserList() {
    }
}
