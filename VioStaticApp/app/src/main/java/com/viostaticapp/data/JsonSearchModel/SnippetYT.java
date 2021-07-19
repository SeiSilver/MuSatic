package com.viostaticapp.data.JsonSearchModel;

public class SnippetYT {

    private String title;

    private String description;

    private String channelId;

    private String channelTitle;

    private ThumbnailYT thumbnails;

    private String publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ThumbnailYT getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbnailYT thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
