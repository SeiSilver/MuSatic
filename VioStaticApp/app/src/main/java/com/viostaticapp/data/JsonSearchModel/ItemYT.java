package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemYT {

    @SerializedName("id")
    @Expose
    private VideoID id;

    @SerializedName("snippet")
    @Expose
    private SnippetYT snippet;

    public ItemYT(VideoID id, SnippetYT snippet) {
        this.id = id;
        this.snippet = snippet;
    }

    public ItemYT() {
    }

}
