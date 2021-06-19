package com.viostaticapp.data.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Channel implements Serializable {

    private String channelId;

    private String channelTitle;

}
