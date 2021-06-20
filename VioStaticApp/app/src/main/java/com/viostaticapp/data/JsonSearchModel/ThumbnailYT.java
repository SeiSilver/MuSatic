package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbnailYT {

    @SerializedName("high")
    private HighThumbnail highThumbnail;

    @SerializedName("medium")
    private MediumThumbnail mediumThumbnail;

    @SerializedName("default")
    private DefaultThumbnail defaultThumbnail;

    @Getter
    @Setter
    public class HighThumbnail {

        private String url;
        public long width;
        public long height;

    }

    @Getter
    @Setter
    public class DefaultThumbnail {

        private String url;
        public long width;
        public long height;

    }

    @Getter
    @Setter
    public class MediumThumbnail {

        private String url;
        public long width;
        public long height;

    }


}
