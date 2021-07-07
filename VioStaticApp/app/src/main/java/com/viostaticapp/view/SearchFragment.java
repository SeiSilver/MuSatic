package com.viostaticapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.JsonSearchModel.ItemYT;
import com.viostaticapp.data.JsonSearchModel.JsonSearchAPIModel;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.present._common.VideoItemClickedEvent;
import com.viostaticapp.present.searchPresent.SearchAdapter;
import com.viostaticapp.service.SaveDataService;
import com.viostaticapp.service.YoutubeAPISearch;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements VideoItemClickedEvent {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    SearchAdapter searchAdapter;
    RecyclerView search_page_recycler;
    public static ArrayList<YoutubeVideo> videoList = new ArrayList<>();
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
        searchAdapter = new SearchAdapter(getContext(), videoList, this::onClicked);
        search_page_recycler.setAdapter(searchAdapter);

        database.collection(EnumInit.Collections.YoutubeVideo.name)
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
                searchProcess(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void searchProcess(String query) {

        ArrayList<ItemYT> modelList = new ArrayList<>();

        String url_call = YoutubeAPISearch.BASE_URL + YoutubeAPISearch.schema +
                YoutubeAPISearch.part + YoutubeAPISearch.maxResults +
                YoutubeAPISearch.searchSchema + query +
                YoutubeAPISearch.type +
                YoutubeAPISearch.orderBy + YoutubeAPISearch.key;

        YoutubeAPISearch.IOnYoutubeAPI youtubeAPI = YoutubeAPISearch.getYoutubeAPICall();
        Call<JsonSearchAPIModel> data = youtubeAPI.getYoutubeResult(url_call);

        data.enqueue(new Callback<JsonSearchAPIModel>() {
            @Override
            public void onResponse(Call<JsonSearchAPIModel> call, Response<JsonSearchAPIModel> response) {

                if (response.errorBody() != null) {
                    Log.e("error", "onFailure" + response.errorBody());
                } else {
                    JsonSearchAPIModel apiResponse = response.body();
                    modelList.addAll(apiResponse.getItems());

                    ArrayList<YoutubeVideo> videos = new ArrayList<>();

                    videoList.clear();

                    for (ItemYT i : modelList) {

                        YoutubeVideo video = new YoutubeVideo();
                        video.setId(i.getId().getVideoId());

                        String titleFormat = StringEscapeUtils.unescapeHtml4(i.getSnippet().getTitle());
                        video.setTitle(titleFormat);

                        video.setThumbnail(i.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());
                        Channel c = new Channel();
                        c.setChannelId(i.getSnippet().getChannelId());

                        String channelTitleFormat = StringEscapeUtils.unescapeHtml4(i.getSnippet().getChannelTitle());
                        c.setChannelTitle(channelTitleFormat);
                        video.setChannel(c);

                        video.setPublishedAt(i.getSnippet().getPublishedAt());
                        video.setVideoUrl("https://www.youtube.com/watch?v=" + i.getId().getVideoId());
                        video.setDescription(i.getSnippet().getDescription());
                        videos.add(video);

                    }

                    videoList.addAll(videos);
                    searchAdapter.notifyDataSetChanged();

                    Intent intent = new Intent(getContext(), SaveDataService.class);
                    getContext().startService(intent);

                }
            }

            @Override
            public void onFailure(Call<JsonSearchAPIModel> call, Throwable t) {
                Log.e("error", "onFailure" + t);

                database.collection(EnumInit.Collections.YoutubeVideo.name)
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

            }
        });
    }

    @Override
    public void onClicked(YoutubeVideo video) {
        Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
        intent.putExtra("youtubeVideo", video);
        getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}