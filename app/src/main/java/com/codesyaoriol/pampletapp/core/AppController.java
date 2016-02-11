package com.codesyaoriol.pampletapp.core;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class AppController extends Application {

    private static AppController mInstance;
    private static GRequestQueue mRequestQueue;


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static GRequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        GSharedPreferences.init(mInstance);
        mRequestQueue = new GRequestQueue(getApplicationContext());


    }


}
