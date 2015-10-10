package com.apps.inen.cameraapp;

import android.graphics.Bitmap;

/**
 * Created by dima on 17.09.15.
 */
public class Place {

    // Places table name
    public static final String TABLE_NAME = "places";

    // Places Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_PLACE = "place";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATE = "date";
    public static final String KEY_PHOTO_PATH = "photo_path";

    public static final String[] COLUMNS = {KEY_ID, KEY_PLACE, KEY_TIME, KEY_DATE, KEY_PHOTO_PATH};

    private String photo_path;
    private String address;
    private String date;
    private String time;
    private int id;

    Place() {
    }

    Place(String address, String date, String time, String photo_path) {
        this.photo_path = photo_path;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }


    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public void setPlace(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }
}
