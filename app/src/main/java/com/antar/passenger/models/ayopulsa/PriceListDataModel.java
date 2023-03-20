package com.antar.passenger.models.ayopulsa;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PriceListDataModel implements Serializable {
    @SerializedName("data")
    private List<PriceListDetailModel> dataX;

    public List<PriceListDetailModel> getDataX() {
        return dataX;
    }

    public void setDataX(List<PriceListDetailModel> dataX) {
        this.dataX = dataX;
    }
}
