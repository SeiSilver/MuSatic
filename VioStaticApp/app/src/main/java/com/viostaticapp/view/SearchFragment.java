package com.viostaticapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.present.homePresent.HomeRecommendAdapter;
import com.viostaticapp.present.searchPresent.SearchAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    SearchAdapter searchAdapter;
    RecyclerView search_page_recycler;
    static ArrayList<YoutubeVideo> videoList = new ArrayList<>();
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSearchAdapter(view);
        setSearchView(view);
    }

    public void setSearchAdapter(View view) {

        search_page_recycler = view.findViewById(R.id.search_page_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        search_page_recycler.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(getContext(), videoList);
        search_page_recycler.setAdapter(searchAdapter);

        database.collection(EnumInit.Table.YoutubeVideo.name)
                .orderBy("title")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            YoutubeVideo video = new YoutubeVideo();

                            video.setId(doc.getString("id"));
                            video.setTitle(doc.getString("title"));
                            video.setVideoUrl(doc.getString("videoUrl"));
                            video.setPublishedAt(doc.getString("publishedAt"));
                            video.setChannel(doc.get("channel", Channel.class));
                            video.setThumbnail(doc.getString("thumbnail"));
                            video.setDescription(doc.getString("description"));

                            videoList.add(video);
                        }
                        searchAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

    public void setSearchView(View view) {
        searchView = view.findViewById(R.id.custom_search_view);
        searchView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setIconified(false);
                    }
                }
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                database.collection(EnumInit.Table.YoutubeVideo.name)
                        .whereGreaterThanOrEqualTo("title", query)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                videoList.clear();
                                for (DocumentSnapshot doc : task.getResult()) {
                                    YoutubeVideo video = new YoutubeVideo();

                                    video.setId(doc.getString("id"));
                                    video.setTitle(doc.getString("title"));
                                    video.setVideoUrl(doc.getString("videoUrl"));
                                    video.setPublishedAt(doc.getString("publishedAt"));
                                    video.setChannel(doc.get("channel", Channel.class));
                                    video.setThumbnail(doc.getString("thumbnail"));
                                    videoList.add(video);
                                }
                                searchAdapter.notifyDataSetChanged();
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
                return false;
            }
        });

    }
}