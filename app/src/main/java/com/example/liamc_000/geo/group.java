package com.example.liamc_000.geo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import java.util.List;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        final ArrayList<String> names = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
//don't include yourself
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }

                    ListView usersListView = (ListView) findViewById(R.id.usersListView);
                    ArrayAdapter<String> namesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.user_list_item, names);
                    usersListView.setAdapter(namesArrayAdapter);



                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
















        }

    }


