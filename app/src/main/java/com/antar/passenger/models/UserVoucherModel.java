package com.antar.passenger.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 11/04/23
 *
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class UserVoucherModel {

    @SerializedName("id")
    private int id;

    @SerializedName("id_user")
    private String userId;

    @SerializedName("id_voucher")
    private List<VoucherModel> voucher;

    @SerializedName("quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<VoucherModel> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<VoucherModel> voucher) {
        this.voucher = voucher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
