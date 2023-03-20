package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobilePulsaHealthBPJSDesc implements Serializable {
    @SerializedName("kode_cabang")
    private String branchOffice;
    @SerializedName("nama_cabang")
    private String branchName;
    @SerializedName("sisa_pembayaran")
    private String remainingPayment;
    @SerializedName("jumlah_peserta")
    private String totalParticipants;

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(String remainingPayment) {
        this.remainingPayment = remainingPayment;
    }

    public String getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(String totalParticipants) {
        this.totalParticipants = totalParticipants;
    }
}
