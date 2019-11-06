package com.example.yelpplus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business {

    @SerializedName("name")
    private String name;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("address")
    private String address;

    @SerializedName("sub_category")
    private String sub_category;

    @SerializedName("review")
    private List<Reviews> reviewsList;

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }

    @SerializedName("photo")
    private String[] photo;

    public Business(String name, String phone_number, String address, String sub_category, List reviewsList){
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.sub_category = sub_category;
        this.reviewsList = reviewsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
