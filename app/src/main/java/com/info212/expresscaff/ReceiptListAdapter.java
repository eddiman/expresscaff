package com.info212.expresscaff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Edvard on 20.10.2015.
 */
public class ReceiptListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Receipt> ReceiptListClass = null;
    private ArrayList<Receipt> arraylist;

    private int[] colors = new int[] { Color.parseColor("#f2ede7"), Color.parseColor("#fffcf8")};

    public ReceiptListAdapter(Context context, List<Receipt> ReceiptListClass) {
    mContext = context;
        this.ReceiptListClass = ReceiptListClass;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Receipt>();
        this.arraylist.addAll(ReceiptListClass);

    }

    public class ViewHolder {
        TextView receiptlistName;
        TextView receiptlistAddress;
        TextView receiptlistDate;
        TextView receiptlistExpire;
    }
    @Override
    public int getCount() {
        return ReceiptListClass.size();
    }
    public Receipt getItem(int position) {
        return ReceiptListClass.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.receiptview_item, null);

            holder.receiptlistName = (TextView) view.findViewById(R.id.receiptlistname);
            holder.receiptlistAddress = (TextView) view.findViewById(R.id.receiptlistaddress);
            holder.receiptlistDate = (TextView) view.findViewById(R.id.receiptlistdate);
            holder.receiptlistExpire = (TextView) view.findViewById(R.id.receiptlistexpire);
            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.receiptlistName.setText(ReceiptListClass.get(position).getNameShop());
        holder.receiptlistAddress.setText(ReceiptListClass.get(position).getAddress());
        holder.receiptlistExpire.setText(ReceiptListClass.get(position).getExpire());
        holder.receiptlistDate.setText(ReceiptListClass.get(position).getDate());


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, ReceiptActivity.class);
                // Pass all data rank
                intent.putExtra("name", (ReceiptListClass.get(position).getNameShop()));
                intent.putExtra("shopAddress", (ReceiptListClass.get(position).getAddress()));
                intent.putExtra("currentDate", (ReceiptListClass.get(position).getDate()));
                intent.putExtra("coffee_type1", (ReceiptListClass.get(position).getCoffeeType1()));
                intent.putExtra("coffee_price", (ReceiptListClass.get(position).getCoffeePrice()));
                intent.putExtra("total_sum", (ReceiptListClass.get(position).getTotalSum()));
                intent.putExtra("barcode_nr", ReceiptListClass.get(position).getBarcode());
                intent.putExtra("expire_date", ReceiptListClass.get(position).getExpireDate());
                mContext.startActivity(intent);
            }
        });


/*
        receiptShopName = i.getStringExtra("name");
        receiptShopAddress = i.getStringExtra("shopAddress");
        currentDate = i.getStringExtra("currentDate");
        coffeeType1 = i.getStringExtra("coffee_type1");

        coffeePrice = i.getIntExtra("coffee_price", 0);
        totalSum = i.getIntExtra("total_sum", 0);
        barcode = i.getIntExtra("barcode", 0);
        */

        return view;

    }

}
