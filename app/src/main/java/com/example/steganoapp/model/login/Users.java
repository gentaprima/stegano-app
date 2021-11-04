package com.example.steganoapp.model.login;

public class Users {
    private String id;
    private String name;
    private String personal_number;
    private String image;

    public Users(String id, String name, String personal_number,String image) {
        this.id = id;
        this.name = name;
        this.personal_number = personal_number;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonal_number() {
        return personal_number;
    }

    public void setPersonal_number(String personal_number) {
        this.personal_number = personal_number;
    }
}
