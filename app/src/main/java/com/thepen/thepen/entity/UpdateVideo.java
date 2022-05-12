package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateVideo {
    @JsonProperty("video_id")
    public String video_id;
    @JsonProperty("publish")
    public boolean publish;
    @JsonProperty("charge")
    public boolean charge;
    @JsonProperty("status")
    public String status;
}
