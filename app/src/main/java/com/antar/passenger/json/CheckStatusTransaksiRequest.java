package com.antar.passenger.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckStatusTransaksiRequest {

    @Expose
    @SerializedName("id_transaksi")
    private String idTransaksi;

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }
}
