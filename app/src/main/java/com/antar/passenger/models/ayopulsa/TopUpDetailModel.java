package com.antar.passenger.models.ayopulsa;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpDetailModel implements Serializable {
    /**
     * deposit : 950000
     * ref_id : trx1
     */

    private int deposit;
    @SerializedName("ref_id")
    private String refId;

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
