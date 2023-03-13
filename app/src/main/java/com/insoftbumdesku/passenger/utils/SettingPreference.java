package com.insoftbumdesku.passenger.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.insoftbumdesku.passenger.constants.Constants;
import com.insoftbumdesku.passenger.json.MobilePulsaHealthBPJSResponseModel;
import com.insoftbumdesku.passenger.models.TopUpPlnHistoryModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SettingPreference {

    private static String CURRENCY = "Rp";
    private static String ABOUTUS = "ABOUTUS";
    private static String EMAIL = "EMAIL";
    private static String PHONE = "PHONE";
    private static String WEBSITE = "WEBSITE";
    private static String MPSTATUS = "MPSTATUS";
    private static String MPACTIVE = "MPACTIVE";
    private static String MOBILEPULSAUSERNAME = "MOBILEPULSAUSERNAME";
    private static String MOBILEPULSAAPIKEY = "MOBILEPULSAAPIKEY";
    private static String CURRENCYTEXT = "CURRENCYTEXT";
    private static String PLN_LIST_PAID_SUCCESS ="SUCCESSPAIDLIST";
    private static String BPJS_LIST_PAID_SUCCESS = "SUCCESSBPJS";
    private static String HARGAPULSA = "HARGAPULSA";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updateCurrency(String string) {
        editor = pref.edit();
        editor.putString(CURRENCY, string);
        editor.commit();
    }

    public void updateabout(String string) {
        editor = pref.edit();
        editor.putString(ABOUTUS, string);
        editor.commit();
    }

    public void updateemail(String string) {
        editor = pref.edit();
        editor.putString(EMAIL, string);
        editor.commit();
    }

    public void updatephone(String string) {
        editor = pref.edit();
        editor.putString(PHONE, string);
        editor.commit();
    }

    public void updateweb(String string) {
        editor = pref.edit();
        editor.putString(WEBSITE, string);
        editor.commit();
    }

    public void updatempstatus(String string) {
        editor = pref.edit();
        editor.putString(MPSTATUS, string);
        editor.commit();
    }

    public void updatempactive(String string) {
        editor = pref.edit();
        editor.putString(MPACTIVE, string);
        editor.commit();
    }

    public void updateMobilepulsausername(String string) {
        editor = pref.edit();
        editor.putString(MOBILEPULSAUSERNAME, string);
        editor.apply();
    }

    public void updateMobilepulsaapikey(String string) {
        editor = pref.edit();
        editor.putString(MOBILEPULSAAPIKEY, string);
        editor.apply();
    }

    public void updatecurrencytext(String string) {
        editor = pref.edit();
        editor.putString(CURRENCYTEXT, string);
        editor.commit();
    }

    public void updatehargapulsa(String value) {
        editor = pref.edit();
        editor.putString(HARGAPULSA, value);
        editor.apply();
    }

    public void updatePlnList(TopUpPlnHistoryModel model) {
        editor = pref.edit();
        List<TopUpPlnHistoryModel> listModel = new ArrayList<>();
        String previousStringModel = pref.getString(PLN_LIST_PAID_SUCCESS,"");
        if (previousStringModel != null && !previousStringModel.isEmpty()) {
            Type listOfMyClassObject = new TypeToken<ArrayList<TopUpPlnHistoryModel>>() {}.getType();
            listModel.addAll(new Gson().fromJson(previousStringModel, listOfMyClassObject));
        }
        listModel.add(0,model);
        editor.putString(PLN_LIST_PAID_SUCCESS, new Gson().toJson(listModel));
        editor.apply();
    }

    public void updateBPJSList(MobilePulsaHealthBPJSResponseModel model) {
        editor = pref.edit();
        List<MobilePulsaHealthBPJSResponseModel> listModel = new ArrayList<>();
        String previousStringModel = pref.getString(BPJS_LIST_PAID_SUCCESS,"");
        if (!previousStringModel.isEmpty()) {
            Type listOfMyClassObject = new TypeToken<ArrayList<MobilePulsaHealthBPJSResponseModel>>() {}.getType();
            listModel.addAll(new Gson().fromJson(previousStringModel, listOfMyClassObject));
        }
        listModel.add(0,model);
        String s = new Gson().toJson(listModel);
        editor.putString(BPJS_LIST_PAID_SUCCESS, new Gson().toJson(listModel));
        editor.apply();
    }

    public String[] getSetting() {

        String[] settingan = new String[15];
        settingan[0] = pref.getString(CURRENCY, "$");
        settingan[1] = pref.getString(ABOUTUS, "");
        settingan[2] = pref.getString(EMAIL, "");
        settingan[3] = pref.getString(PHONE, "");
        settingan[4] = pref.getString(WEBSITE, "");
        settingan[5] = pref.getString(MPSTATUS, "1");
        settingan[6] = pref.getString(MPACTIVE, "0");
        settingan[7] = pref.getString(MOBILEPULSAUSERNAME, "123");
        settingan[8] = pref.getString(MOBILEPULSAAPIKEY, "123");
        settingan[9] = pref.getString(CURRENCYTEXT, "USD");
        settingan[10] = pref.getString(PLN_LIST_PAID_SUCCESS, "");
        settingan[11] = pref.getString(BPJS_LIST_PAID_SUCCESS, "");
        settingan[12] = pref.getString(HARGAPULSA, "");
        return settingan;
    }
}