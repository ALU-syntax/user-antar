package com.antar.passenger.constants;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.RectangularBounds;

import java.text.SimpleDateFormat;
import java.util.Locale;




public class Constants {

    public static final RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-3.6170744895761273, 0),
            new LatLng(0, 128.24434470185543));

//    private static final String BASE_URL = "https://motest.m-ojek.com/";
    private static final String BASE_URL = "https://antar.shop/";
//    public static final String FCM_KEY = FCM1+FCM2+FCM3+FCM4;
    public static final String CONNECTION = BASE_URL + "api/";
    public static final String IMAGESFITUR = BASE_URL + "images/fitur/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESITEM = BASE_URL + "images/itemmerchant/";
    public static final String IMAGESBERITA = BASE_URL + "images/berita/";
    public static final String IMAGESSLIDER = BASE_URL + "images/promo/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESUSER = BASE_URL + "images/pelanggan/";
    public static final int REJECT = 0;
    public static final int ACCEPT = 2;
    public static final int CANCEL = 5;
    public static final int START = 3;
    public static final int FINISH = 4;
    public static final int TERIMA1 = 11;
    public static final int TERIMA2 = 12;
    public static final int TERIMA3 = 13;
    public static final int TERIMA4 = 14;
    public static final int TERIMA5 = 15;
    public static String CURRENCY = "";
    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;

    public static String TOKEN = "token";

    public static String USERID = "uid";

    public static String PREF_NAME = "pref_name";

    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;

    public static SimpleDateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static String versionname = "1.0";

    public static String OPERATOR = "OPERATOR_LIST";
    public static String MOBILEPULSA_PRODUCTION_URL = "https://api.mobilepulsa.net/v1/legacy/index";


}