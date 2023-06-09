package com.antar.passenger.json;

import com.antar.passenger.models.DriverModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class GetNearRideCarResponseJson {

    @Expose
    @SerializedName("data")
    private ArrayList<DriverModel> data = new ArrayList<>();

    public ArrayList<DriverModel> getData() {
        return data;
    }

    public void setData(ArrayList<DriverModel> data) {
        this.data = data;
    }
}
