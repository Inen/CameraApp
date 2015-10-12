package com.apps.inen.cameraapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 11.10.15.
 */
public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Place> places;

    CollectionPagerAdapter(FragmentManager fm, ArrayList<Place> places)
    {
        super(fm);
        this.places = places;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new DisplayFragment();
        Bundle args = new Bundle();

        Place temp = places.get(position);
        args.putInt("ID", position);
        args.putString("PhotoPath", temp.getPhoto_path());
        args.putString("Address", temp.getAddress());
        args.putString("DateAndTime", temp.getDate() + " at " + temp.getTime());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Place #" + position;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
