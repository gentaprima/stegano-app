package com.example.steganoapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersData {
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

    @SerializedName("image")
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UsersData(String id, String personalNumber, String name,String image) {
        this.id = id;
        this.personalNumber = personalNumber;
        this.name = name;
        this.image = image;
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
