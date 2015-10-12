package com.apps.inen.cameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class DisplayPlaceActivity extends AppCompatActivity {

    static final String TAG = "DisplayPlaceActivity";
    CollectionPagerAdapter adapter;
    ViewPager pager;
    ArrayList<Place> places;
    int id;
    DataBaseOpenHelper db;
    boolean isDataChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_place);

        id = getIntent().getIntExtra("ID", -1);
        if (id != -1) {
            db = new DataBaseOpenHelper(this);
            places = db.getAllPlaces();

            adapter = new CollectionPagerAdapter(getSupportFragmentManager(), places);
            pager = (ViewPager) findViewById(R.id.displayPager);
            pager.setAdapter(adapter);
            pager.setCurrentItem(id);
            pager.setOffscreenPageLimit(3);
        }

    }

    @Override
    public void onBackPressed() {
        returnResult();
        super.onBackPressed();
    }

    private void returnResult()
    {
        Intent intent = new Intent();
        intent.putExtra("isDataChanged", isDataChanged);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            if(pager.getCurrentItem() >= 0 && places.size() > 0) {
                Log.d(TAG, "Deleted place #" + pager.getCurrentItem());
                db.deletePlace(places.remove(pager.getCurrentItem()));
                adapter.notifyDataSetChanged();
                isDataChanged = true;
                return true;
            } else
            {
                returnResult();
            }
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
