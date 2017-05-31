package com.fyp.masukami.weacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;
import com.fyp.masukami.weacon.estimote.BeaconID;
import com.fyp.masukami.weacon.estimote.BeaconNotificationsManager;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Suhail on 5/5/2017.
 */

public class MyApplication extends Application {

    public final String ipAddress = "http://192.168.1.176/";
    private AssetManager am;
    public Typeface BebasBold, BebasBook, BebasLight, BebasRegular, SansBlack, SansRegular, SansSemiBold;

    @Override
    public void onCreate() {
        super.onCreate();
        am = getApplicationContext().getAssets();
        initializeFonts();
        EstimoteSDK.initialize(getApplicationContext(), "ariff-ishigaki-yahoo-com-s-7rp", "5d716e7a46df2d5149ccb981b2cb2ca8");
    }

    private void initializeFonts() {
        BebasBold = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "BebasNeue_Bold.ttf"));
        BebasBook = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "BebasNeue_Book.ttf"));
        BebasLight = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "BebasNeue_Light.ttf"));
        BebasRegular = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "BebasNeue_Regular.ttf"));
        SansBlack = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "SourceSansPro-Black.otf"));
        SansRegular = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "SourceSansPro-Regular.otf"));
        SansSemiBold = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "SourceSansPro-Semibold.otf"));
    }

}
