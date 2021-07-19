package com.viostaticapp.data.model;

import org.apache.commons.text.StringEscapeUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YoutubeVideo implements Serializable {

    private String id;

    private String title;

    private String description;

    private String thumbnail;

    private String videoUrl;

    private Channel channel;

    private String publishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = dateFormat.parse(publishedAt);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);

            return dateStr;

        } catch (Exception e) {
            return publishedAt;
        }

    }

    public YoutubeVideo() {
    }

    public String getTitle() {

        return StringEscapeUtils.unescapeHtml4(title);
    }
}
