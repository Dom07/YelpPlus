package com.example.yelpplus;

public class UploadObject {
    private Boolean success;

    public UploadObject(Boolean success){
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
