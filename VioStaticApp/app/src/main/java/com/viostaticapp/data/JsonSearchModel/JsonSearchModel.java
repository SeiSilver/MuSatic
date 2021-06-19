package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonSearchModel {

    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;

    @SerializedName("items")
    @Expose
    private List<ItemYT> items;

    public JsonSearchModel(String nextPageToken, List<ItemYT> items) {
        this.nextPageToken = nextPageToken;
        this.items = items;
    }

    public JsonSearchModel() {
    }

}
