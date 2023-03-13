package com.insoftbumdesku.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpRequestResponse implements Serializable {
    @SerializedName("ref_id")
    private String refId;
    @SerializedName("status")
    private int status;
    @SerializedName("code")
    private String code;
    @SerializedName("hp")
    private String phoneNumber;
    @SerializedName("price")
    private int price;
    @SerializedName("message")
    private String message;
    @SerializedName("balance")
    private int balance;
    @SerializedName("tr_id")
    private int transferId;
    @SerializedName("rc")
    private String rc;
    @SerializedName("sn")
    private String meteredToken;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getMeteredToken() {
        return meteredToken;
    }

    public void setMeteredToken(String meteredToken) {
        this.meteredToken = meteredToken;
    }
}
