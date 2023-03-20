package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpAyoPulsaRequestJson implements Serializable {

    public TopUpAyoPulsaRequestJson() {
    }

    public TopUpAyoPulsaRequestJson(String destinationNumber, String password, String code, String refId) {
        this.destinationNumber = destinationNumber;
        this.password = password;
        this.code = code;
        this.refId = refId;
    }


    @SerializedName("hp")
    private String destinationNumber;
    private String password;
    private String code;
    @SerializedName("ref_id")
    private String refId;

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
