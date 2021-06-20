package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnippetYT {

    private String title;

    private String description;

    private String channelId;

    private String channelTitle;

    private ThumbnailYT thumbnails;

    private String publishedAt;

}
