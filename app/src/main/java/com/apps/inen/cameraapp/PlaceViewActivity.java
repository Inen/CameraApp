package com.apps.inen.cameraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaceViewActivity extends AppCompatActivity {

    ImageView photo;
    TextView address;
    TextView dateAndTime;
    DataBaseOpenHelper db;
    int id;
    Place place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_view);

        photo = (ImageView) findViewById(R.id.photoDisplay);
        address = (TextView) findViewById(R.id.addressDisplay);
        dateAndTime = (TextView) findViewById(R.id.dateAndTimeDisplay);

        id = getIntent().getIntExtra("ID", -1);
        if (id != -1) {
            db = new DataBaseOpenHelper(this);
            ArrayList<Place> list = db.getAllPlaces();
            place = list.get(id);
            photo.setImageBitmap(place.getBitmap());
            address.setText(place.getAddress());
            dateAndTime.setText(place.getDate() + " at " + place.getTime());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_view, menu);
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
            Intent intent = new Intent();
            intent.putExtra("ID", this.id);
            setResult(RESULT_OK, intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
