package com.example.yelpplus;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("image_url")
    private String image;

    @SerializedName("name")
    private String name;

    public Category(String image, String name){
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
