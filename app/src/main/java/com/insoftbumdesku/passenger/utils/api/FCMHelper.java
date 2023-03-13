package com.insoftbumdesku.passenger.utils.api;


import com.insoftbumdesku.passenger.json.fcm.FCMMessage;
import com.insoftbumdesku.passenger.utils.BooleanSerializerDeserializer;
import com.insoftbumdesku.passenger.utils.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.insoftbumdesku.passenger.utils.Log.LOG;


public class FCMHelper {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "FCMHelper";
    private static BooleanSerializerDeserializer booleanSerializerDeserializer = new BooleanSerializerDeserializer();
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            .registerTypeAdapter(Boolean.class, booleanSerializerDeserializer)
            .registerTypeAdapter(boolean.class, booleanSerializerDeserializer)
            .create();

    public static Call sendMessage(String serverKey, FCMMessage message) {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (LOG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        OkHttpClient client = builder.build();

        String json = gson.toJson(message);
        Log.e("FCM REQUEST", json);
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "key=" + serverKey)
                .post(body)
                .build();

        return client.newCall(request);
    }
}
