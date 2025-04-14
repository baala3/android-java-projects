package com.example.trivia.controller;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton extends Application {
    private static MySingleton instance;
    private RequestQueue requestQueue;
   // private ImageLoader imageLoader;
   // private static Context ctx;


    public static synchronized MySingleton getInstance() {
      //  if (instance == null) {
     //       instance = new MySingleton(context);
     //   }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //instance lo put the main activity context
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}