package com.example.seiaudioplayer.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.seiaudioplayer.R;
import com.example.seiaudioplayer.data.model.Music;
import com.example.seiaudioplayer.service.MusicService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private TextView song_name, artist_name, duration_played, duration_total;
    private ImageView covert_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    private FloatingActionButton playPauseBtn;
    private SeekBar seekBar;
    private int position = -1;
    private ArrayList<Music> listSongs = new ArrayList<>();
    private Uri uri;
    private Handler handler = new Handler();
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        initViewPage();
        runSelectedMusic();
        setButtonEvent();
    }

    private void initViewPage() {
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.durationPlayed);
        duration_total = findViewById(R.id.durationTotal);
        covert_art = findViewById(R.id.covert_art);
        nextBtn = findViewById(R.id.next_btn);
        prevBtn = findViewById(R.id.prev_btn);
        backBtn = findViewById(R.id.back_btn);
        shuffleBtn = findViewById(R.id.shuffle);
        repeatBtn = findViewById(R.id.repeat_btn);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);
    }

    private void runSelectedMusic() {
        Intent intent = this.getIntent();

        // get music location
        position = intent.getIntExtra("position", -1);
        listSongs = new MusicService().getAllMusic(this);
        if (listSongs != null) {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }

        // play music
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

//        mediaPlayer = MediaPlayer.create(this, uri);

        try {
            mediaPlayer.setDataSource("https://www.youtube.com/watch?v=8XbwRJGdQqo");
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {

        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // update the seekbar and total played using thread
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formatTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

        setDisplayMusicData();
    }

    private void setDisplayMusicData() {
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        playPauseBtn.setImageResource(R.drawable.ic_pause);

        seekBar.setMax(mediaPlayer.getDuration() / 1000);

        // set Image
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.valueOf(listSongs.get(position).getDuration()) / 1000;
        duration_total.setText(formatTime(durationTotal));
        byte[] image = retriever.getEmbeddedPicture();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aqua_neko);

        if (image != null) {
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        }

        imageAnimation(this, covert_art, bitmap);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextBtn.callOnClick();
            }
        });

    }

    private void setButtonEvent() {

        // playPauseBtn
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    playPauseBtn.setImageResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                } else {
                    playPauseBtn.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        // nextBtn
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position + 1) % listSongs.size();
                uri = Uri.parse(listSongs.get(position).getPath());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                setDisplayMusicData();
                mediaPlayer.start();
            }
        });

        // prevBtn
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position - 1) < 0 ? listSongs.size() - 1 : position - 1;
                uri = Uri.parse(listSongs.get(position).getPath());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                setDisplayMusicData();
                mediaPlayer.start();
            }
        });
    }

    private void imageAnimation(Context context, ImageView imageView, Bitmap bitmap) {

        Animation animFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);

        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(context).load(bitmap).into(imageView);
                imageView.startAnimation(animFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        imageView.startAnimation(animFadeOut);

    }

    private String formatTime(int mCurrentPosition) {
        String totalout = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalNew = minutes + ":0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        }
        return totalout;
    }

}
