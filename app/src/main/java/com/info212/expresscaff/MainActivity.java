package com.info212.expresscaff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                // If user is anonymous, send the user to LoginSignupActivity.class
                Intent intent = new Intent(MainActivity.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            } else {
                // If current user is NOT anonymous user
                // Get current user data from Parse.com
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    // Send logged in users to ShopActivity.class
                    Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Send user to LoginSignupActivity.class
                    Intent intent = new Intent(MainActivity.this,
                            LoginSignupActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
