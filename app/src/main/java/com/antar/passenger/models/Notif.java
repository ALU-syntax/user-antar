package com.antar.passenger.models;

import java.io.Serializable;

import static com.antar.passenger.json.fcm.FCMType.OTHER;

/**
 * Created by Ourdevelops Team on 19/10/2019.
 */
public class Notif implements Serializable {
    public int type = OTHER;
    public String title;
    public String message;
}
