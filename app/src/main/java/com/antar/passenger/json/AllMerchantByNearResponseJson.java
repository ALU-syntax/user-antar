package com.antar.passenger.json;

import com.antar.passenger.models.CatMerchantModel;
import com.antar.passenger.models.MerchantNearModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AllMerchantByNearResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("allmerchantnearby")
    @Expose
    private List<MerchantNearModel> data = new ArrayList<>();

    @SerializedName("kategorymerchant")
    @Expose
    private List<CatMerchantModel> kategori = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MerchantNearModel> getData() {
        return data;
    }

    public void setData(List<MerchantNearModel> data) {
        this.data = data;
    }

    public List<CatMerchantModel> getKategori() {
        return kategori;
    }

    public void setKategori(List<CatMerchantModel> kategori) {
        this.kategori = kategori;
    }
}
