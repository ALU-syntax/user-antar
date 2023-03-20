package com.antar.passenger.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class StatusTransaksiModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
