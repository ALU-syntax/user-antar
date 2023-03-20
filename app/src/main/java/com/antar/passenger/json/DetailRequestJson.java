package com.antar.passenger.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DetailRequestJson {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("id_driver")
    @Expose
    private String idDriver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

}
