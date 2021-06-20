package com.viostaticapp.data.JsonSearchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonSearchAPIModel {

    private String nextPageToken;

    private List<ItemYT> items;

}
