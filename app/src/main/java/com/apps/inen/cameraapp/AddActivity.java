package com.apps.inen.cameraapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 2;
    ImageView imageView;
    private Bitmap icon;

    private static TextView date;
    private static TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageView = (ImageView) findViewById(R.id.photoView);

        date = (TextView) findViewById(R.id.dateView);
        time = (TextView) findViewById(R.id.timeView);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        date.setText(c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR));
        time.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
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

    public void onSetTimeClicked(View view) {
        DialogFragment newFragment = new TimeDialog();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onSetDateClicked(View view)
    {
        DialogFragment newFragment = new DateDialog();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onPhotoClick(View view) {
        if (view.getId() == R.id.photoView) {
            takeAPicture();
        }
    }

    public void takeAPicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            icon = data.getParcelableExtra("data");
            imageView.setImageBitmap(icon);
        }
    }

    private void returnTheResult() {
        Intent intent = new Intent();
        EditText address = (EditText) findViewById(R.id.addressEdit);
        TextView date = (TextView) findViewById(R.id.dateView);
        TextView time = (TextView) findViewById(R.id.timeView);

        if (icon != null)
            intent.putExtra("icon", icon);
        if (!address.getText().toString().isEmpty())
            intent.putExtra("address", address.getText().toString());
        if (!date.getText().toString().isEmpty())
            intent.putExtra("date", date.getText().toString());
        if (!time.getText().toString().isEmpty())
            intent.putExtra("time", time.getText().toString());

        setResult(RESULT_OK, intent);

        finish();
    }

    public static class TimeDialog extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, min,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.setText(hourOfDay + ":" + minute);
        }
    }

    public static class DateDialog extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.setText(dayOfMonth + "." + monthOfYear + "." + year);
        }
    }
}
