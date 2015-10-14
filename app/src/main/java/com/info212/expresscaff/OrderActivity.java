package com.info212.expresscaff;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;

public class OrderActivity extends AppCompatActivity {

    TextView shopname;
    TextView shopaddress;
    String name;
    String address;
    Double latitude;
    Double longitude;



    Spinner coffeeSpinner;
    TextView sumCost;
    ImageButton addOrder;
    Button payOrderButton;

    int sum;

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
        //setter fargen til statusbaren, er en bug som gjør at det @color/myPrimaryDarkColor ikke funker
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.faded));
        }*/

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
                            .title(name)
                            .snippet(address));

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15);
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


        //////////////////////////////////////Order part//////

        coffeeSpinner = (Spinner) findViewById(R.id.orderselect);
        sumCost = (TextView) findViewById(R.id.orderprice);
        addOrder = (ImageButton) findViewById(R.id.orderadd);
        payOrderButton = (Button) findViewById(R.id.orderconfirm);


        ParseUser currentUser = ParseUser.getCurrentUser();
        String struser = currentUser.getUsername();







    }
public void getShopInfo(){

    // Retrieve data from MainActivity on item click event
    Intent i = getIntent();

    // Get the name
    name = i.getStringExtra("name");
    address = i.getStringExtra("address");
    latitude = i.getDoubleExtra("latitude", 0);
    longitude = i.getDoubleExtra("longitude", 0);

    /*latitude = Double.parseDouble(stringLatitude);
    longitude = Double.parseDouble(stringLongitude);*/
   // Locate the TextView in singleitemview.xml
    shopname = (TextView) findViewById(R.id.shopName);
    shopaddress = (TextView) findViewById(R.id.shopAddress);


    // Load the text into the TextView
    shopname.setText(name);
    shopaddress.setText(address);


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

}
