package com.antar.passenger.json;

import com.antar.passenger.models.BeritaModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class BeritaDetailResponseJson {

    @Expose
    @SerializedName("data")
    private List<BeritaModel> data = new ArrayList<>();

    public List<BeritaModel> getData() {
        return data;
    }

    public void setData(List<BeritaModel> data) {
        this.data = data;
    }
}
