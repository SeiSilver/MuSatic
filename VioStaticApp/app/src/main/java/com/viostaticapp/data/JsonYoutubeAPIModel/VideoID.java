package com.viostaticapp.data.JsonYoutubeAPIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VideoID {

    @SerializedName("videoId")
    @Expose
    private String videoId;

    public VideoID(String videoId) {
        this.videoId = videoId;
    }

    public VideoID() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
