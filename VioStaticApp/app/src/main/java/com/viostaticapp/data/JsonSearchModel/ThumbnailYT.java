package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.SerializedName;


public class ThumbnailYT {

    @SerializedName("high")
    private HighThumbnail highThumbnail;

    @SerializedName("medium")
    private MediumThumbnail mediumThumbnail;

    @SerializedName("default")
    private DefaultThumbnail defaultThumbnail;

    public HighThumbnail getHighThumbnail() {
        return highThumbnail;
    }

    public void setHighThumbnail(HighThumbnail highThumbnail) {
        this.highThumbnail = highThumbnail;
    }

    public MediumThumbnail getMediumThumbnail() {
        return mediumThumbnail;
    }

    public void setMediumThumbnail(MediumThumbnail mediumThumbnail) {
        this.mediumThumbnail = mediumThumbnail;
    }

    public DefaultThumbnail getDefaultThumbnail() {
        return defaultThumbnail;
    }

    public void setDefaultThumbnail(DefaultThumbnail defaultThumbnail) {
        this.defaultThumbnail = defaultThumbnail;
    }

    public class HighThumbnail {

        private String url;
        public long width;
        public long height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getWidth() {
            return width;
        }

        public void setWidth(long width) {
            this.width = width;
        }

        public long getHeight() {
            return height;
        }

        public void setHeight(long height) {
            this.height = height;
        }
    }


    public class DefaultThumbnail {

        private String url;
        public long width;
        public long height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getWidth() {
            return width;
        }

        public void setWidth(long width) {
            this.width = width;
        }

        public long getHeight() {
            return height;
        }

        public void setHeight(long height) {
            this.height = height;
        }
    }


    public class MediumThumbnail {

        private String url;
        public long width;
        public long height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getWidth() {
            return width;
        }

        public void setWidth(long width) {
            this.width = width;
        }

        public long getHeight() {
            return height;
        }

        public void setHeight(long height) {
            this.height = height;
        }
    }


}
