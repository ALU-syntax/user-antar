package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class XenditPaymentRequestJson implements Serializable {

    @SerializedName("ServerKey")
    private String serverKey;
    @SerializedName("external_id")
    private String externalId;
    @SerializedName("phone")
    private String phone;
    @SerializedName("amount")
    private long amount;
    @SerializedName("items")
    private List<XenditItemDetailRequestJson> itemList;
    @SerializedName("callback_url")
    private String callbackUrl;
    @SerializedName("redirect_url")
    private String redirectUrl;
    @SerializedName("ewallet_type")
    private String eWalletType;

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public List<XenditItemDetailRequestJson> getItemList() {
        return itemList;
    }

    public void setItemList(List<XenditItemDetailRequestJson> itemList) {
        this.itemList = itemList;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String geteWalletType() {
        return eWalletType;
    }

    public void seteWalletType(String eWalletType) {
        this.eWalletType = eWalletType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
