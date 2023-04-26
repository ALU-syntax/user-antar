package com.antar.passenger.json;

import com.antar.passenger.models.UserVoucherModel;
import com.antar.passenger.models.VoucherModel;
import com.antar.passenger.models.WalletModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 11/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class UserVoucherResponseJson {

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<MyVoucherResponseJson> myVoucherResponseJson;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MyVoucherResponseJson> getMyVoucherResponseJson() {
        return myVoucherResponseJson;
    }

    public void setMyVoucherResponseJson(List<MyVoucherResponseJson> myVoucherResponseJson) {
        this.myVoucherResponseJson = myVoucherResponseJson;
    }
}
