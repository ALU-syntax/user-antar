package com.antar.passenger.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class ItemPesanan extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id_item")
    private int id_item;

    @Expose
    @SerializedName("nama_item")
    private String nama_item;

    @Expose
    @SerializedName("harga_item")
    private int harga_item;

    @Expose
    @SerializedName("quantity")
    private int quantity;

    @Expose
    @SerializedName("total_harga")
    private int total_harga;

    @Expose
    @SerializedName("catatan")
    private String catatan;

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getNama_item() {
        return nama_item;
    }

    public void setNama_item(String nama_item) {
        this.nama_item = nama_item;
    }

    public int getHarga_item() {
        return harga_item;
    }

    public void setHarga_item(int harga_item) {
        this.harga_item = harga_item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
