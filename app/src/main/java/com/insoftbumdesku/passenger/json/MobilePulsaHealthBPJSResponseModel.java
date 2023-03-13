package com.insoftbumdesku.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobilePulsaHealthBPJSResponseModel implements Serializable {
    @SerializedName("tr_id")
    private int transactionId;
    @SerializedName("code")
    private String code;
    @SerializedName("hp")
    private String hp;
    @SerializedName("tr_name")
    private String transactionName;
    @SerializedName("period")
    private  String transactionPeriod;
    @SerializedName("nominal")
    private  int nominal;
    @SerializedName("admin")
    private  int adminPayment;
    @SerializedName("ref_id")
    private String referenceId;
    @SerializedName("response_code")
    private String responseCode;
    @SerializedName("message")
    private String message;
    @SerializedName("price")
    private int price;
    @SerializedName("selling_price")
    private int sellingPrice;
    @SerializedName("desc")
    private MobilePulsaHealthBPJSDesc description;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionPeriod() {
        return transactionPeriod;
    }

    public void setTransactionPeriod(String transactionPeriod) {
        this.transactionPeriod = transactionPeriod;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getAdminPayment() {
        return adminPayment;
    }

    public void setAdminPayment(int adminPayment) {
        this.adminPayment = adminPayment;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public MobilePulsaHealthBPJSDesc getDescription() {
        return description;
    }

    public void setDescription(MobilePulsaHealthBPJSDesc description) {
        this.description = description;
    }
}