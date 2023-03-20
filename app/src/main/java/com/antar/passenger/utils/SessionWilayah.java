package com.antar.passenger.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionWilayah {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "WILAYAH";

    public static final String IDWILAYAH = "IDWILAYAH";
    public static final String NAMAWILAYAH = "NAMAWILAYAH";

    public SessionWilayah(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String namacabang) {
        editor.putString(IDWILAYAH, id);
        editor.putString(NAMAWILAYAH, namacabang);
        editor.apply();
    }

    public HashMap<String, String> getSessionData() {
        HashMap<String, String> wilayah = new HashMap<>();
        wilayah.put(IDWILAYAH, sharedPreferences.getString(IDWILAYAH, null));
        wilayah.put(NAMAWILAYAH, sharedPreferences.getString(NAMAWILAYAH, null));
        return wilayah;
    }

}

