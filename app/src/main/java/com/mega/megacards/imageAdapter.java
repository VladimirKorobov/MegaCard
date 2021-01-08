package com.mega.megacards;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class imageAdapter extends BaseAdapter {
    Context context;
    ArrayList<MainActivity.thumbHolder> arrayList;
    public imageAdapter(Context context, ArrayList<MainActivity.thumbHolder> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_list, parent, false);
        }
        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.image);
        imageView.getLayoutParams().height = 300;
        //imageView.setBackgroundColor(Color.WHITE);
        //imageView.setImageResource(arrayList.get(position).getmThumbIds());
        imageView.setImageBitmap(arrayList.get(position).thumb);
        return convertView;
    }
}
