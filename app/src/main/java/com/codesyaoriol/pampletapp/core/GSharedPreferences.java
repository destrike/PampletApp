package com.codesyaoriol.pampletapp.core;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class GSharedPreferences {
    private static final String APP_PREFS = "APP_SETTINGS";
    private static SharedPreferences mSharedPreferences;
    private static Context mContext;

    private GSharedPreferences() {
    }

    public static void init(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public static String getSomeStringValue(Context context, String key) {
        return mSharedPreferences.getString(key, "");
    }

    public static void setSomeStringValue(Context context, String key, String newValue) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, newValue);
        editor.commit();
    }

    public static void clearAllPreferences() {
        GDebug.logDebug("clearAllPreferences", "PREF CLEARED! LOGOUT!");
        mSharedPreferences.edit().clear().commit();
    }
}

