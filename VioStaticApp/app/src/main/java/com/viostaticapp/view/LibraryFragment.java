package com.viostaticapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.JsonSearchModel.ItemYT;
import com.viostaticapp.data.JsonSearchModel.JsonSearchAPIModel;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.present._common.VideoItemClickedEvent;
import com.viostaticapp.present.libraryPresent.LibraryAdapter;
import com.viostaticapp.service.SaveDataService;
import com.viostaticapp.service.YoutubeAPISearch;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment implements VideoItemClickedEvent, LibraryAdapter.CallReloadLibrary {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser user;

    ArrayList<YoutubeVideo> videoList = new ArrayList<>();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    LibraryAdapter libraryAdapter;
    RecyclerView library_recycler_view;
    SearchView library_search_view;
    TextView warning_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        library_recycler_view = view.findViewById(R.id.library_recycler_view);
        library_search_view = view.findViewById(R.id.library_search_view);
        warning_text = view.findViewById(R.id.warning_text);

        setLibraryAdapter();
        setSearchView(view);
    }

    private void setLibraryAdapter() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);

        library_recycler_view.setLayoutManager(layoutManager);
        libraryAdapter = new LibraryAdapter(getContext(), videoList, this::onClicked, this::reloadLibrary);
        library_recycler_view.setAdapter(libraryAdapter);

        if (user != null) {
            warning_text.setVisibility(View.GONE);
            library_recycler_view.setVisibility(View.VISIBLE);
            reloadData();

        } else {
            warning_text.setVisibility(View.VISIBLE);
            library_recycler_view.setVisibility(View.GONE);
        }

    }


    private void reloadData() {

        if (user != null || user.getEmail() != null) {
            database.collection(EnumInit.Collections.Library.name).document(user.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            videoList.clear();

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {

                                    Map<String, Object> map = document.getData();
                                    if (map != null) {
                                        for (Map.Entry<String, Object> entry : map.entrySet()) {

                                            HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();
                                            YoutubeVideo video = new YoutubeVideo();
                                            video.setId((String) temp.get("id"));
                                            video.setTitle((String) temp.get("title"));
                                            video.setVideoUrl((String) temp.get("videoUrl"));
                                            video.setPublishedAt((String) temp.get("publishedAt"));
                                            video.setThumbnail((String) temp.get("thumbnail"));
                                            video.setDescription((String) temp.get("description"));

                                            videoList.add(video);
                                        }
                                    }
                                    libraryAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

    }

    @Override
    public void onClicked(YoutubeVideo video) {

        Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
        intent.putExtra("youtubeVideo", video);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    @SuppressLint("RestrictedApi")
    public void reloadLibrary(YoutubeVideo video, View v) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        MenuBuilder menuBuilder = new MenuBuilder(getContext());
        MenuInflater inflater = new MenuInflater(getContext());
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
                                            Toast.makeText(getContext(), "Added to library successfully",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }

                        reloadData();
                        return true;
                    case R.id.deletePlaylist:

                        if (user != null) {
                            Map<String, Object> data = new HashMap<>();
                            data.put(video.getId(), FieldValue.delete());

                            db.collection(EnumInit.Collections.Library.name).document(user.getEmail()).update(data)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getContext(), "Removed from library successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                        reloadData();
                        return true;
                    case R.id.openYoutube:

                        Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideoUrl()));
                        Intent chooser = Intent.createChooser(yt_play, "Open With");

                        if (yt_play.resolveActivity(getContext().getPackageManager()) != null) {
                            getContext().startActivity(chooser);
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

        MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), menuBuilder, v);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    public void setSearchView(View view) {
        library_search_view = view.findViewById(R.id.library_search_view);
        library_search_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        library_search_view.setIconified(false);
                    }
                }
        );

        library_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                database.collection(EnumInit.Collections.Library.name).document(user.getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                videoList.clear();

                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        Map<String, Object> map = document.getData();
                                        if (map != null) {
                                            for (Map.Entry<String, Object> entry : map.entrySet()) {

                                                HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();
                                                YoutubeVideo video = new YoutubeVideo();

                                                if (!((String) temp.get("title")).toLowerCase().contains(query.toLowerCase())) {
                                                    continue;
                                                }

                                                video.setId((String) temp.get("id"));
                                                video.setTitle((String) temp.get("title"));
                                                video.setVideoUrl((String) temp.get("videoUrl"));
                                                video.setPublishedAt((String) temp.get("publishedAt"));
                                                video.setThumbnail((String) temp.get("thumbnail"));
                                                video.setDescription((String) temp.get("description"));

                                                videoList.add(video);
                                            }
                                        }
                                        libraryAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText == null || newText.isEmpty()) {
                    reloadData();
                }

                return false;
            }
        });

    }


}