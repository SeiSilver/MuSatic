package com.viostaticapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    FloatingActionButton showPlayerOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        player_video_title = findViewById(R.id.player_video_title);
        player_video_description = findViewById(R.id.player_video_description);
        player_video_publish_date = findViewById(R.id.player_video_publish_date);
        player_back_btn = findViewById(R.id.player_back_btn);
        showPlayerOption = findViewById(R.id.showPlayerOption);

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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

    }


    // onclick
    @SuppressLint("RestrictedApi")
    public void showPlayerOptionMenu(View v) {

        MenuBuilder menuBuilder = new MenuBuilder(this);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.player_options_menu, menuBuilder);

        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addPlaylist:
                        Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.deletePlaylist:
                        Toast.makeText(getApplicationContext(), "remove", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.openYoutube:
                        Toast.makeText(getApplicationContext(), "go to youtube", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(@NonNull MenuBuilder menu) {

            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(this, menuBuilder, v);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

}