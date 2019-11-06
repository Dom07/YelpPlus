package com.example.yelpplus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business {

    @SerializedName("_id")
    private String business_id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("address")
    private String address;

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }

    @SerializedName("photo")
    private String[] photo;

    @SerializedName("review")
    private List<Reviews_profile> review;


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

    public String getBusiness_id() {return business_id;}

    public void setBusiness_id(String id) {this.business_id = id;}

    public List<Reviews_profile> getReview() {
        return review;
    }

    public void setReview(List<Reviews_profile> review) {
        this.review = review;
    }
}
