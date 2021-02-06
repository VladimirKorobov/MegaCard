package com.mega.megacards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CardEditAdapter extends BaseAdapter {
    static class EditorItem {
        public String tab;
        int color;
    }
    Context context;;
    ArrayList<EditorItem> arrayList;
    public CardEditAdapter(Context context, ArrayList<EditorItem> arrayList) {
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
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        float height = ((Activity)context).getWindow().getDecorView().getHeight();
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(arrayList.get(position).color);
        tv.setText(arrayList.get(position).tab);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 30.0f);
        tv.setPadding(50, 0, 5, 2);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Generate ListView Item using TextView
        return tv;
    }
}
