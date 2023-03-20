package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobilePulsaPlnDetailResponse implements Serializable {
    @SerializedName("status")
    private int status;
    @SerializedName("hp")
    private String userPLNMeterId;
    @SerializedName("meter_no")
    private String meterNo;
    @SerializedName("subscriber_id")
    private String subscriberId;
    @SerializedName("name")
    private String name;
    @SerializedName("segment_power")
    private String segmentPower;
    @SerializedName("message")
    private String message;
    @SerializedName("rc")
    private String rc;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserPLNMeterId() {
        return userPLNMeterId;
    }

    public void setUserPLNMeterId(String userPLNMeterId) {
        this.userPLNMeterId = userPLNMeterId;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSegmentPower() {
        return segmentPower;
    }

    public void setSegmentPower(String segmentPower) {
        this.segmentPower = segmentPower;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }
}
