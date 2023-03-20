package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobilePulsaPLNCheckResponse implements Serializable {
    @SerializedName("data")
    private MobilePulsaPlnDetailResponse detailResponse;

    public MobilePulsaPlnDetailResponse getDetailResponse() {
        return detailResponse;
    }

    public void setDetailResponse(MobilePulsaPlnDetailResponse detailResponse) {
        this.detailResponse = detailResponse;
    }

}
