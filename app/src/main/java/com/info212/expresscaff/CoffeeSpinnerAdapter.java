package com.info212.expresscaff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Edvard on 04.11.2015.
 */
public class CoffeeSpinnerAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public CoffeeSpinnerAdapter(Context context,String[] values) {

        super(context, R.layout.activity_order, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View row=inflater.inflate(R.layout.spinner_row, parent, false);
    TextView label=(TextView)row.findViewById(R.id.spinnertext);

    ImageView icon=(ImageView)row.findViewById(R.id.icon);
        label.setText(this.values[position]);


        return row;
    }
}
