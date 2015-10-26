package com.info212.expresscaff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Edvard on 20.10.2015.
 */
public class ReceiptListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public ReceiptListAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopview_item, parent, false);

        return rowView;

    }

}
