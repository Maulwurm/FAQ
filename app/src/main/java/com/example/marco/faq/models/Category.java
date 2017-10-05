package com.example.marco.faq.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marco on 05.10.2017.
 */

public class Category {
    @SerializedName("category_id") private String category_id;
    @SerializedName("category_title") private String category_title;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }
}
