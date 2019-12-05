package com.example.yelpplus;

import android.widget.ListView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("_id")
    private String user_id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("password")
    private String password;

    @SerializedName("authenticationStatus")
    private Boolean authenticationStatus;

    @SerializedName("reviews")
    private List<Reviews_profile> reviews;

    @SerializedName("secondary_id")
    private String reviewerID;

    @SerializedName("following")
    private List<User> following;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Reviews_profile> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews_profile> reviews) {
        this.reviews = reviews;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Boolean getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(Boolean authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }
}
