package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {
    @JsonProperty("usercontent")
    public UserContent usercontent;

    protected UserProfile() {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class UserContent{
        @JsonProperty("firstName")
        public String firstName;
        @JsonProperty("lastName")
        public String lastName;
        @JsonProperty("phoneNo")
        public String phoneNo;
        @JsonProperty("address")
        public String address;
        @JsonProperty("dob")
        public String dob;
        @JsonProperty("student_std")
        public String student_std;
        @JsonProperty("city")
        public String city;
        @JsonProperty("state")
        public String state;
        @JsonProperty("country")
        public String country;
        @JsonProperty("zipcode")
        public String zipcode;
    }
}
