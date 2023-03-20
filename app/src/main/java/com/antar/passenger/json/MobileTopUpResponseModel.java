package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MobileTopUpResponseModel implements Serializable {
    @SerializedName("data")
    List<MobileTopUpDetailResponseModel> detailResponse;

    public List<MobileTopUpDetailResponseModel> getDetailResponse() {
        return detailResponse;
    }

    public void setDetailResponse(List<MobileTopUpDetailResponseModel> detailResponse) {
        this.detailResponse = detailResponse;
    }
}
