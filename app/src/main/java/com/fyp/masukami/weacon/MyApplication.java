package com.fyp.masukami.weacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;
import com.fyp.masukami.weacon.estimote.BeaconID;
import com.fyp.masukami.weacon.estimote.BeaconNotificationsManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Suhail on 5/5/2017.
 */

public class MyApplication extends Application {

    public final String ipAddress = "http://192.168.1.176/";

    @Override
    public void onCreate() {
        super.onCreate();
        EstimoteSDK.initialize(getApplicationContext(), "ariff-ishigaki-yahoo-com-s-7rp", "5d716e7a46df2d5149ccb981b2cb2ca8");
    }

}
