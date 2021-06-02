package com.example.seiaudioplayer.data.model;

import lombok.Data;

@Data
public class Music {

    private String path;

    private String title;

    private String artist;

    private String album;

    private String duration;

    public Music(String path, String title, String artist, String album, String duration) {
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }


}
