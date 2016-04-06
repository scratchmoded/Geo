package com.example.liamc_000.geo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;
import android.content.Intent;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.ParseException;
/**
 * Created by liamc_000 on 02/02/2016.
 */
public class content extends Activity {


    Button newmap, smap, groups;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newmap=(Button)findViewById(R.id.NewMapBtn);
        smap=(Button)findViewById(R.id.SavedMapBtn);
        groups=(Button)findViewById(R.id.GroupsBtn);


        newmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext(),MapsActivity.class);      //intent to start the registration page
                startActivityForResult(intent,0);
            }
        });

        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), group.class);      //intent to start the registration page
                startActivityForResult(intent, 0);
            }
        });

            smap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(v.getContext(),SavedMap.class);
                    startActivityForResult(intent,0);

                }
            });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
