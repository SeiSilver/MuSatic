package com.viostaticapp.data.JsonYoutubeAPIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonYoutubeAPIModel {

    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;

    @SerializedName("items")
    @Expose
    private List<ItemYT> items;

    public JsonYoutubeAPIModel(String nextPageToken, List<ItemYT> items) {
        this.nextPageToken = nextPageToken;
        this.items = items;
    }

    public JsonYoutubeAPIModel() {
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<ItemYT> getItems() {
        return items;
    }

    public void setItems(List<ItemYT> items) {
        this.items = items;
    }
}
