package com.example.seiaudioplayer.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.seiaudioplayer.data.model.Music;

import java.util.ArrayList;

public class MusicService implements MusicServiceInf {

    private ArrayList<Music> musicFiles = new ArrayList<>();

    @Override
    public ArrayList<Music> getAllMusic(Context context) {

        ArrayList<Music> musics = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Log.e("E/Test", uri.toString());

        String[] projection = new String[]{
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);

                Music music = new Music(path, title, artist, album, duration);

                Log.e("E/Test Path: " + path, "Album: " + title);
                musics.add(music);
            }
            cursor.close();
        }

        return musics;

    }

}
