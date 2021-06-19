package com.viostaticapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.viostaticapp.R;
import com.viostaticapp._config.YoutubeAPIConfig;
import com.viostaticapp.data.model.YoutubeVideo;

public class YoutubePlayerActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        Intent intent = getIntent();

        runVideo(intent);
    }

    private void runVideo(Intent intent) {
        YoutubeVideo youtubeVideo = (YoutubeVideo) intent.getSerializableExtra("youtubeVideo");

        String videoId = youtubeVideo.getId();

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


        youTubePlayerView.initialize(YoutubeAPIConfig.KEY, onInitializedListener);


    }

}