package com.example.liamc_000.geo;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SavedMap extends ListActivity {

    ListView listView;
    protected List<ParseObject> mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_map);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ParseUser curentUser = ParseUser.getCurrentUser();
        if (curentUser != null) {


            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("maps");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> statusobject, com.parse.ParseException e) {
                    if (e == null) {
                        //success
                        mStatus = statusobject;
                        mapadaptor adaptor = new mapadaptor(getListView().getContext(), mStatus);
                        setListAdapter(adaptor);

                    } else {
                        //there was a problem
                    }
                }
            });


        }


    }
}







