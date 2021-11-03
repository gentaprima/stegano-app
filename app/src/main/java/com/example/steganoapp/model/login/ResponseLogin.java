package com.example.steganoapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("data")
    @Expose
    private UsersData data;
    @SerializedName("status")
    @Expose
    private Boolean status;

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

    @SerializedName("message")
    @Expose
    private String message;

    private static class UsersData {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPersonalNumber() {
            return personalNumber;
        }

        public void setPersonalNumber(String personalNumber) {
            this.personalNumber = personalNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("personal_number")
        @Expose
        private String personalNumber;
        @SerializedName("name")
        @Expose
        private String name;
    }
}


