package com.insoftbumdesku.passenger.json;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobileTopUpDetailResponseModel implements Serializable {
    @SerializedName("pulsa_code")
    private String phoneCreditCode;
    @SerializedName("pulsa_op")
    private String phoneCreditOperator;
    @SerializedName("pulsa_nominal")
    private String phoneCreditDescription;
    @SerializedName("pulsa_price")
    private int phoneCreditPrice;
    @SerializedName("pulsa_type")
    private String phoneCreditType;
    @SerializedName("status")
    private String phoneCreditStatus;

    public String getPhoneCreditCode() {
        return phoneCreditCode;
    }

    public void setPhoneCreditCode(String phoneCreditCode) {
        this.phoneCreditCode = phoneCreditCode;
    }

    public String getPhoneCreditOperator() {
        return phoneCreditOperator;
    }

    public void setPhoneCreditOperator(String phoneCreditOperator) {
        this.phoneCreditOperator = phoneCreditOperator;
    }

    public String getPhoneCreditDescription() {
        return phoneCreditDescription;
    }

    public void setPhoneCreditDescription(String phoneCreditDescription) {
        this.phoneCreditDescription = phoneCreditDescription;
    }

    public int getPhoneCreditPrice() {
        return phoneCreditPrice;
    }

    public void setPhoneCreditPrice(int phoneCreditPrice) {
        this.phoneCreditPrice = phoneCreditPrice;
    }

    public String getPhoneCreditType() {
        return phoneCreditType;
    }

    public void setPhoneCreditType(String phoneCreditType) {
        this.phoneCreditType = phoneCreditType;
    }

    public String getPhoneCreditStatus() {
        return phoneCreditStatus;
    }

    public void setPhoneCreditStatus(String phoneCreditStatus) {
        this.phoneCreditStatus = phoneCreditStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "MobileTopUpDetailResponseModel{" +
                "phoneCreditCode='" + phoneCreditCode + '\'' +
                ", phoneCreditOperator='" + phoneCreditOperator + '\'' +
                ", phoneCreditDescription='" + phoneCreditDescription + '\'' +
                ", phoneCreditPrice=" + phoneCreditPrice +
                ", phoneCreditType='" + phoneCreditType + '\'' +
                ", phoneCreditStatus='" + phoneCreditStatus + '\'' +
                '}';
    }
}