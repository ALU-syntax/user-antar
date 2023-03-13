package com.insoftbumdesku.passenger.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.insoftbumdesku.passenger.constants.Constants;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PhoneProviderHelper {

    public static void addOperator(SharedPreferences preferences) {
        if (preferences == null) return;
        String defValue = new Gson().toJson(new HashMap<String, String>());
        String json = preferences.getString(Constants.OPERATOR, defValue);
        if (json != null && !json.isEmpty() && !json.equalsIgnoreCase("{}")) return;
        Map<String, String> map = new HashMap<>();
        map.put("0814", "indosat");
        map.put("0815", "indosat");
        map.put("0816", "indosat");
        map.put("0855", "indosat");
        map.put("0856", "indosat");
        map.put("0857", "indosat");
        map.put("0858", "indosat");
        map.put("0817", "xl");
        map.put("0818", "xl");
        map.put("0819", "xl");
        map.put("0859", "xl");
        map.put("0878", "xl");
        map.put("0877", "xl");
        map.put("0838", "axis");
        map.put("0837", "axis");
        map.put("0831", "axis");
        map.put("0832", "axis");
        map.put("0812", "telkomsel");
        map.put("0813", "telkomsel");
        map.put("0852", "telkomsel");
        map.put("0853", "telkomsel");
        map.put("0821", "telkomsel");
        map.put("0823", "telkomsel");
        map.put("0822", "telkomsel");
        map.put("0851", "telkomsel");
        map.put("0881", "smart");
        map.put("0882", "smart");
        map.put("0883", "smart");
        map.put("0884", "smart");
        map.put("0885", "smart");
        map.put("0886", "smart");
        map.put("0887", "smart");
        map.put("0888", "smart");
        map.put("0896", "three");
        map.put("0897", "three");
        map.put("0898", "three");
        map.put("0899", "three");
        map.put("0895", "three");

        String mapToJson = new Gson().toJson(map);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.OPERATOR, mapToJson);
        editor.apply();
    }

    @SuppressWarnings("UnstableApiUsage")
    public static String checkPhoneProvider(String phoneNumber, Context context) {
        if (context == null) return "";
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE);
        String defValue = new Gson().toJson(new HashMap<String, String>());
        String json = prefs.getString(Constants.OPERATOR, defValue);
        TypeToken<HashMap<String,String>> token = new TypeToken<HashMap<String,String>>() {};

        HashMap<String,String> retrievedMap=new Gson().fromJson(json,token.getType());
        if (retrievedMap != null) {
            if (retrievedMap.containsKey(phoneNumber)) {
                return retrievedMap.get(phoneNumber);
            }
        }
        return "";
    }

}
