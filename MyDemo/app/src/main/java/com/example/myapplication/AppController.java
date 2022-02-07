package com.example.myapplication;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Application subclass and provides a static instance of itself to any class calling getInstance()
 * and also allows access to a Volley.RequestQueue to enable JSON Requests to be added to the queue and loaded
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController sInstance;

    /**
     * {@inheritDoc}
     * Initialises the UI and sets sInstance Object to this
     */
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    /**
     * Returns the static AppController object mInstance
     */
    public static synchronized AppController getInstance() {
        return sInstance;
    }

    /**
     * Initialises mRequestQueue object if it is null using Volley.newRequestQueue and returns it
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Sets tagForRequest parameter to tag of aRequest parameter calling setTag() on aRequest, gets a copy of RequestQueue and adds aRequest to queue
     *
     * @param aRequest a Volley.Request object containing info about a Json Request to be added to RequestQueue and loaded
     * @param tagForRequest a tag to be added to aRequest
     */
    public <T> void addToRequestQueue(Request<T> aRequest, String tagForRequest) {
        aRequest.setTag(TextUtils.isEmpty(tagForRequest) ? TAG : tagForRequest);
        getRequestQueue().add(aRequest);
    }

    /**
     * calls setTag on aRequest parameter passing static String TAG, gets a copy of RequestQueue and adds aRequest to queue
     *
     * @param aRequest a Volley.Request object containing info about a Json Request to be added to RequestQueue and loaded
     */
    public <T> void addToRequestQueue(Request<T> aRequest) {
        aRequest.setTag(TAG);
        getRequestQueue().add(aRequest);
    }

    /**
     * Calls cancelAll(aTag) on local mRequestQueue if is not null passing aTag received argument as a search criteria
     *
     * @param aTag contains info linked to a Volley.Request object to be removed from mRequestQueue so it is not loaded
     */
    public void cancelPendingRequests(Object aTag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(aTag);
        }
    }
}