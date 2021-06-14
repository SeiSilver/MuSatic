package com.viostaticapp.service;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.data.JsonYoutubeAPIModel.ItemYT;
import com.viostaticapp.data.JsonYoutubeAPIModel.JsonYoutubeAPIModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPISearchImp implements YoutubeAPISearch {

    private final String APIKEY = "AIzaSyDCsqdJIEIZUs9xugVEfamZ-ebWeiewpBI";
    private final String BASE_URL = "https://youtube.googleapis.com/youtube/v3/";
    private final String schema = "search?";

    private String part = "part=snippet";
    private String maxResults = "&maxResults=10";
    private String search = "&q=";
    private String orderBy = "&order=date";
    private String key = "&key=" + APIKEY;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private YoutubeAPICall youtubeAPICall = null;

    public YoutubeAPICall getYoutubeAPICall() {
        if (youtubeAPICall == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            youtubeAPICall = retrofit.create(YoutubeAPICall.class);
        }
        return youtubeAPICall;

    }

    @Override
    public boolean search(String query) {

        ArrayList<ItemYT> modelList = new ArrayList<>();

        String search = "&q=" + query;

        String url_call = BASE_URL + schema + part + maxResults + search + orderBy + key;

        Call<JsonYoutubeAPIModel> data = getYoutubeAPICall().getYT(url_call);

        data.enqueue(new Callback<JsonYoutubeAPIModel>() {
            @Override
            public void onResponse(Call<JsonYoutubeAPIModel> call, Response<JsonYoutubeAPIModel> response) {
                if (response.errorBody() != null) {
                    Log.e("error", "onResponse" + response.errorBody());
                } else {
                    JsonYoutubeAPIModel model = response.body();
                    modelList.addAll(model.getItems());
                    saveToDatabase(modelList);
                }
            }

            @Override
            public void onFailure(Call<JsonYoutubeAPIModel> call, Throwable t) {
                Log.e("error", "onFailure" + t);
            }
        });

        return false;
    }

    private ArrayList<YoutubeVideo> saveToDatabase(ArrayList<ItemYT> itemYTList) {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();

        for (ItemYT i : itemYTList) {
            YoutubeVideo video = new YoutubeVideo();
            video.setId(i.getId().getVideoId());
            video.setTitle(i.getSnippet().getTitle());
            video.setThumbnail(i.getSnippet().getThumbnail().getThumbnailHigh().getUrl());
            Channel c = new Channel();
            c.setChannelId(i.getSnippet().getChannelId());
            c.setChannelId(i.getSnippet().getChannelTitle());
            video.setChannel(c);
            videos.add(video);
        }

        //Save to Database

        for (YoutubeVideo i : videos) {
            database.collection(EnumInit.Table.YoutubeVideo.name).document(i.getId()).set(i);
        }

        return videos;
    }

    interface YoutubeAPICall {
        @GET
        Call<JsonYoutubeAPIModel> getYT(@Url String url);
    }

}

