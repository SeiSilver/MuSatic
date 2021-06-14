package com.viostaticapp.data.JsonYoutubeAPIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnippetYT {

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("channelId")
    @Expose
    private String channelId;

    @SerializedName("channelTitle")
    @Expose
    private String channelTitle;

    @SerializedName("thumbnails")
    @Expose
    private ThumbnailYT thumbnail;

    public SnippetYT() {
    }

    public SnippetYT(String publishedAt, String title, String channelId, String channelTitle, ThumbnailYT thumbnail) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.thumbnail = thumbnail;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public ThumbnailYT getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailYT thumbnail) {
        this.thumbnail = thumbnail;
    }
}
