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

    @SerializedName("price_rating")
    private float price;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("author")
    private String author;

    @SerializedName("user")
    private String userID;

    @SerializedName("date")
    private String date;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }
}
