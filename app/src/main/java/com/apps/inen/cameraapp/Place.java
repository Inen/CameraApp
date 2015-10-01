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
    public static final String KEY_IMAGE = "image";

    public static final String[] COLUMNS = {KEY_ID, KEY_PLACE, KEY_TIME, KEY_DATE, KEY_IMAGE};


    private Bitmap bitmap;
    private String address;
    private String date;
    private String time;
    private int id;

    Place()
    {
    }

    Place(Bitmap bitmap, String address, String date, String time)
    {
        this.bitmap = bitmap;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
