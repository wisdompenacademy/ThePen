package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User{
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("address")
    public String address;
    @JsonProperty("exptSubject")
    public String exptSubject;
    @JsonProperty("dob")
    public String dob;
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("phoneNo")
    public String phoneNo;
    @JsonProperty("student_std")
    public String student_std;
    @JsonProperty("roleId")
    public String roleId;
    public User(){}
}
