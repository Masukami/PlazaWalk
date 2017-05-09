package com.fyp.masukami.weacon;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

/**
 * Created by Suhail on 5/5/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EstimoteSDK.initialize(getApplicationContext(), "ariff-ishigaki-yahoo-com-s-7rp", "5d716e7a46df2d5149ccb981b2cb2ca8");
    }
}
