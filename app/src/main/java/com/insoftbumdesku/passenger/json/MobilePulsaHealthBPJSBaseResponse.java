package com.insoftbumdesku.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MobilePulsaHealthBPJSBaseResponse implements Serializable {
    @SerializedName("data")
    MobilePulsaHealthBPJSResponseModel data;

    @SerializedName("meta")
    List<Object> meta;

    public MobilePulsaHealthBPJSResponseModel getData() {
        return data;
    }

    public void setData(MobilePulsaHealthBPJSResponseModel data) {
        this.data = data;
    }
}
