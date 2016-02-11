package com.codesyaoriol.pampletapp.core;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class GResponseListener implements Response.Listener<JSONObject> {

    @Override
    public void onResponse(JSONObject jsonObject) {

        try {
            GDebug.logDebug("API_onResponse", jsonObject.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
