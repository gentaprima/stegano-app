package com.example.steganoapp.obj;

public class EmbedObject {
    String mp3;
    String secretMessage;
    String password;
    public EmbedObject(String mp3, String secretMessage, String password) {
        this.mp3 = mp3;
        this.secretMessage = secretMessage;
        this.password = password;
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
}
