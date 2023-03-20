package com.antar.passenger.json;

import com.antar.passenger.models.DriverModel;
import com.antar.passenger.models.ItemPesananModel;
import com.antar.passenger.models.TransaksiModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class DetailTransResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<TransaksiModel> data = new ArrayList<>();

    @Expose
    @SerializedName("driver")
    private List<DriverModel> driver = new ArrayList<>();

    @Expose
    @SerializedName("item")
    private List<ItemPesananModel> item = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransaksiModel> getData() {
        return data;
    }

    public void setData(List<TransaksiModel> data) {
        this.data = data;
    }

    public List<DriverModel> getDriver() {
        return driver;
    }

    public void setDriver(List<DriverModel> driver) {
        this.driver = driver;
    }

    public List<ItemPesananModel> getItem() {
        return item;
    }

    public void setItem(List<ItemPesananModel> item) {
        this.item = item;
    }
}
