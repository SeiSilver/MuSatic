package com.viostaticapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.present.homePresent.HomeLatestAdapter;
import com.viostaticapp.present.homePresent.HomeRecommendAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView homeLatestRecycler;
    RecyclerView homeRecommendRecycler;

    HomeLatestAdapter homeLatestAdapter;
    HomeRecommendAdapter homeRecommendAdapter;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    static List<YoutubeVideo> latestVideoList = new ArrayList<>();
    static List<YoutubeVideo> recommendVideoList = new ArrayList<>();

    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Notification");
        dialog.setMessage("Getting data");
        dialog.show();
        setHomeLatestRecycler();
        setHomeRecommendRecycler();
    }

    private void setHomeLatestRecycler() {

        homeLatestRecycler = this.getView().findViewById(R.id.latest_row_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        homeLatestRecycler.setLayoutManager(layoutManager);
        homeLatestAdapter = new HomeLatestAdapter(getContext(), latestVideoList);
        homeLatestRecycler.setAdapter(homeLatestAdapter);

        database.collection(EnumInit.Table.YoutubeVideo.name).orderBy("publishedAt", Query.Direction.DESCENDING)
                .limit(10)
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

                            latestVideoList.add(video);
                        }
                        homeLatestAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void setHomeRecommendRecycler() {

        homeRecommendRecycler = this.getView().findViewById(R.id.recommend_row_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        homeRecommendRecycler.setLayoutManager(layoutManager);
        homeRecommendAdapter = new HomeRecommendAdapter(getContext(), recommendVideoList);
        homeRecommendRecycler.setAdapter(homeRecommendAdapter);

        database.collection(EnumInit.Table.YoutubeVideo.name)
                .orderBy("title")
                .limitToLast(10)
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

                            recommendVideoList.add(video);
                        }
                        homeRecommendAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}