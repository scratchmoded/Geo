package com.example.liamc_000.geo;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import java.util.List;
import java.util.ArrayList;

public class SavedMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList map = new ArrayList();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("maps");
        query.orderByDescending("updatedAT");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> maps, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < maps.size(); i++) {
                    map.add(maps.get(i).getParseObject(String.valueOf(maps)));



                    }

                    ListView mapListView=(ListView) findViewById(R.id.mapListView);
                    ArrayAdapter <String> mapsArrayAdapter = new ArrayAdapter<String>(SavedMap.this,
                            R.layout.map_list_items,map);




                            mapListView.setAdapter(mapsArrayAdapter);

                }
            }


        });

    }
}
