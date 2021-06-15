package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoID {

    @SerializedName("videoId")
    @Expose
    private String videoId;

    public VideoID(String videoId) {
        this.videoId = videoId;
    }

    public VideoID() {
    }

}
