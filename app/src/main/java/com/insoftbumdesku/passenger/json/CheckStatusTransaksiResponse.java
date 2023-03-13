package com.insoftbumdesku.passenger.json;

import com.insoftbumdesku.passenger.models.DriverModel;
import com.insoftbumdesku.passenger.models.StatusTransaksiModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CheckStatusTransaksiResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<StatusTransaksiModel> data = new ArrayList<>();
    @SerializedName("list_driver")
    @Expose
    private List<DriverModel> listDriver = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StatusTransaksiModel> getData() {
        return data;
    }

    public void setData(List<StatusTransaksiModel> data) {
        this.data = data;
    }

    public List<DriverModel> getListDriver() {
        return listDriver;
    }

    public void setListDriver(List<DriverModel> listDriver) {
        this.listDriver = listDriver;
    }
}
