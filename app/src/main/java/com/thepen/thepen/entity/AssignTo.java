package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class AssignTo  {
    @JsonProperty("sendTouserName")
    public String sendTouserName;
    @JsonProperty("video_id")
    public String video_id;
}
