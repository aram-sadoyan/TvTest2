package com.union.travel.tvtest2;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ApplicationMain extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);


    }


}
