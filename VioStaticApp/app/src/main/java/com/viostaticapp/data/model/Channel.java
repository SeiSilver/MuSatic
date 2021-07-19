package com.viostaticapp.data.model;

import java.io.Serializable;

public class Channel implements Serializable {

    private String channelId;

    private String channelTitle;

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
}
