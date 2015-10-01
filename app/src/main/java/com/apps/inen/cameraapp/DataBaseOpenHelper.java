package com.apps.inen.cameraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dima on 24.09.15.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper{

    private static int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "CameraAPP";


    DataBaseOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACES_TABLE = "CREATE TABLE " + Place.TABLE_NAME + " ( " +
                Place.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Place.KEY_PLACE + " TEXT, " +
                Place.KEY_TIME + " TEXT, " +
                Place.KEY_DATE + " TEXT, " +
                Place.KEY_IMAGE + " BLOB )";

        // create table
        db.execSQL(CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
        // create fresh table
        this.onCreate(db);
    }

    public void addPlace(Place place) {
        Log.d("addPlace", place.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(place.KEY_PLACE, place.getAddress());
        values.put(place.KEY_TIME, place.getTime());
        values.put(place.KEY_DATE, place.getDate());
        values.put(place.KEY_IMAGE, Util.bitmapToByteArray(place.getBitmap()));

        // 3. insert
        db.insert(place.TABLE_NAME, null, values);

        // 4. close
        db.close();
    }

    public Place getPlace(int id) {
//        // 1. get reference to readable DB
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // 2. build query
//        Cursor cursor = db.query(Place.TABLE_NAME, // a. table
//                Place.COLUMNS, // b. column names
//                " id = ?", // c. selections
//                new String[]{String.valueOf(id)}, // d. selections args
//                null, // e. group by - how to group rows
//                null, // f. having - which row groups to include (filter)
//                null, // g. order by
//                null); // h. limit
//
//        // 3. if we got results get the first one
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // 4. build place object
//        Place place = new Place();
//        place.setId(Integer.parseInt(cursor.getString(0)));
//        place.setPlace(cursor.getString(1));
//        place.setTime(cursor.getString(2));
//        place.setDate(cursor.getString(3));
//        place.setBitmap(Util.byteArrayToBitmap(cursor.getBlob(4)));
//
//        Log.d("getPlace(" + id + ")", place.toString());
//        return place;

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(Place.TABLE_NAME, // a. table
                Place.COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by - how to group rows
                null, // f. having - which row groups to include (filter)
                null, // g. order by
                null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build ad object
        Place place = new Place();
        place.setId(Integer.parseInt(cursor.getString(0)));
        place.setPlace(cursor.getString(1));
        place.setTime(cursor.getString(2));
        place.setDate(cursor.getString(3));
        place.setBitmap(Util.byteArrayToBitmap(cursor.getBlob(4)));

        Log.d("getAd(" + id + ")", place.toString());
        return place;
    }

    public ArrayList<Place> getAllPlaces() {
        ArrayList<Place> places = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + Place.TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build ad and add it to list
        Place place;
        if (cursor.moveToFirst()) {
            do {
                place = new Place();
                place.setId(Integer.parseInt(cursor.getString(0)));
                place.setPlace(cursor.getString(1));
                place.setTime(cursor.getString(2));
                place.setDate(cursor.getString(3));
                place.setBitmap(Util.byteArrayToBitmap(cursor.getBlob(4)));
                places.add(place);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPlaces()", places.toString());
        return places;
    }

    public int updatePlace(Place place) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(place.KEY_PLACE, place.getAddress());
        values.put(place.KEY_TIME, place.getTime());
        values.put(place.KEY_DATE, place.getDate());
        values.put(place.KEY_IMAGE, Util.bitmapToByteArray(place.getBitmap()));

        // 3. updating row
        int i = db.update(place.TABLE_NAME, values, place.KEY_ID + " = ?",
                new String[]{String.valueOf(place.getId())});

        // 4. close
        db.close();

        Log.d("updatePlace", place.toString());
        return i;
    }

    public void deletePlace(Place place) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(Place.TABLE_NAME, Place.KEY_ID + " = ?", new String[]{String.valueOf(place.getId())});

        // 3. close
        db.close();

        Log.d("deletePlace", place.toString());
    }
}
