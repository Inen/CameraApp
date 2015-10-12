package com.apps.inen.cameraapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dima on 11.10.15.
 */
public class DisplayFragment extends Fragment {

    String photoPath;
    Bitmap photo;
    ImageView photoView;
    TextView addressView;
    TextView dateAndTimeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_place, container, false);

        photoView = (ImageView) rootView.findViewById(R.id.photoDisplay);
        addressView = (TextView) rootView.findViewById(R.id.addressDisplay);
        dateAndTimeView = (TextView) rootView.findViewById(R.id.dateAndTimeDisplay);

        Bundle args = getArguments();

        String address = args.getString("Address");
        addressView.setText(address);
        dateAndTimeView.setText(args.getString("DateAndTime"));
        photoPath = args.getString("PhotoPath");



        photo = BitmapFactory.decodeFile(photoPath);
        if (photo == null)
            photo = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);

        photoView.setImageBitmap(photo);

        return rootView;
    }
}
