package com.example.steganoapp.obj;

public class EmbedObject {
    String mp3;
    String secretMessage;
    String password;

    public String getUsersID() {
        return usersID;
    }

    String usersID;
    public EmbedObject(String mp3, String secretMessage, String password,String usersID) {
        this.mp3 = mp3;
        this.secretMessage = secretMessage;
        this.password = password;
        this.usersID = usersID;
    }

    public String getMp3() {
        return mp3;
    }

    public String getSecretMessage() {
        return secretMessage;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValid() {
        return mp3 != null && secretMessage != null && password != null && usersID !=null;
    }
}
