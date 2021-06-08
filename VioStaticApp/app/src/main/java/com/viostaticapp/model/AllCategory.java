package com.viostaticapp.model;

import java.util.List;

import lombok.Data;

@Data
public class AllCategory {

    private String categoryTitle;
    private List<CategoryItem> categoryItemList;

    public AllCategory(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public AllCategory(String categoryTitle, List<CategoryItem> categoryItemList) {
        this.categoryTitle = categoryTitle;
        this.categoryItemList = categoryItemList;
    }
}
