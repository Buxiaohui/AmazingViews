package com.example.bxh.viewsapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by buxiaohui on 6/22/17.
 */

public class MyApp extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public MyApp() {
        super();
    }
}
