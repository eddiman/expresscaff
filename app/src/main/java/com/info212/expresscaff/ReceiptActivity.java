package com.info212.expresscaff;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {
    Toolbar mToolbar;



    String receiptShopName;
    String receiptShopAddress;
    String currentDate;
    String expiredDate;
    String coffeeType1;
    int coffeePrice;
    int totalSum;
    int barcode;

    TextView receiptShopNameView;
    TextView receiptShopAddressView;
    TextView receiptValidDateView ;
    TextView receiptValidCounterTimeView;

    TextView receiptCoffeeView ;
    TextView receiptPriceView ;
    TextView receiptSumPriceView;

    ImageButton receiptBarcodeButton;
    TextView receiptBarcodeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        //Disse to må være med i hver eneste activity som inflates,
        //setter at activitien setter en custom toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        //setter fargen til statusbaren, er en bug som gjør at det @color/myPrimaryDarkColor ikke funker
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.faded));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getOrderInfo();

        receiptBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcode_window(barcode);
            }
        });




    }

    public void getOrderInfo(){

        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();

        // Get the name
        receiptShopName = i.getStringExtra("name");
        receiptShopAddress = i.getStringExtra("shopAddress");
        currentDate = i.getStringExtra("currentDate");
        expiredDate = i.getStringExtra("expire_date");
        coffeeType1 = i.getStringExtra("coffee_type1");

        coffeePrice = i.getIntExtra("coffee_price", 0);
        totalSum = i.getIntExtra("total_sum", 0);
        barcode = i.getIntExtra("barcode_nr", 0);



        // Locate the TextView in singleitemview.xml
        receiptShopNameView = (TextView) findViewById(R.id.receiptShopName);
        receiptShopAddressView = (TextView) findViewById(R.id.receiptShopAddress);
        receiptValidDateView = (TextView) findViewById(R.id.receiptvaliddate);
        receiptValidCounterTimeView = (TextView) findViewById(R.id.receiptvalidtime);

        receiptCoffeeView = (TextView) findViewById(R.id.receiptcoffee);
        receiptPriceView = (TextView) findViewById(R.id.receiptprice);
        receiptSumPriceView = (TextView) findViewById(R.id.receiptsumprice);

        receiptBarcodeButton = (ImageButton) findViewById(R.id.barcodeButton);
        receiptBarcodeView = (TextView) findViewById(R.id.receiptbarcodenr);


        // Load the text into the TextView
        receiptShopNameView.setText(receiptShopName);
        receiptShopAddressView.setText(receiptShopAddress);

        receiptValidDateView.setText("Valid until: " + expiredDate);
        receiptValidCounterTimeView.setText("Time left: " + "00:00"); //TODO: Legge til counter som teller i bakgrunnen
        receiptCoffeeView.setText(coffeeType1);
        receiptPriceView.setText("kr " + String.valueOf(coffeePrice) + ",00");
        receiptSumPriceView.setText("kr " + String.valueOf(totalSum) + ",00");


        receiptBarcodeView.setText(String.valueOf(barcode));


    }

    public void barcode_window(int popup_barcodenr){

        final Dialog dialog = new Dialog(ReceiptActivity.this);
        dialog.show();
        dialog.setContentView(R.layout.popup_barcode);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        TextView barcode_popup = (TextView) dialog.findViewById(R.id.barcodetextpop);
        barcode_popup.setText(String.valueOf(popup_barcodenr));
    }

    public void onBackPressed() {
        Intent intent = new Intent(ReceiptActivity.this, ShopActivity.class);
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
