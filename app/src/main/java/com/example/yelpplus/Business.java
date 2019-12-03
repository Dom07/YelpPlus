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

    @SerializedName("claimed")
    private Boolean claimed;

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

    @SerializedName("event_booking_status")
    private Boolean registered;

    @SerializedName("owner")
    private String owner_id;

    @SerializedName("menu")
    private String[] menu;

    @SerializedName("event")
    private List<EventBooking> events;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("long")
    private String longitude;

    @SerializedName("avg_price_rating")
    private String avg_price_rating;

    @SerializedName("avg_ambience_rating")
    private String avg_ambience_rating;

    @SerializedName("avg_product_rating")
    private String avg_product_rating;

    @SerializedName("avg_service_rating")
    private String avg_service_rating;

    @SerializedName("avg_rating")
    private String avg_rating;

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

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }

    public Boolean getRegistered() {return registered;}

    public void setRegistered(Boolean registered) {this.registered = registered;}

    public String getOwner_id() {return owner_id;}

    public void setOwner_id(String owner_id) {this.owner_id = owner_id;}

    public String[] getMenu() {
        return menu;
    }

    public void setMenu(String[] menu) {
        this.menu = menu;
    }

    public List<EventBooking> getEvents() {
        return events;
    }

    public void setEvents(List<EventBooking> events) {
        this.events = events;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAvg_price_rating() {
        return avg_price_rating;
    }

    public String getAvg_ambience_rating() {
        return avg_ambience_rating;
    }

    public String getAvg_product_rating() {
        return avg_product_rating;
    }

    public String getAvg_service_rating() {
        return avg_service_rating;
    }

    public String getAvg_rating() {
        return avg_rating;
    }
}
