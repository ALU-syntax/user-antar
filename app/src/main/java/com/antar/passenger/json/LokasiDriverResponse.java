package com.antar.passenger.json;

import com.antar.passenger.models.LokasiDriverModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 24/02/2019.
 */

public class LokasiDriverResponse {

    @SerializedName("data")
    @Expose
    private List<LokasiDriverModel> data = new ArrayList<>();

    public List<LokasiDriverModel> getData() {
        return data;
    }

}
