package com.viostaticapp.model;

import lombok.Data;

@Data
public class CategoryItem {

    Integer id;

    Integer image;

    public CategoryItem(Integer id, Integer image) {
        this.id = id;
        this.image = image;
    }


}
