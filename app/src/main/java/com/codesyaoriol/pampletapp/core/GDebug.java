package com.codesyaoriol.pampletapp.core;

import android.util.Log;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class GDebug {

    public static void logDebug(String tag, String message) {
        if (GConfiguration.DEBUG) {
            Log.i("DEBUG_" + tag, message);
        }
    }

}
