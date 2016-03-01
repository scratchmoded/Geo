package com.example.liamc_000.geo;

/**
 * Created by liamc_000 on 03/02/2016.
 */

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends Activity {


    Button reg2;
    EditText fn, sn, email, password;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);                //on create method
        setContentView(R.layout.content_register);

        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this);


        reg2= (Button) findViewById(R.id.reg2);


        fn= (EditText) findViewById(R.id.firstnamereg);       //assigning ids to the variables
        sn= (EditText) findViewById(R.id.surnamereg);
        email= (EditText) findViewById(R.id.emailreg);
        password= (EditText) findViewById(R.id.passwordreg);


        reg2.setOnClickListener(new View.OnClickListener() {


            public void onClick(final View v) {


                String getInput = fn.getText().toString();       //assigning the text input to the various strings
                String getInput2 = sn.getText().toString();
                String getInput3 = email.getText().toString();
                String getInput4 = password.getText().toString();

                if (getInput == null || getInput.trim().equals("")){

                    Toast.makeText(getBaseContext(), "Please enter Name", Toast.LENGTH_LONG).show();   //if text input is blank a toast is displayed
                }

                else if(getInput2 == null || getInput2.trim().equals("")){

                    Toast.makeText(getBaseContext(), "Please enter Email", Toast.LENGTH_LONG).show();
                }

                else if(getInput3 == null || getInput3.trim().equals("")){

                    Toast.makeText(getBaseContext(), "Please enter email", Toast.LENGTH_LONG).show();
                }

                else if(getInput3 == null || getInput4.trim().equals("")){

                    Toast.makeText(getBaseContext(), "Please enter password", Toast.LENGTH_LONG).show();
                }



                else{
                    ParseUser user = new ParseUser();
                    user.setUsername(getInput);
                    user.setPassword(getInput4);
                    user.setEmail(getInput3);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(v.getContext(), content.class);
                                startActivityForResult(intent, 0);

                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up!",
                                        Toast.LENGTH_LONG).show();
                            }

                            else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }}


                    });


                }

            }

        });

    }
    @Override
    public void onBackPressed(){ this.finish(); }     //method to kill the class if the back button is pressed


    public void onBackPressed(View view){ this.finish();}



}

