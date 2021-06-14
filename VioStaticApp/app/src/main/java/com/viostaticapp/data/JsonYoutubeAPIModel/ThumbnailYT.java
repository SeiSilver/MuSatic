package com.viostaticapp.data.JsonYoutubeAPIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class ThumbnailYT {

    @SerializedName("high")
    @Expose
    private HighThumbnail thumbnailHigh;

    public ThumbnailYT(HighThumbnail thumbnailHigh) {
        this.thumbnailHigh = thumbnailHigh;
    }

    public ThumbnailYT() {
    }

    @Data
    public class HighThumbnail {

        @SerializedName("url")
        @Expose
        private String url;

        public HighThumbnail(String url) {
            this.url = url;
        }

        public HighThumbnail() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public HighThumbnail getThumbnailHigh() {
        return thumbnailHigh;
    }

    public void setThumbnailHigh(HighThumbnail thumbnailHigh) {
        this.thumbnailHigh = thumbnailHigh;
    }
}
