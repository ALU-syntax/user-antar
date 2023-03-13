package com.insoftbumdesku.passenger.models.ayopulsa;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceListDetailModel implements Serializable {
    /**
     * code : xis10
     * nominal : 10000
     * price : 11300
     * original_price : 0
     * text : 10000 - (Rp. 11.300)
     * description : -
     * status : true
     * is_pm : 0
     */

    private String code;
    private String nominal;
    private int price;
    @SerializedName("original_price")
    private int originalPrice;
    private String text;
    private String description;
    private boolean status;
    @SerializedName("is_pm")
    private int isPm;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getIsPm() {
        return isPm;
    }

    public void setIsPm(int isPm) {
        this.isPm = isPm;
    }
}
