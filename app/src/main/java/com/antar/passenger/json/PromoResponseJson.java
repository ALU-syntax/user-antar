package com.antar.passenger.json;

import com.antar.passenger.models.KodePromoModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



public class PromoResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("nominal")
    @Expose
    private String nominal;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("data")
    @Expose
    private List<KodePromoModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<KodePromoModel> getData() {
        return data;
    }

    public void setData(List<KodePromoModel> data) {
        this.data = data;
    }
}
