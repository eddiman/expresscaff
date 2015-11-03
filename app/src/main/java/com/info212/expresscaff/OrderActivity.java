package com.info212.expresscaff;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    TextView shopname;
    TextView shopaddress;
    String shopName;
    String shopAddress;
    Double latitude;
    Double longitude;
    String struser;
    String card;
    String cardNumber;
    int cardMonth;
    int cardyear;
    String coffee_type1;

    String expireDateString;
    Date expireTime;
    String expireDate;

    int priceSum;
    int barcode;
    ProgressDialog progdialog;
    String currentDate;

    Spinner coffeeSpinner;
    TextView sumCost;
    ImageButton addOrder;
    Button payOrderButton;

    int sum = 0;

    MapView shopMapView;


    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //Disse to må være med i hver eneste activity som inflates,
        //setter at activitien setter en custom toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ////////////////////////////////////////////////////////////////////////////////////////
        getShopInfo();

        shopMapView = (MapView) findViewById(R.id.shopmapview);

        shopMapView.onCreate(savedInstanceState);
        shopMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                shopMapView.onResume();
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.setMyLocationEnabled(true);

                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(shopName)
                        .snippet(shopAddress)
                        .icon((BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                );

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15);
                googleMap.moveCamera(cameraUpdate);

                marker.showInfoWindow();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.orderfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to your favorites!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        progdialog = new ProgressDialog(OrderActivity.this);

        //////////////////////////////////////Order part//////

        coffeeSpinner = (Spinner) findViewById(R.id.orderselect);
        sumCost = (TextView) findViewById(R.id.orderprice);
        addOrder = (ImageButton) findViewById(R.id.orderadd);
        payOrderButton = (Button) findViewById(R.id.orderconfirm);


        ParseUser currentUser = ParseUser.getCurrentUser();
        struser = currentUser.getUsername();
        card = currentUser.getString("card_type");
        cardNumber = currentUser.getString("card_number");
        cardMonth = currentUser.getInt("card_month");
        cardyear = currentUser.getInt("card_year");

        // name; //shopname
        // shopAddress; //shopaddress
        //expired;





        setCoffeePrice();

        payOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coffee_type1 = coffeeSpinner.getSelectedItem().toString();
                payment_window(coffee_type1, shopName, sum, card, cardNumber);
            }
        });




    }

public void getShopInfo(){

    // Retrieve data from MainActivity on item click event
    Intent i = getIntent();

    // Get the name
    shopName = i.getStringExtra("name");
    shopAddress = i.getStringExtra("shopAddress");
    latitude = i.getDoubleExtra("latitude", 0);
    longitude = i.getDoubleExtra("longitude", 0);
    Random rand = new Random();
    barcode = rand.nextInt(999999 - 100000 + 1) + 100000;

   // Locate the TextView in singleitemview.xml
    shopname = (TextView) findViewById(R.id.shopName);
    shopaddress = (TextView) findViewById(R.id.shopAddress);


    // Load the text into the TextView
    shopname.setText(shopName);
    shopaddress.setText(shopAddress);


}

    public void setCoffeePrice(){

coffeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {

            case 0:
                sum = 27;
                break;
            case 1:
                sum = 32;
                break;
            case 2:
                sum = 18;
                break;
            case 3:
                sum = 35;
                break;
            case 4:
                sum = 35;
                break;
            default:
                sum = 0;
                break;
        }
        priceSum = sum; //sets the sum of entire purchase, TODO: when multiple purchases are available; add new int variabale

        sumCost.setText("kr " + String.valueOf(sum) + ",00");
        payOrderButton.setText("kr " + String.valueOf(sum) + ",00");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});


    }


    public void payment_window(final String popup_coffee, final String popup_name, final  int popup_price,
                                String popup_cardType, String popup_cardNumber){

        final Dialog dialog = new Dialog(OrderActivity.this);
        dialog.show();
        dialog.setContentView(R.layout.popup_payment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        TextView paymentShopName = (TextView) dialog.findViewById(R.id.paymentshopname);
        TextView paymentAddress = (TextView) dialog.findViewById(R.id.paymentaddress);
        TextView paymentPrice = (TextView) dialog.findViewById(R.id.paymentprice);
       TextView paymentCardInfo = (TextView) dialog.findViewById(R.id.cardinfo);

        Button paymentButton = (Button) dialog.findViewById(R.id.paybutton);

        //TODO: Make some options
        ImageButton paymentOptions = (ImageButton) dialog.findViewById(R.id.paymentoptions);

        paymentShopName.setText(popup_coffee);
        paymentAddress.setText(popup_name);
        paymentCardInfo.setText(popup_cardType + "-" + popup_cardNumber.substring(popup_cardNumber.length() - 4));
         paymentPrice.setText("kr " + String.valueOf(popup_price) + ",00");

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new writeToReceiptTask().execute();

            }
        });



    }
    public void onBackPressed() {

        Intent intent = new Intent(OrderActivity.this, ShopActivity.class);
        startActivity(intent);
        finish();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;


            default:
                return false;

        }}

    private class writeToReceiptTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(OrderActivity.this);





        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage("Transaction in progress...");
            //show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            currentDate = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

            //////////Add two hours to time
            expireDateString = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

            final long millisToAdd = 7_200_000; //two hours

           DateFormat formatExpireDate = new SimpleDateFormat("dd/MM-yyyy HH:mm");
            try {
                expireTime = formatExpireDate.parse(expireDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            expireTime.setTime(expireTime.getTime() + millisToAdd);


            expireDate = formatExpireDate.format(expireTime);

            /////////////////////////////////////////////

            ParseObject receipt = new ParseObject("receipt");

            receipt.put("username", struser );
            receipt.put("card_number", cardNumber );
            receipt.put("card_type", card );
            receipt.put("card_month", cardMonth);
            receipt.put("card_year", cardyear );
            receipt.put("sum_cost", priceSum);
            receipt.put("shop_name", shopName);
            receipt.put("shop_address", shopAddress);
            receipt.put("coffee_type1", coffee_type1);
            receipt.put("barcode_nr", barcode);
            receipt.put("bought_at", currentDate);
            receipt.put("expire_date", expireDate);
            Log.d("Things", struser + cardNumber + card + cardNumber + priceSum + shopName +
                    shopAddress + coffee_type1 + expireDateString);
            receipt.saveInBackground();





            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //hide the dialog
            asyncDialog.dismiss();
            Intent i = new Intent(OrderActivity.this, ReceiptActivity.class);

            i.putExtra("name", shopName);
            i.putExtra("shopAddress", shopAddress);
            i.putExtra("coffee_type1",coffee_type1);
            i.putExtra("currentDate", currentDate);
            i.putExtra("expire_date", expireDate);
            i.putExtra("coffee_price", sum);
            i.putExtra("total_sum", priceSum);
            i.putExtra("barcode_nr", barcode);



            startActivity(i);
            super.onPostExecute(result);
        }

    }


}
