package com.apps.inen.cameraapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
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

    private void returnResult() {
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
            if (pager.getCurrentItem() >= 0 && places.size() > 0) {
                createAlertDialog();
                return true;
            } else {
                returnResult();
            }
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deletePlace()
    {
        Log.d(TAG, "Deleted place #" + pager.getCurrentItem());
        String path = places.get(pager.getCurrentItem()).getPhoto_path();
        if (path != null) {
            File file = new File(path);
            if (file.delete()) {
                Log.d(TAG, "File was deleted");
            } else
                Log.d(TAG, "File was not deleted!");
        }
        db.deletePlace(places.remove(pager.getCurrentItem()));
        adapter.notifyDataSetChanged();
        isDataChanged = true;
    }

    private void createAlertDialog()
    {
        final boolean result = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(DisplayPlaceActivity.this)
                .setTitle(R.string.delete_warning)
                .setIcon(R.drawable.icon_warning)
                .setPositiveButton(R.string.action_OK,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePlace();
                            }
                        })
                .setNegativeButton(R.string.action_Cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
