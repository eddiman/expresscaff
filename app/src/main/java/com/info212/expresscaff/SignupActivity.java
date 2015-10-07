package com.info212.expresscaff;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    String usernameText;
    String passwordText;
    String emailText;

    String address;
    String postalCode;

    //brukes for å gjøre om string til int
    String cardDateMonthString;
    String cardDateYearString;
    ////////////////////////////


    String cardType;
    String cardNumber;
    int cardDateMonth;
    int cardDateYear;
    String cardCVC;

    String phoneNumber;


    EditText signupUsername;
    EditText signupPassword;
    EditText signupEmail;

    EditText signupAddress;
    EditText signupPostal;

    Spinner signupCardType;
    EditText signupCardNumber;
    EditText signupCardDateMonth;
    EditText signupCardDateYear;
    EditText signupCardCVC;

    EditText signupPhone;


    
    Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Disse to må være med i hver eneste activty som inflates,
        //setter at activitien setter en custom toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        //setter fargen til statusbaren, er en bug som gjør at det @color/myPrimaryDarkColor ikke funker
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));
        }
        //Setter en backknapp oppe til venstre, kun grafikk se nederst for tilhørende kode
        //forresten; ignorer warningen i android studio
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
///////////////////////////////////////////////////////////////////////

        signupUsername = (EditText) findViewById(R.id.signUpUsername);
        signupPassword = (EditText) findViewById(R.id.signUpPassword);
        signupEmail = (EditText) findViewById(R.id.signUpEmail);

        signupAddress = (EditText) findViewById(R.id.signUpAddress);
        signupPostal = (EditText) findViewById(R.id.signUpPostal);

        signupCardType = (Spinner) findViewById(R.id.signUpCardType);
        signupCardNumber = (EditText) findViewById(R.id.signUpCardNumber);
        signupCardDateMonth = (EditText) findViewById(R.id.signUpCardDateMonth);
        signupCardDateYear = (EditText) findViewById(R.id.signUpCardDateYear);
        signupCardCVC = (EditText) findViewById(R.id.signUpCardCVC);

        signupPhone = (EditText) findViewById(R.id.signUpPhone);
        signupButton = (Button) findViewById(R.id.confirmSignUp);


        // Sign up Button Click Listener
        signupButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernameText = signupUsername.getText().toString();
                passwordText = signupPassword.getText().toString();
                emailText = signupEmail.getText().toString();

                address = signupAddress.getText().toString();
                 postalCode = signupPostal.getText().toString();

                 cardType = signupCardType.getSelectedItem().toString();
                 cardNumber = signupCardNumber.getText().toString();

                 cardDateMonthString = signupCardDateMonth.getText().toString();
                 cardDateYearString = signupCardDateYear.getText().toString();
                 cardCVC = signupCardCVC.getText().toString();

                 phoneNumber = signupPhone.getText().toString();


                if(cardDateMonthString.equals("") || cardDateYearString.equals("") )
                {
                    Toast.makeText(getApplicationContext(), "Please complete the sign up form", Toast.LENGTH_LONG).show();
                }else {
                    cardDateMonth = Integer.parseInt(cardDateMonthString);
                    cardDateYear = Integer.parseInt(cardDateYearString);
                }

                // Force user to fill up the form
                if (usernameText.equals("") || passwordText.equals("") || emailText.equals("") || address.equals("") || postalCode.equals("") || cardNumber.equals("") || cardCVC.equals("") || phoneNumber.equals("")){

                        Toast.makeText(getApplicationContext(),
                                "Please complete the sign up form",
                                Toast.LENGTH_LONG).show();

                }
                else  if(!emailText.contains("@")){
                Toast.makeText(getApplicationContext(),
                        "This is not a valid e-mail address" , Toast.LENGTH_LONG)
                        .show();
             } else if(passwordText.length() < 7){
                Toast.makeText(getApplicationContext(),
                        "Your password is shorter than 8 characters" + passwordText.length(), Toast.LENGTH_LONG)
                        .show();
                }
                    else if(!passwordText.matches(".*\\d.*") || !passwordText.matches(".*[A-Z].*")){
                Toast.makeText(getApplicationContext(),
                        "Your password must contain a number and a capital letter", Toast.LENGTH_LONG)
                        .show();
            } else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameText);
                    user.setPassword(passwordText);
                    user.setEmail(emailText);

                    user.put("address", address);
                    user.put("postal_code", postalCode);

                    user.put("card_type", cardType);
                    user.put("card_number", cardNumber);
                    user.put("carddate_month", cardDateMonth);
                    user.put("carddate_year", cardDateYear);
                    user.put("card_cvc", cardCVC);

                    user.put("phone", phoneNumber);


                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, LoginSignupActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Something went wrong with the sign up, " +
                                                "check if information is correct" , Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });
        
    }

    ///////////////////////////////SLUTT PÅ TING////////////////////////////////^^

    public void onBackPressed() {

        Intent intent = new Intent(SignupActivity.this, LoginSignupActivity.class);
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
