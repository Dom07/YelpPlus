package com.example.yelpplus;

import android.widget.RatingBar;

import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("business_name")
    private String business_name;

    @SerializedName("username")
    private String username;

    @SerializedName("product")
    private float product;

    @SerializedName("service")
    private float service;

    @SerializedName("ambience")
    private float ambience;

    @SerializedName("reviews")
    private String reviews;

    public Reviews(String username, String business_name, float product, float service, float ambience, String reviews){
        this.business_name = business_name;
        this.username = username;
        this.product = product;
        this.service = service;
        this.ambience = ambience;
        this.reviews = reviews;
    }

    public String getBusiness_name() {
        return business_name;
    }
    public void setBusiness_name(String business_name) { this.business_name = business_name; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { this.username = username; }

    public float getProduct() {
        return product;
    }
    public void setProduct(float product) {
        this.product = product;
    }

    public float getService() {
        return service;
    }
    public void setService(float service) { this.service = service; }

    public float getAmbience() {
        return ambience;
    }
    public void setAmbience(float ambience) {
        this.ambience = ambience;
    }

    public String getReviews() { return reviews; }
    public void setReviews(String reviews) {
        this.reviews = reviews;
    }
}
