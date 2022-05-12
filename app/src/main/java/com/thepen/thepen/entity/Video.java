package com.thepen.thepen.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video implements Parcelable, Serializable {
        @JsonProperty("video_id")
        public String video_id;
        @JsonProperty("subject")
        public String subject;
        @JsonProperty("std")
        public String std;
        @JsonProperty("educationBoard")
        public String educationBoard;
        @JsonProperty("medium")
        public String medium;
        @JsonProperty("videoPath")
        public String videoPath;
        @JsonProperty("charge")
        public boolean charge;
        @JsonProperty("videoName")
        public String videoName;
        @JsonProperty("imagePath")
        public String imagePath;
        @JsonProperty("userName")
        public String userName;
        @JsonProperty("description")
        public String description;
        @JsonProperty("approve")
        public boolean approve;
        @JsonProperty("publish")
        public boolean publish;
        @JsonProperty("approved")
        public boolean approved;
        public Video(){}

        protected Video(Parcel in) {
                video_id = in.readString();
                subject = in.readString();
                std = in.readString();
                educationBoard = in.readString();
                medium = in.readString();
                videoPath = in.readString();
                charge = in.readByte() != 0;
                videoName = in.readString();
                imagePath = in.readString();
                userName = in.readString();
                description = in.readString();
        }

        public static final Creator<Video> CREATOR = new Creator<Video>() {
                @Override
                public Video createFromParcel(Parcel in) {
                        return new Video(in);
                }

                @Override
                public Video[] newArray(int size) {
                        return new Video[size];
                }
        };

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(video_id);
                parcel.writeString(subject);
                parcel.writeString(std);
                parcel.writeString(educationBoard);
                parcel.writeString(medium);
                parcel.writeString(videoPath);
                parcel.writeByte((byte) (charge ? 1 : 0));
                parcel.writeString(videoName);
                parcel.writeString(imagePath);
                parcel.writeString(userName);
                parcel.writeString(description);
        }
}
