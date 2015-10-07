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
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgottenActivity extends AppCompatActivity {

    Button forgottenPassButton;
    EditText forgottenEmail;
    Toolbar mToolbar;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten);

        //Disse to må være med i hver eneste activty som inflates,
        //setter at activitien setter en custom toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        //setter fargen til statusbaren, er en bug som gjør at det @color/myPrimaryDarkColor ikke funker
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ////////////////////////////////////////////////////////////////////////////////////////

        forgottenEmail = (EditText) findViewById(R.id.forgottenEmail);
        forgottenPassButton = (Button) findViewById(R.id.forgottenPass);


        forgottenPassButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                email = forgottenEmail.getText().toString();



                ParseUser.requestPasswordResetInBackground(email,
                        new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "An E-mail was sent, check your inbox!",
                                            Toast.LENGTH_LONG).show();
                                            onBackPressed();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Something went wrong, check if E-mail is correct",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    public void onBackPressed() {

        Intent intent = new Intent(ForgottenActivity.this, LoginSignupActivity.class);
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
