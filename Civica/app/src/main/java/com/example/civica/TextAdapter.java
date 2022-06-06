package com.example.civica;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter {

    private Context context;
    private String[] itemsName;

    public TextAdapter(Context context, String[] itemsName) {
        this.context = context;
        this.itemsName = itemsName;
    }

    @Override
    public int getCount() {
        return itemsName.length;
    }

    @Override
    public Object getItem(int position) {
        return itemsName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(this.context);

        text.setText(itemsName[position]);

        text.setGravity(Gravity.CENTER);
        text.setTextSize(30);
        text.setBackgroundColor(Color.parseColor("#fbdcbb"));

        text.setBackgroundResource(R.drawable.grid_border);

        return text;
    }
}