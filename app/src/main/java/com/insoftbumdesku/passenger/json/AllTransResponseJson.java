package com.insoftbumdesku.passenger.json;

import com.insoftbumdesku.passenger.models.AllTransaksiModel;
import com.insoftbumdesku.passenger.models.DriverModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AllTransResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<AllTransaksiModel> data = new ArrayList<>();

    @Expose
    @SerializedName("driver")
    private List<DriverModel> driver = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AllTransaksiModel> getData() {
        return data;
    }

    public void setData(List<AllTransaksiModel> data) {
        this.data = data;
    }

    public List<DriverModel> getDriver() {
        return driver;
    }

    public void setDriver(List<DriverModel> driver) {
        this.driver = driver;
    }
}
