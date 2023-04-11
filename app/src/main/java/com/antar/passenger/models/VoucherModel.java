package com.antar.passenger.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ardian Iqbal Yusmartito on 10/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class VoucherModel extends RealmObject implements Serializable {

    public VoucherModel() {
    }

    public VoucherModel(int id, String voucherName, String description, int voucherNominal, String voucherType, int isiVoucher, int minimumTransaksi, String expired, String fitur, String imageVoucher, String status) {
        this.id = id;
        this.voucherName = voucherName;
        this.description = description;
        this.voucherNominal = voucherNominal;
        this.voucherType = voucherType;
        this.isiVoucher = isiVoucher;
        this.minimumTransaksi = minimumTransaksi;
        this.expired = expired;
        this.fitur = fitur;
        this.imageVoucher = imageVoucher;
        this.status = status;
    }

    @PrimaryKey
    @SerializedName("id_voucher_promo")
    private int id;

    @SerializedName("nama_voucher_promo")
    @Expose
    private String voucherName;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("harga_voucher")
    @Expose
    private int hargaVoucher;

    @SerializedName("nominal_voucher_promo")
    @Expose
    private int voucherNominal;

    @SerializedName("type_voucher_promo")
    @Expose
    private String voucherType;

    @SerializedName("isi_voucher_promo")
    @Expose
    private int isiVoucher;

    @SerializedName("minimum_transaksi")
    @Expose
    private int minimumTransaksi;

    @SerializedName("expired")
    @Expose
    private String expired;

    @SerializedName("fitur")
    @Expose
    private String fitur;

    @SerializedName("image_voucher_promo")
    @Expose
    private String imageVoucher;

    @SerializedName("status")
    @Expose
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVoucherNominal() {
        return voucherNominal;
    }

    public void setVoucherNominal(int voucherNominal) {
        this.voucherNominal = voucherNominal;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public int getIsiVoucher() {
        return isiVoucher;
    }

    public void setIsiVoucher(int isiVoucher) {
        this.isiVoucher = isiVoucher;
    }

    public int getMinimumTransaksi() {
        return minimumTransaksi;
    }

    public void setMinimumTransaksi(int minimumTransaksi) {
        this.minimumTransaksi = minimumTransaksi;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getFitur() {
        return fitur;
    }

    public void setFitur(String fitur) {
        this.fitur = fitur;
    }

    public String getImageVoucher() {
        return imageVoucher;
    }

    public void setImageVoucher(String imageVoucher) {
        this.imageVoucher = imageVoucher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHargaVoucher() {
        return hargaVoucher;
    }

    public void setHargaVoucher(int hargaVoucher) {
        this.hargaVoucher = hargaVoucher;
    }

    @Override
    public String toString() {
        return "VoucherModel{" +
                "hargaVoucher=" + hargaVoucher +
                '}';
    }
}
