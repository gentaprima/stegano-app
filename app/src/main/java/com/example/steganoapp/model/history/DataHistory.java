package com.example.steganoapp.model.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataHistory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("stegano_id")
    @Expose
    private String steganoId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("personal_number")
    @Expose
    private String personalNumber;
    @SerializedName("embedding_file")
    @Expose
    private String embeddingFile;
    @SerializedName("original_file")
    @Expose
    private String originalFile;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSteganoId() {
        return steganoId;
    }

    public void setSteganoId(String steganoId) {
        this.steganoId = steganoId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getEmbeddingFile() {
        return embeddingFile;
    }

    public void setEmbeddingFile(String embeddingFile) {
        this.embeddingFile = embeddingFile;
    }

    public String getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(String originalFile) {
        this.originalFile = originalFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
