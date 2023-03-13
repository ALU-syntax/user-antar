package com.insoftbumdesku.passenger.models;

import java.io.Serializable;

import static com.insoftbumdesku.passenger.json.fcm.FCMType.OTHER;

/**
 * Created by Ourdevelops Team on 19/10/2019.
 */
public class Notif implements Serializable {
    public int type = OTHER;
    public String title;
    public String message;
}
