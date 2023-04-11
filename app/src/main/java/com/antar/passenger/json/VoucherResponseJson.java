package com.antar.passenger.json;

import com.antar.passenger.models.VoucherModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 10/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class VoucherResponseJson {

    @SerializedName("data")
    @Expose
    private List<VoucherModel> voucherModelList;

    public List<VoucherModel> getVoucherModelList() {
        return voucherModelList;
    }

    public void setVoucherModelList(List<VoucherModel> voucherModelList) {
        this.voucherModelList = voucherModelList;
    }
}
