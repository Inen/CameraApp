package com.apps.inen.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dima on 17.09.15.
 */
public class ItemAdapter extends ArrayAdapter<Place> {
    private final Context context;
    private final ArrayList<Place> listViewItems;

    public ItemAdapter(Context context, ArrayList<Place> listViewItem) {
        super(context, R.layout.item_list_layout, listViewItem);
        this.context = context;
        this.listViewItems = listViewItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_list_layout, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listImageView);
        TextView textView = (TextView) rowView.findViewById(R.id.listTextView);

        String photoPath = listViewItems.get(position).getPhoto_path();

        BitmapWorkerTask task = new BitmapWorkerTask(imageView, getContext());
        task.execute(photoPath);

        textView.setText(listViewItems.get(position).getAddress() + "\n" +
                listViewItems.get(position).getDate() + " at " + listViewItems.get(position).getTime());

        return rowView;
    }

}
