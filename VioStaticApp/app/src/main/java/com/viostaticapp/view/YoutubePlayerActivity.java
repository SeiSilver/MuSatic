package com.viostaticapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

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

    TextView player_video_title, player_video_description, player_video_publish_date;
    ImageView player_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        player_video_title = findViewById(R.id.player_video_title);
        player_video_description = findViewById(R.id.player_video_description);
        player_video_publish_date = findViewById(R.id.player_video_publish_date);
        player_back_btn = findViewById(R.id.player_back_btn);

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

        setComponentView(youtubeVideo);
    }

    private void setComponentView(YoutubeVideo video) {

        player_video_title.setText(video.getTitle());
        player_video_description.setText(video.getDescription());
        player_video_publish_date.setText(video.getPublishedAt());

        player_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}