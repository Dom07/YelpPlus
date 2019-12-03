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
}
