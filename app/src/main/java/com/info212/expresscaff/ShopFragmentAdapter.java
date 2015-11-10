package com.info212.expresscaff;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edvard on 04.11.2015.
 */
public class ShopFragmentAdapter extends BaseAdapter {
    private final Context context;
    List<ParseObject> ParseObjects;

    public ShopFragmentAdapter(Context context, List<ParseObject> ParseObjects) {
        this.context = context;
        this.ParseObjects = ParseObjects;
    }

    @Override
    public int getCount() {
        return ParseObjects.size();
    }
    public ParseObject getItem(int position) {
        return ParseObjects.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopview_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.shopTextItem);

        switch (position) {
            case 0: //
                textView.setBackgroundResource(R.color.brownOne);

                break;
        }
            return rowView;
        }
    }
