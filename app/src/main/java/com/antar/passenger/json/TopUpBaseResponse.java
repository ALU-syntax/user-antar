package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpBaseResponse implements Serializable {
    @SerializedName("data")
    TopUpRequestResponse data;

    public TopUpRequestResponse getData() {
        return data;
    }

    public void setData(TopUpRequestResponse data) {
        this.data = data;
    }
}
