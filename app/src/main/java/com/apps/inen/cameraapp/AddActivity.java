package com.apps.inen.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 2;
    ImageView imageView;
    private static Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageView = (ImageView) findViewById(R.id.photoView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_OK) {
            returnTheResult();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPhotoClick(View view)
    {
        if (view.getId() == R.id.photoView)
        {
            takeAPicture();
        }
    }

    public void takeAPicture()
    {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            icon = data.getParcelableExtra("data");
            imageView.setImageBitmap(icon);
        }
    }

    private void returnTheResult()
    {
        Intent intent = new Intent();
        EditText place = (EditText) findViewById(R.id.placeEdit);
        EditText date = (EditText) findViewById(R.id.dateEdit);
        EditText time = (EditText) findViewById(R.id.timeEdit);

        if(icon != null)
            intent.putExtra("icon", icon);
        if(!place.getText().toString().isEmpty())
            intent.putExtra("place", place.getText().toString());
        if(!date.getText().toString().isEmpty())
            intent.putExtra("date", date.getText().toString());
        if(!time.getText().toString().isEmpty())
            intent.putExtra("time", time.getText().toString());

        setResult(RESULT_OK, intent);

        finish();
    }
}
