package com.example.yelpplus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews_profile {

    @SerializedName("product_rating")
    private float product;

    @SerializedName("service_rating")
    private float service;

    @SerializedName("ambience_rating")
    private float ambience;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("business")
    private Business business;

    public float getProduct() {
        return product;
    }

    public void setProduct(float product) {
        this.product = product;
    }

    public float getService() {
        return service;
    }

    public void setService(float service) {
        this.service = service;
    }

    public float getAmbience() {
        return ambience;
    }

    public void setAmbience(float ambience) {
        this.ambience = ambience;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
