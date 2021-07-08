package com.viostaticapp.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.view.YoutubePlayerActivity;

import java.util.HashMap;
import java.util.Map;

public class LibraryItemMenuOption {

    public static boolean isChanged = false;

    // onclick
    @SuppressLint("RestrictedApi")
    public static void showPopupMenu(View v, YoutubeVideo video, Context context) {
        isChanged = false;

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser  user = firebaseAuth.getCurrentUser();

        MenuBuilder menuBuilder = new MenuBuilder(context);
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.player_options_menu, menuBuilder);

        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addPlaylist:

                        if (user != null) {

                            Map<String, Object> data = new HashMap<>();
                            data.put(video.getId(), video);

                            db.collection(EnumInit.Collections.Library.name).document(user.getEmail()).update(data)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Added to library successfully",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

//                                            createAlert("Error", "Registered successfully but couldn't save details!", "OK");

                                        }
                                    });

                        } else {
                            Toast.makeText(context, "Please Login to use this!", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    case R.id.deletePlaylist:

                        if (user != null) {

                            Map<String, Object> data = new HashMap<>();
                            data.put(video.getId(), FieldValue.delete());

                            db.collection(EnumInit.Collections.Library.name).document(user.getEmail()).update(data)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Removed from library successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                        } else {
                            Toast.makeText(context, "Please Login to use this!", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    case R.id.openYoutube:

                        Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideoUrl()));
                        Intent chooser = Intent.createChooser(yt_play, "Open With");

                        if (yt_play.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(chooser);
                        }
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(@NonNull MenuBuilder menu) {

            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(context, menuBuilder, v);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();

    }


}
