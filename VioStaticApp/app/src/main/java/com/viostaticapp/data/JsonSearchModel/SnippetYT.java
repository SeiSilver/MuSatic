package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
