package com.android.research.researchproject;

import android.app.Application;

import com.android.research.researchproject.utility.ServerUtils;
import com.facebook.FacebookSdk;
import com.parse.Parse;

/**
 * Created by amit on 11/21/2015.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
//        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ServerUtils.APPLICATION_ID, ServerUtils.CLIENT_KEY);

        FacebookSdk.sdkInitialize(this);
    }
}
