package com.codesyaoriol.pampletapp.core;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class GRequest {

    public static Calendar c = Calendar.getInstance();


    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static String dateNow = df.format(c.getTime());

    /* GET METHOD FROM API */
//    format 2016-12-21 date now

    public static String kApiMethodGetUserInfo = "file/getLink/"+dateNow;



    private int mReqMethod;
    private String mUrl;
    private String mResource;
    private JSONObject mJsonParams = null;
    private HashMap<String, String> mParams;
    private GResponseListener mResponseListener;
    private GResponseErrorListener mResponseErrorListener;


    public GRequest(String resource,
                     HashMap<String, String> params,
                     GResponseListener pResListener,
                     GResponseErrorListener pResErrorListener) {
        this.mResource = resource;
        this.mParams = params;
        this.mResponseListener = pResListener;
        this.mResponseErrorListener = pResErrorListener;

    }

    public static String getApiRootForResource() {
        if (GConfiguration.DEBUG) {
            return GConfiguration.testURL;
        } else {
            return GConfiguration.liveURL;
        }
    }

    public void execute() {
        this.mUrl = getApiRootForResource() + mResource;
        this.mReqMethod = getRequestMethod(mResource);


        CustomRequest req = createJsonRequest();
        AppController.getRequestQueue().addToRequestQueue(req, mResource);
    }

    private CustomRequest createJsonRequest() {

        GDebug.logDebug("PRequest", "Request url: " + mUrl);

        return new CustomRequest(mReqMethod, mUrl, mParams, mResponseListener, mResponseErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //Add session token header
                Map<String, String> headers = new HashMap<>();
                headers.put("Session-Token", GSharedPreferences.getSomeStringValue(AppController.getInstance(), "session_token"));
                return headers;
            }
        };
    }

    private int getRequestMethod(String resource) {
        if (
                resource.contains("get")
                ) {
            addGetParams();
            return Request.Method.GET;
        } else {
            return Request.Method.POST;
        }
    }

    private void addGetParams() {
        mUrl = mUrl + "?";
        Iterator it = mParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            mUrl = mUrl + pair.getKey() + "=" + pair.getValue() + "&";
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


}

