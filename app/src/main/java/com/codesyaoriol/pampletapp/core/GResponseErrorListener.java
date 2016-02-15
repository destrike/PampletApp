package com.codesyaoriol.pampletapp.core;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class GResponseErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        GDebug.logDebug("API_onErrorResponse", volleyError.toString());

    }
}
