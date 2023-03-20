package com.antar.passenger.json;

import com.antar.passenger.models.Cabang;

import java.util.ArrayList;
import java.util.List;

public class DaftarWilayahResponseJson {
    private String resultcode;
    private List<Cabang> data = new ArrayList<>();

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public List<Cabang> getData() {
        return data;
    }

    public void setData(List<Cabang> data) {
        this.data = data;
    }
}
