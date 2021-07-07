package com.viostaticapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.JsonSearchModel.ItemYT;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.view.SearchFragment;

public class SaveDataService extends Service implements Runnable {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public SaveDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("saveDatabase", "run save service");
        Log.e("saveDatabase", "saveProces");

        for (YoutubeVideo video: SearchFragment.videoList) {

            database.collection("TEST").document(video.getId()).set(video);
            Log.e("saveDatabase", "saved: " + video.getTitle());

        }
        Log.e("saveDatabase", "service Stop");

        stopSelf();

        return START_STICKY;
    }

    @Override
    public void run() {

    }
}