package com.codesyaoriol.pampletapp.core;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by smartwavedev on 2/4/16.
 */
public class GRequestQueue {
    public static final String TAG = "PRequestQueue";

    private static GRequestQueue mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    protected GRequestQueue(Context context) {

        mContext = context;

        mRequestQueue = getRequestQueue();

        mImageLoader = getImageLoader();

    }

    public static synchronized GRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
//        if (mImageLoader == null) {
//
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache(mContext));
//        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        try {
            GDebug.logDebug("REQ", "header: " + req.getHeaders());
            GDebug.logDebug("REQ", "body: " + req.getBody().toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        } catch (NullPointerException npe) {
            //npe.printStackTrace();
        }

        GDebug.logDebug("REQ", "url: " + req.getUrl());
        GDebug.logDebug("REQ", "method: " + req.getMethod());
        GDebug.logDebug("REQ", "cacheKey: " + req.getCacheKey());
        GDebug.logDebug("REQ", "bodyContentType: " + req.getBodyContentType());

        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);

    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}

