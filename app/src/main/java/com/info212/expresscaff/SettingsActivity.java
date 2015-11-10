package com.info212.expresscaff;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    Toolbar mToolbar;

    public static Double radius = 500.0;

    ParseUser currentUser = ParseUser.getCurrentUser();
    String struser = currentUser.getUsername();
    String stremail = currentUser.getEmail();
    String strcard = currentUser.getString("card_number");

    TextView userText;
    TextView userCard;
    TextView userMail;
    TextView sliderText;

    ListView settingThings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

         userText = (TextView) findViewById(R.id.d1_1);
        userCard = (TextView) findViewById(R.id.d1_2);
         userMail = (TextView) findViewById(R.id.d1_3);
        sliderText = (TextView) findViewById(R.id.d2_2);
        settingThings = (ListView) findViewById(R.id.listView);

        SeekBar seekBar = (SeekBar) findViewById(R.id.distSlider);
        String radString = Double.toString(radius);


        userText.setText("Username: " + struser);
        userCard.setText("Card Number: " + strcard);
        userMail.setText("E-mail: " + stremail);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                progress = progresValue;


            }


            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {


            }


            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

                sliderText.setText("Covered: " + progress + "/" + seekBar.getMax());


            }

        });

        settingThings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Send single item click data to SingleItemView Class

                popup_window();

            }
        });


    }

    public void popup_window() {

        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.show();
        dialog.setContentView(R.layout.popup_about);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

        Intent intent = new Intent(SettingsActivity.this, ShopActivity.class);
        startActivity(intent);
        finish();

    }
}
