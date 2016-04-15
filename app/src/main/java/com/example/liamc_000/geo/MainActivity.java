package com.example.liamc_000.geo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends Activity {


    Button login, register;

    EditText eb, pb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);



        login= (Button) findViewById(R.id.btnLogin);
        register=(Button) findViewById(R.id.btnLinkToRegisterScreen);

        eb= (EditText) findViewById(R.id.email);
        pb= (EditText) findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {        //on click listener method for login button

            @Override
            public void onClick(View v) {


                String getInput = eb.getText().toString();            //assigning the inout from the text buttons
                String getInput2 = pb.getText().toString();        //to a string


                ParseUser.logInInBackground(getInput, getInput2,
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to next screen
                                    Intent intent = new Intent(
                                            MainActivity.this,
                                            content.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exists, please register",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }


        });

        register.setOnClickListener(new View.OnClickListener() {         //open the registration page

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent (v.getContext(),register.class);       //intent to start the registration page
                startActivityForResult(intent,0);
            }
        });


    }
}

