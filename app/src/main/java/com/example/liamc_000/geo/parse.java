package com.example.liamc_000.geo;

/**
 * Created by liamc_000 on 02/02/2016.
 */
import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;

import android.app.Application;


public class parse  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        Parse.initialize(this, "jd32J9391VFtDrdkOAhScH06AZH5qI6qd6TxiQOP", "v9c4UIaCm0B9iEzY2DYVJfRPIOs2nJupa8yjw7ub");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);


    }
}

