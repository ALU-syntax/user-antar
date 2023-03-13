package com.insoftbumdesku.passenger.json;

import com.insoftbumdesku.passenger.models.AllFiturModel;
import com.insoftbumdesku.passenger.models.BeritaModel;
import com.insoftbumdesku.passenger.models.CatMerchantModel;
import com.insoftbumdesku.passenger.models.FiturModel;
import com.insoftbumdesku.passenger.models.MerchantModel;
import com.insoftbumdesku.passenger.models.MerchantNearModel;
import com.insoftbumdesku.passenger.models.PromoModel;
import com.insoftbumdesku.passenger.models.RatingModel;
import com.insoftbumdesku.passenger.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetHomeResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("saldo")
    @Expose
    private String saldo;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("currency_text")
    @Expose
    private String currency_text;

    @SerializedName("app_aboutus")
    @Expose
    private String aboutus;

    @SerializedName("app_email")
    @Expose
    private String email;

    @SerializedName("app_contact")
    @Expose
    private String phone;

    @SerializedName("app_website")
    @Expose
    private String website;

    @SerializedName("mobilepulsa_username")
    @Expose
    private String mobilepulsausername;

    @SerializedName("mobilepulsa_api_key")
    @Expose
    private String mobilepulsaapikey;

    @SerializedName("mp_status")
    @Expose
    private String mpstatus;

    @SerializedName("mp_active")
    @Expose
    private String mpactive;

    @SerializedName("fitur")
    @Expose
    private List<FiturModel> fitur = new ArrayList<>();

    @SerializedName("allfitur")
    @Expose
    private List<AllFiturModel> allfitur = new ArrayList<>();

    @SerializedName("ratinghome")
    @Expose
    private List<RatingModel> rating = new ArrayList<>();

    @SerializedName("beritahome")
    @Expose
    private List<BeritaModel> berita = new ArrayList<>();

    @SerializedName("slider")
    @Expose
    private List<PromoModel> slider = new ArrayList<>();

    @SerializedName("data")
    @Expose
    private List<User> data = new ArrayList<>();

    @SerializedName("merchantpromo")
    @Expose
    private List<MerchantModel> merchantpromo = new ArrayList<>();

    @SerializedName("merchantnearby")
    @Expose
    private List<MerchantNearModel> merchantnear = new ArrayList<>();

    @SerializedName("kategorymerchanthome")
    @Expose
    private List<CatMerchantModel> catmerchant = new ArrayList<>();

    @SerializedName("api_password")
    @Expose
    private String ayoPesanApiPassword;

    @SerializedName("api_token")
    @Expose
    private String ayoPesanApiToken;

    @SerializedName("harga_pulsa")
    @Expose
    private String hargaPulsa;

    public String getHargaPulsa() {
        return hargaPulsa;
    }

    public void setHargaPulsa(String hargaPulsa) {
        this.hargaPulsa = hargaPulsa;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencytext() {
        return currency_text;
    }

    public void setCurrencytext(String currencytext) {
        this.currency_text = currencytext;
    }

    public List<FiturModel> getFitur() {
        return fitur;
    }

    public void setFitur(List<FiturModel> fitur) {
        this.fitur = fitur;
    }

    public List<PromoModel> getSlider() {
        return slider;
    }

    public void setSlider(List<PromoModel> slider) {
        this.slider = slider;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<RatingModel> getRating() {
        return rating;
    }

    public void setRating(List<RatingModel> rating) {
        this.rating = rating;
    }

    public List<BeritaModel> getBerita() {
        return berita;
    }

    public void setBerita(List<BeritaModel> berita) {
        this.berita = berita;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public String getMobilepulsausername() {
        return mobilepulsausername;
    }

    public void setMobilepulsausername(String mobilepulsausername) {
        this.mobilepulsausername = mobilepulsausername;
    }
    public String getMobilepulsaapikey() {
        return mobilepulsaapikey;
    }

    public void setMobilepulsaapikey(String mobilepulsaapikey) {
        this.mobilepulsaapikey = mobilepulsaapikey;
    }
    public String getMpstatus() {
        return mpstatus;
    }

    public void setMpstatus(String mpstatus) {
        this.mpstatus = mpstatus;
    }

    public String getMpactive() {
        return mpactive;
    }

    public void setMpactive(String mpactive) {
        this.mpactive = mpactive;
    }

    public List<MerchantModel> getMerchantpromo() {
        return merchantpromo;
    }

    public void setMerchantpromo(List<MerchantModel> merchantpromo) {
        this.merchantpromo = merchantpromo;
    }

    public List<CatMerchantModel> getCatmerchant() {
        return catmerchant;
    }

    public void setCatmerchant(List<CatMerchantModel> catmerchant) {
        this.catmerchant = catmerchant;
    }

    public List<MerchantNearModel> getMerchantnear() {
        return merchantnear;
    }

    public void setMerchantnear(List<MerchantNearModel> merchantnear) {
        this.merchantnear = merchantnear;
    }

    public List<AllFiturModel> getAllfitur() {
        return allfitur;
    }

    public void setAllfitur(List<AllFiturModel> allfitur) {
        this.allfitur = allfitur;
    }

    public String getAyoPesanApiPassword() {
        return ayoPesanApiPassword;
    }

    public void setAyoPesanApiPassword(String ayoPesanApiPassword) {
        this.ayoPesanApiPassword = ayoPesanApiPassword;
    }

    public String getAyoPesanApiToken() {
        return ayoPesanApiToken;
    }

    public void setAyoPesanApiToken(String ayoPesanApiToken) {
        this.ayoPesanApiToken = ayoPesanApiToken;
    }
}
