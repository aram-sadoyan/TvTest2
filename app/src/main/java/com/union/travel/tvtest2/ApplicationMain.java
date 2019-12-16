package com.union.travel.tvtest2;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ApplicationMain extends Application {

    public static ApplicationMain instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);


    }


    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
    public static ApplicationMain getInstance() {
        return instance;
    }


}
