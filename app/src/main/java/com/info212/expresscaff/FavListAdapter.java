package com.info212.expresscaff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edvard on 20.10.2015.
 */
public class FavListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Favorite> FavListClass = null;
    private ArrayList<Favorite> arraylist;

    private int[] colors = new int[] { Color.parseColor("#f2ede7"), Color.parseColor("#fffcf8")};

    public FavListAdapter(Context context, List<Favorite> FavListClass) {
    mContext = context;
        this.FavListClass = FavListClass;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Favorite>();
        this.arraylist.addAll(FavListClass);

    }

    public class ViewHolder {
        TextView favlistName;
        TextView favlistAddress;

    }
    @Override
    public int getCount() {
        return FavListClass.size();
    }
    public Favorite getItem(int position) {
        return FavListClass.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.favview_item, null);

            holder.favlistName = (TextView) view.findViewById(R.id.favlistname);
            holder.favlistAddress = (TextView) view.findViewById(R.id.favlistaddress);

            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.favlistName.setText(FavListClass.get(position).getNameShop());
        holder.favlistAddress.setText(FavListClass.get(position).getAddress());


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, OrderActivity.class);
                // Pass all data rank
                intent.putExtra("name", (FavListClass.get(position).getNameShop()));
                intent.putExtra("shopAddress", (FavListClass.get(position).getAddress()));
                intent.putExtra("latitude", (FavListClass.get(position).getLatitude()));
                intent.putExtra("longitude", (FavListClass.get(position).getLongitude()));

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
