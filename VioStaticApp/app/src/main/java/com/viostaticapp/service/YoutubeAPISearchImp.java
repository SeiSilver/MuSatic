package com.viostaticapp.service;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.data.JsonSearchModel.ItemYT;
import com.viostaticapp.data.JsonSearchModel.JsonSearchModel;

import org.apache.commons.text.StringEscapeUtils;

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
    private String maxResults = "&maxResults=50";
    private String searchSchema = "&q=";
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

    private ArrayList<YoutubeVideo> videoList = new ArrayList<>();

    @Override
    public void search(String query) {

        ArrayList<ItemYT> modelList = new ArrayList<>();

        String url_call = BASE_URL + schema + part + maxResults + searchSchema + query + orderBy + key;

        Call<JsonSearchModel> data = getYoutubeAPICall().getYT(url_call);

        data.enqueue(new Callback<JsonSearchModel>() {
            @Override
            public void onResponse(Call<JsonSearchModel> call, Response<JsonSearchModel> response) {

                if (response.errorBody() != null) {
                    Log.e("error", "onFailure" + response.errorBody());
                } else {
                    JsonSearchModel apiResponse = response.body();
                    modelList.addAll(apiResponse.getItems());
                    videoList = saveToDatabase(modelList);
                }
            }

            @Override
            public void onFailure(Call<JsonSearchModel> call, Throwable t) {
                Log.e("error", "onFailure" + t);

            }
        });

    }

    private ArrayList<YoutubeVideo> saveToDatabase(ArrayList<ItemYT> itemYTList) {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();

        for (ItemYT i : itemYTList) {
            if (i == null) {
                continue;
            }

            YoutubeVideo video = new YoutubeVideo();
            video.setId(i.getId().getVideoId());

            String titleFormat = StringEscapeUtils.unescapeHtml4(i.getSnippet().getTitle());
            video.setTitle(titleFormat);

            video.setThumbnail(i.getSnippet().getThumbnail().getThumbnailHigh().getUrl());
            Channel c = new Channel();
            c.setChannelId(i.getSnippet().getChannelId());

            String channelTitleFormat = StringEscapeUtils.unescapeHtml4(i.getSnippet().getChannelTitle());
            c.setChannelTitle(channelTitleFormat);

            video.setChannel(c);

            video.setPublishedAt(i.getSnippet().getPublishedAt());

            video.setVideoUrl("https://www.youtube.com/watch?v=" + i.getId().getVideoId());

            videos.add(video);
        }

        //Save to Database

        for (YoutubeVideo i : videos) {
            if (i == null || i.getId() == null) {
                continue;
            }

            database.collection(EnumInit.Table.YoutubeVideo.name).document(i.getId()).set(i);
            Log.e("database", "Video: " + i.getTitle());

        }

        return videos;
    }

    interface YoutubeAPICall {
        @GET
        Call<JsonSearchModel> getYT(@Url String url);
    }

}

