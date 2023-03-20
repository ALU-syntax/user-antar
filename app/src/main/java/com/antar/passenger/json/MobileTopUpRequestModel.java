package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobileTopUpRequestModel implements Serializable {

    @SerializedName("commands")
    String command;
    @SerializedName("username")
    String username;
    @SerializedName("sign")
    String sign;
    @SerializedName("status")
    String status;
    @SerializedName("ref_id")
    String orderId;
    @SerializedName("hp")
    String destinationPhoneNumber;
    @SerializedName("pulsa_code")
    String phoneCreditCode;
    @SerializedName("month")
    String month;
    @SerializedName("code")
    String code;
    @SerializedName("tr_id")
    String transferId;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDestinationPhoneNumber() {
        return destinationPhoneNumber;
    }

    public void setDestinationPhoneNumber(String destinationPhoneNumber) {
        this.destinationPhoneNumber = destinationPhoneNumber;
    }

    public String getPhoneCreditCode() {
        return phoneCreditCode;
    }

    public void setPhoneCreditCode(String phoneCreditCode) {
        this.phoneCreditCode = phoneCreditCode;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }
}
