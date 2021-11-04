package com.example.steganoapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("data")
    private UsersData data;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;


    public UsersData getData() {
        return data;
    }

    public void setData(UsersData data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ResponseLogin(UsersData data, Boolean status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
}


