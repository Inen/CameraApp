package com.apps.inen.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private static ArrayList<Place> items = new ArrayList<>();
    private static final int ADD_REQUEST = 1;
    private static final int DELETE_REQUEST = 2;
    private static final String TAG = "MainActivity";
    private DataBaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseOpenHelper(this);
        items = db.getAllPlaces();

        adapter = new ItemAdapter(this, items);

        ListView view = (ListView) findViewById(R.id.listView);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked item with pos:" + position + " id:" + id);
                Intent intent = new Intent(MainActivity.this, DisplayPlaceActivity.class);
                intent.putExtra("ID", position);
                startActivityForResult(intent, DELETE_REQUEST);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent, ADD_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            String address = data.getStringExtra("address");
            if (address == null)
                address = "No address";
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String photoPath = data.getStringExtra("photoPath");

            Place place = new Place(address, date, time, photoPath);
            db.addPlace(place);
            items.add(place);
        } else if (requestCode == DELETE_REQUEST && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("isDataChanged", true))
            {
                items.clear();
                items.addAll(db.getAllPlaces());
            }
        }
        adapter.notifyDataSetChanged();
    }
}
