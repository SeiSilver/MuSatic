package com.viostaticapp.service;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp._config.YoutubeAPIConfig;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.Channel;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.data.JsonSearchModel.ItemYT;
import com.viostaticapp.data.JsonSearchModel.JsonSearchAPIModel;

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

    private final String APIKEY = YoutubeAPIConfig.KEY;
    private final String BASE_URL = "https://youtube.googleapis.com/youtube/v3/";
    private final String schema = "search?";

    private String part = "part=snippet";
    private String maxResults = "&maxResults=20";
    private String searchSchema = "&q=";
    private String orderBy = "&order=relevance";
    private String key = "&key=" + APIKEY;
    private String type = "&type=video";

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private IOnYoutubeAPI youtubeAPICall = null;

    public IOnYoutubeAPI getYoutubeAPICall() {
        if (youtubeAPICall == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            youtubeAPICall = retrofit.create(IOnYoutubeAPI.class);
        }
        return youtubeAPICall;
    }

    @Override
    public void search(String query) {

        ArrayList<ItemYT> modelList = new ArrayList<>();

        String url_call = BASE_URL + schema +
                part + maxResults +
                searchSchema + query +
                type +
                orderBy + key;

        Call<JsonSearchAPIModel> data = getYoutubeAPICall().getYoutubeResult(url_call);

        data.enqueue(new Callback<JsonSearchAPIModel>() {
            @Override
            public void onResponse(Call<JsonSearchAPIModel> call, Response<JsonSearchAPIModel> response) {

                if (response.errorBody() != null) {
                    Log.e("error", "onFailure" + response.errorBody());
                } else {
                    JsonSearchAPIModel apiResponse = response.body();
                    modelList.addAll(apiResponse.getItems());
                    saveToDatabase(modelList);
                }
            }

            @Override
            public void onFailure(Call<JsonSearchAPIModel> call, Throwable t) {
                Log.e("error", "onFailure" + t);

            }
        });

    }

    private ArrayList<YoutubeVideo> saveToDatabase(ArrayList<ItemYT> itemYTList) {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();

        for (ItemYT i : itemYTList) {

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

            //Save to Database
            database.collection(EnumInit.Table.YoutubeVideo.name).document(video.getId()).set(video);
//            Log.e("database", "Video: " + video.getTitle());
        }

        return videos;
    }

    public interface IOnYoutubeAPI {
        @GET
        Call<JsonSearchAPIModel> getYoutubeResult(@Url String url);
    }

}

