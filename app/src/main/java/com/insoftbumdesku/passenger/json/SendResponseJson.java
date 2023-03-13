package com.insoftbumdesku.passenger.json;

import com.insoftbumdesku.passenger.models.TransaksiSendModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class SendResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<TransaksiSendModel> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransaksiSendModel> getData() {
        return data;
    }

    public void setData(List<TransaksiSendModel> data) {
        this.data = data;
    }
}
