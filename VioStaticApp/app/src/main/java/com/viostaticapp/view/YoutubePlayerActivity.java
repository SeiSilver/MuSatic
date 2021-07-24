package com.viostaticapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;
import com.viostaticapp._config.YoutubeAPIConfig;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.service.LibraryItemMenuOption;

import java.util.HashMap;
import java.util.Map;

public class YoutubePlayerActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    TextView player_video_title, player_video_description, player_video_publish_date;
    ImageView player_back_btn;

    FloatingActionButton showPlayerOption;

    public YoutubeVideo youtubeVideo;

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
        youtubeVideo = (YoutubeVideo) intent.getSerializableExtra("youtubeVideo");

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
    public void showPlayerOptionMenu(View v) {
        LibraryItemMenuOption.showPopupMenu(v, youtubeVideo, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}