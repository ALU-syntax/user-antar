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
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("id_user")
    private String idUser;

    @Expose
    @SerializedName("id_voucher")
    private List<VoucherModel> voucher;

    @Expose
    @SerializedName("quantity")
    private String quantity;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<VoucherModel> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<VoucherModel> voucher) {
        this.voucher = voucher;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
