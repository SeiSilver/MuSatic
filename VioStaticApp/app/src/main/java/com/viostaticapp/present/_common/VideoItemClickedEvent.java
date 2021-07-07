package com.viostaticapp.present._common;

import com.viostaticapp.data.model.YoutubeVideo;

public interface VideoItemClickedEvent {

    void onClicked(YoutubeVideo video);

}
