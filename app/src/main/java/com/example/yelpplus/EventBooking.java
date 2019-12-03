package com.example.yelpplus;

import com.google.gson.annotations.SerializedName;

public class EventBooking {

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("guest_count")
    private  String guestCount;

    @SerializedName("menu")
    private String[] menu;

    @SerializedName("business_id")
    private Business business;

    //Getter and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(String guestCount) {
        this.guestCount = guestCount;
    }

    public String[] getMenu() {
        return menu;
    }

    public void setMenu(String[] menu) {
        this.menu = menu;
    }

    public Business getBusiness() {
        return business;
    }
}
