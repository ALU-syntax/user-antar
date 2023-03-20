package com.antar.passenger.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class TransaksiSendModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName("status")
    public int status;
    @Expose
    @SerializedName("nama_pengirim")
    public String namaPengirim;
    @Expose
    @SerializedName("telepon_pengirim")
    public String teleponPengirim;
    @Expose
    @SerializedName("nama_penerima")
    public String namaPenerima;

    @Expose
    @SerializedName("nama_penerima2")
    public String namaPenerima2;

    @Expose
    @SerializedName("nama_penerima3")
    public String namaPenerima3;

    @Expose
    @SerializedName("nama_penerima4")
    public String namaPenerima4;

    @Expose
    @SerializedName("nama_penerima5")
    public String namaPenerima5;

    @Expose
    @SerializedName("telepon_penerima")
    public String teleponPenerima;

    @Expose
    @SerializedName("telepon_penerima2")
    public String teleponPenerima2;

    @Expose
    @SerializedName("telepon_penerima3")
    public String teleponPenerima3;

    @Expose
    @SerializedName("telepon_penerima4")
    public String teleponPenerima4;

    @Expose
    @SerializedName("nama_barang")
    public String namaBarang;
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("id_pelanggan")
    private String idPelanggan;
    @Expose
    @SerializedName("id_driver")
    private String idDriver;
    @Expose
    @SerializedName("order_fitur")
    private String orderFitur;
    @Expose
    @SerializedName("start_latitude")
    private double startLatitude;
    @Expose
    @SerializedName("start_longitude")
    private double startLongitude;

    @Expose
    @SerializedName("end_latitude")
    private double endLatitude;

    @Expose
    @SerializedName("end_longitude")
    private double endLongitude;

    @Expose
    @SerializedName("end_lat2")
    private double endLat2;

    @Expose
    @SerializedName("end_long2")
    private double endLong2;

    @Expose
    @SerializedName("end_lat3")
    private double endLat3;

    @Expose
    @SerializedName("end_long3")
    private double endLong3;

    @Expose
    @SerializedName("end_lat4")
    private double endLat4;

    @Expose
    @SerializedName("end_long4")
    private double endLong4;

    @Expose
    @SerializedName("end_lat5")
    private double endLat5;

    @Expose
    @SerializedName("end_long5")
    private double endLong5;


    @Expose
    @SerializedName("jarak")
    private double jarak;
    @Expose
    @SerializedName("harga")
    private long harga;
    @Expose
    @SerializedName("waktu_order")
    private Date waktuOrder;
    @Expose
    @SerializedName("waktu_selesai")
    private Date waktuSelesai;
    @Expose
    @SerializedName("alamat_asal")
    private String alamatAsal;
    @Expose
    @SerializedName("alamat_tujuan")
    private String alamatTujuan;

    @Expose
    @SerializedName("alamat_tujuan2")
    private String alamatTujuan2;

    @Expose
    @SerializedName("alamat_tujuan3")
    private String alamatTujuan3;

    @Expose
    @SerializedName("alamat_tujuan4")
    private String alamatTujuan4;

    @Expose
    @SerializedName("alamat_tujuan5")
    private String alamatTujuan5;
    @Expose
    @SerializedName("kode_promo")
    private String kodePromo;
    @Expose
    @SerializedName("kredit_promo")
    private String kreditPromo;
    @Expose
    @SerializedName("pakai_wallet")
    private boolean pakaiWallet;
    @Expose
    @SerializedName("rate")
    private String rate;
    @Expose
    @SerializedName("estimasi_time")
    private String estimasi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getOrderFitur() {
        return orderFitur;
    }

    public void setOrderFitur(String orderFitur) {
        this.orderFitur = orderFitur;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public Date getWaktuOrder() {
        return waktuOrder;
    }

    public void setWaktuOrder(Date waktuOrder) {
        this.waktuOrder = waktuOrder;
    }

    public Date getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(Date waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public void setAlamatAsal(String alamatAsal) {
        this.alamatAsal = alamatAsal;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public String getKreditPromo() {
        return kreditPromo;
    }

    public void setKreditPromo(String kreditPromo) {
        this.kreditPromo = kreditPromo;
    }

    public boolean isPakaiWallet() {
        return pakaiWallet;
    }

    public void setPakaiWallet(boolean pakaiWallet) {
        this.pakaiWallet = pakaiWallet;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

    public String getNamaPengirim() {
        return namaPengirim;
    }

    public void setNamaPengirim(String namaPengirim) {
        this.namaPengirim = namaPengirim;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getTeleponPengirim() {
        return teleponPengirim;
    }

    public void setTeleponPengirim(String teleponPengirim) {
        this.teleponPengirim = teleponPengirim;
    }

    public String getTeleponPenerima() {
        return teleponPengirim;
    }

    public void setTeleponPenerima(String teleponPenerima) {
        this.teleponPenerima = teleponPenerima;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getNamaPenerima2() {
        return namaPenerima2;
    }

    public void setNamaPenerima2(String namaPenerima2) {
        this.namaPenerima2 = namaPenerima2;
    }

    public String getNamaPenerima3() {
        return namaPenerima3;
    }

    public void setNamaPenerima3(String namaPenerima3) {
        this.namaPenerima3 = namaPenerima3;
    }

    public String getNamaPenerima4() {
        return namaPenerima4;
    }

    public void setNamaPenerima4(String namaPenerima4) {
        this.namaPenerima4 = namaPenerima4;
    }

    public String getNamaPenerima5() {
        return namaPenerima5;
    }

    public void setNamaPenerima5(String namaPenerima5) {
        this.namaPenerima5 = namaPenerima5;
    }

    public String getTeleponPenerima2() {
        return teleponPenerima2;
    }

    public void setTeleponPenerima2(String teleponPenerima2) {
        this.teleponPenerima2 = teleponPenerima2;
    }

    public String getTeleponPenerima3() {
        return teleponPenerima3;
    }

    public void setTeleponPenerima3(String teleponPenerima3) {
        this.teleponPenerima3 = teleponPenerima3;
    }

    public String getTeleponPenerima4() {
        return teleponPenerima4;
    }

    public void setTeleponPenerima4(String teleponPenerima4) {
        this.teleponPenerima4 = teleponPenerima4;
    }

    public double getEndLat2() {
        return endLat2;
    }

    public void setEndLat2(double endLat2) {
        this.endLat2 = endLat2;
    }

    public double getEndLong2() {
        return endLong2;
    }

    public void setEndLong2(double endLong2) {
        this.endLong2 = endLong2;
    }

    public double getEndLat3() {
        return endLat3;
    }

    public void setEndLat3(double endLat3) {
        this.endLat3 = endLat3;
    }

    public double getEndLong3() {
        return endLong3;
    }

    public void setEndLong3(double endLong3) {
        this.endLong3 = endLong3;
    }

    public double getEndLat4() {
        return endLat4;
    }

    public void setEndLat4(double endLat4) {
        this.endLat4 = endLat4;
    }

    public double getEndLong4() {
        return endLong4;
    }

    public void setEndLong4(double endLong4) {
        this.endLong4 = endLong4;
    }

    public double getEndLat5() {
        return endLat5;
    }

    public void setEndLat5(double endLat5) {
        this.endLat5 = endLat5;
    }

    public double getEndLong5() {
        return endLong5;
    }

    public void setEndLong5(double endLong5) {
        this.endLong5 = endLong5;
    }

    public String getAlamatTujuan2() {
        return alamatTujuan2;
    }

    public void setAlamatTujuan2(String alamatTujuan2) {
        this.alamatTujuan2 = alamatTujuan2;
    }

    public String getAlamatTujuan3() {
        return alamatTujuan3;
    }

    public void setAlamatTujuan3(String alamatTujuan3) {
        this.alamatTujuan3 = alamatTujuan3;
    }

    public String getAlamatTujuan4() {
        return alamatTujuan4;
    }

    public void setAlamatTujuan4(String alamatTujuan4) {
        this.alamatTujuan4 = alamatTujuan4;
    }

    public String getAlamatTujuan5() {
        return alamatTujuan5;
    }

    public void setAlamatTujuan5(String alamatTujuan5) {
        this.alamatTujuan5 = alamatTujuan5;
    }
}
