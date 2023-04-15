package com.antar.passenger.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ardian Iqbal Yusmartito on 15/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class BuyVoucherRequestJson {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("card")
    @Expose
    private String card;

    @SerializedName("no_telepon")
    @Expose
    private String notelepon;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("nama")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("id_voucher")
    @Expose
    private String idVoucher;

    @SerializedName("quantity")
    @Expose
    private String quantityVoucher;

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getQuantityVoucher() {
        return quantityVoucher;
    }

    public void setQuantityVoucher(String quantityVoucher) {
        this.quantityVoucher = quantityVoucher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
