package com.testosterolapp.unrd.serverCommunication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController {

    private static AppController AppControllerSingleton;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private AppController(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized AppController getInstance(Context context) {
        if (AppControllerSingleton == null) {
            AppControllerSingleton = new AppController(context);
        }
        return AppControllerSingleton;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
