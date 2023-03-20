package com.antar.passenger.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RegisterRequestJson {

    @SerializedName("fullnama")
    @Expose
    private String fullNama;

    @SerializedName("kota")
    @Expose
    private String kota;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("no_telepon")
    @Expose
    private String noTelepon;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir = "-";

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("fotopelanggan")
    @Expose
    private String fotopelanggan;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("checked")
    @Expose
    private String checked;

    @SerializedName("refcode")
    @Expose
    private String refcode;

    public String getFullNama() {
        return fullNama;
    }

    public void setFullNama(String fullNama) {
        this.fullNama = fullNama;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFotopelanggan() {
        return fotopelanggan;
    }

    public void setFotopelanggan(String fotopelanggan) {
        this.fotopelanggan = fotopelanggan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }
}
