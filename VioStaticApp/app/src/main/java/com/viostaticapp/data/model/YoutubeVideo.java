package com.viostaticapp.data.model;

import lombok.Data;

@Data
public class YoutubeVideo {

    private String id;

    private String title;

    private String thumbnail;

    private String videoUrl;

    private Channel channel;

    public YoutubeVideo() {
    }

}
