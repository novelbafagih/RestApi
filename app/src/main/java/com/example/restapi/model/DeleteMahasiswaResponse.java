package com.example.restapi.model;

import com.google.gson.annotations.SerializedName;

public class DeleteMahasiswaResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("id")
    private String id;

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }
}
