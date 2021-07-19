package com.viostaticapp.data.JsonSearchModel;

import java.util.List;


public class JsonSearchAPIModel {

    private String nextPageToken;

    private List<ItemYT> items;

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
