package com.apps.inen.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private static ArrayList<ListViewItem> items = new ArrayList<>();
    private static final int ADD_REQUEST = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ItemAdapter(this, items);

        ListView view = (ListView) findViewById(R.id.listView);
        view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            String place = data.getStringExtra("place");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            Bitmap bmp = null;
            try {
                bmp = data.getParcelableExtra("icon");
            } catch (NullPointerException e) {
                Log.e(TAG, "bytearray is null");
            }

            if (bmp == null)
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);

            adapter.add(new ListViewItem(bmp, place, date, time));
        }
    }
}
