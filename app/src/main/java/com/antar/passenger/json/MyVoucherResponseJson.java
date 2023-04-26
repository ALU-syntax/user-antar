package com.antar.passenger.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ardian Iqbal Yusmartito on 25/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class MyVoucherResponseJson implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("id_voucher")
    private int idVoucher;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("id_voucher_promo")
    private int idVoucherPromo;

    @SerializedName("nama_voucher_promo")
    private String namaVoucherPromo;

    @SerializedName("description")
    private String description;

    @SerializedName("harga_voucher")
    private int hargaVoucher;

    @SerializedName("nominal_voucher_promo")
    private int nominalVoucherPromo;

    @SerializedName("type_voucher_promo")
    private String typeVoucherPromo;

    @SerializedName("isi_voucher_promo")
    private int isiVoucherPromo;

    @SerializedName("minimum_transaksi")
    private int minimumTransaksi;

    @SerializedName("expired")
    private String expired;

    @SerializedName("fitur")
    private String fitur;

    @SerializedName("image_voucher_promo")
    private String imageVoucherPromo;

    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdVoucherPromo() {
        return idVoucherPromo;
    }

    public void setIdVoucherPromo(int idVoucherPromo) {
        this.idVoucherPromo = idVoucherPromo;
    }

    public String getNamaVoucherPromo() {
        return namaVoucherPromo;
    }

    public void setNamaVoucherPromo(String namaVoucherPromo) {
        this.namaVoucherPromo = namaVoucherPromo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHargaVoucher() {
        return hargaVoucher;
    }

    public void setHargaVoucher(int hargaVoucher) {
        this.hargaVoucher = hargaVoucher;
    }

    public int getNominalVoucherPromo() {
        return nominalVoucherPromo;
    }

    public void setNominalVoucherPromo(int nominalVoucherPromo) {
        this.nominalVoucherPromo = nominalVoucherPromo;
    }

    public String getTypeVoucherPromo() {
        return typeVoucherPromo;
    }

    public void setTypeVoucherPromo(String typeVoucherPromo) {
        this.typeVoucherPromo = typeVoucherPromo;
    }

    public int getIsiVoucherPromo() {
        return isiVoucherPromo;
    }

    public void setIsiVoucherPromo(int isiVoucherPromo) {
        this.isiVoucherPromo = isiVoucherPromo;
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

    public String getImageVoucherPromo() {
        return imageVoucherPromo;
    }

    public void setImageVoucherPromo(String imageVoucherPromo) {
        this.imageVoucherPromo = imageVoucherPromo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
