package com.insoftbumdesku.passenger.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoinRequestJson {
    @SerializedName("id_user")
    @Expose
    private String id_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
