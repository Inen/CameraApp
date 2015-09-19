package com.apps.inen.cameraapp;

import android.graphics.Bitmap;

/**
 * Created by dima on 17.09.15.
 */
public class ListViewItem {

    private Bitmap bitmap;
    private String place;
    private String date;
    private String time;



    ListViewItem(Bitmap bitmap, String place, String date, String time)
    {
        this.bitmap = bitmap;
        this.place = place;
        this.date = date;
        this.time = time;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public String getPlace() {
        return place;
    }
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
