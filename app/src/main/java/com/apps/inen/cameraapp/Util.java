package com.apps.inen.cameraapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by dima on 24.09.15.
 */
public class Util {
    public static byte[] bitmapToByteArray(Bitmap b) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] bArray) {
        return BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
    }
}
