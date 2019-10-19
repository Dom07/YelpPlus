package com.example.yelpplus;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("emailId")
    private String emailId;

    @SerializedName("password")
    private String password;

    @SerializedName("authenticationStatus")
    private Boolean authenticationStatus;

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
}
