package com.apps.inen.cameraapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by dima on 16.10.15.
 */
class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private Context mContext;
    private int reqWidth = 150;
    private int reqHeight = 150;

    public BitmapWorkerTask(ImageView imageView, Context context) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<>(imageView);
        mContext = context;
        ImageView view = imageViewReference.get();
        if (view.getWidth() > 0 && view.getHeight() > 0) {
            reqWidth = view.getWidth();
            reqHeight = view.getHeight();
        } // TODO: need to get imageView width and height before it was drawn.
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        String mCurrentPhotoPath = params[0];

        if (mCurrentPhotoPath == "ShowCameraImage")
            return decodeSampledBitmapFromResource(mContext.getResources(),
                    R.drawable.camera_image, reqWidth, reqHeight);

        if (mCurrentPhotoPath != null)
            return decodeSampledBitmapFromFile(mCurrentPhotoPath, reqWidth, reqHeight);

        return decodeSampledBitmapFromResource(mContext.getResources(),
                R.drawable.noimage, reqWidth, reqHeight);

    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private Bitmap decodeSampledBitmapFromFile(String mCurrentFilePath,
                                               int targetW, int targetH) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentFilePath, options);

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        return BitmapFactory.decodeFile(mCurrentFilePath, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resID,
                                                         int targetW, int targetH) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resID, options);

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        return BitmapFactory.decodeResource(res, resID, options);
    }


}
