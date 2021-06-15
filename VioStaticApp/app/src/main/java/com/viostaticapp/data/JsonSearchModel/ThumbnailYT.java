package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbnailYT {

    @SerializedName("medium")
    @Expose
    private HighThumbnail thumbnailHigh;

    public ThumbnailYT(HighThumbnail thumbnailHigh) {
        this.thumbnailHigh = thumbnailHigh;
    }

    public ThumbnailYT() {
    }

    @Getter
    @Setter
    public class HighThumbnail {

        @SerializedName("url")
        @Expose
        private String url;

        public HighThumbnail(String url) {
            this.url = url;
        }

        public HighThumbnail() {
        }

    }


}
