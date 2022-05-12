package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoList {
    @JsonProperty("subject")
    String subject;
    @JsonProperty("std")
    String std;
    @JsonProperty("educationBoard")
    String educationBoard;
    @JsonProperty("medium")
    String medium;
    @JsonProperty("roleId")
    String roleId;
    @JsonProperty("userName")
    String userName;

    @JsonProperty("UploadVideoList")
    ArrayList<Video> UploadVideoList;

    public VideoList() {
    }

    public VideoList(String subject, String std, String educationBoard, String medium, ArrayList<Video> uploadVideoList) {
        this.subject = subject;
        this.std = std;
        this.educationBoard = educationBoard;
        this.medium = medium;
        UploadVideoList = uploadVideoList;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public void setEducationBoard(String educationBoard) {
        this.educationBoard = educationBoard;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public ArrayList<Video> getUploadVideoList() {
        return UploadVideoList;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
