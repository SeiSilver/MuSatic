package com.viostaticapp.data.model;

import com.google.firebase.Timestamp;

import org.apache.commons.text.StringEscapeUtils;

import java.io.Serializable;

import lombok.Data;

@Data
public class YoutubeVideo implements Serializable {

    private String id;

    private String title;

    private String description;

    private String thumbnail;

    private String videoUrl;

    private Channel channel;

    private String publishedAt;

    public YoutubeVideo() {
    }

    public String getTitle() {

        return StringEscapeUtils.unescapeHtml4(title);
    }
}
