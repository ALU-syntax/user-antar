package com.antar.passenger.json;

import com.antar.passenger.models.BankModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class BankResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<BankModel> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BankModel> getData() {
        return data;
    }

    public void setData(List<BankModel> data) {
        this.data = data;
    }
}
