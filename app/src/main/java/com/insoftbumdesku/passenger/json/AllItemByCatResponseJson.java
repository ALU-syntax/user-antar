package com.insoftbumdesku.passenger.json;

import com.insoftbumdesku.passenger.models.ItemModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AllItemByCatResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("allmerchantnearby")
    @Expose
    private List<ItemModel> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemModel> getData() {
        return data;
    }

    public void setData(List<ItemModel> data) {
        this.data = data;
    }
}
