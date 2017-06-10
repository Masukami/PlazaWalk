package com.fyp.masukami.weacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Suhail on 5/5/2017.
 */

public class MyApplication extends Application {

    public final String ipAddress = "http://192.168.1.176/";
    private AssetManager am;
    private BeaconManager beaconManager;
    public Typeface BebasBold, BebasBook, BebasLight, BebasRegular, SansBlack, SansRegular, SansSemiBold;
    private Region blueBerry, iceMarshmallow, beetrootA, candyFlossB, candyFlossD, mintCocktailC, beetrootE, blueberryF;

    @Override
    public void onCreate() {
        super.onCreate();
        am = getApplicationContext().getAssets();
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if (region.getIdentifier().equals("suhail-blueberry")){
                    showNotification("Check out these stores near you!",
                            "Auntie Anne's Pretzels\n" + "Caring Pharmacy\n" + "Nando's");
                }else if (region.getIdentifier().equals("suhail-ice")){
                    showNotification("Check out these stores near you!",
                            "Auntie Anne's Pretzels\n" + "Baskin Robbins\n" + "The Body Shop");
                }else if (region.getIdentifier().equals("lemon-tart")){

                }
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });

        initializeRegion();
        startMonitoring();
        initializeFonts();
        EstimoteSDK.initialize(getApplicationContext(), "ariff-ishigaki-yahoo-com-s-7rp", "5d716e7a46df2d5149ccb981b2cb2ca8");
    }

    private void initializeRegion() {
        blueBerry = new Region("suhail-blueberry", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 63797, 8827); //Beacon 9
        iceMarshmallow = new Region("suhail-ice", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 41073, 32690); //Beacon 8
        beetrootA = new Region("Beacon A", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 39324, 29378); //Beacon 1
        candyFlossB = new Region("Beacon B", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 48201,32369); //Beacon 3
        mintCocktailC = new Region("Beacon C", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 56450, 55624); //Region 5
        candyFlossD = new Region("Beacon D", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 15237, 17187); //Beacon 6
        beetrootE = new Region("Beacon E", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 24024, 52596);//Beacon 10
        blueberryF = new Region("Beacon F", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 49483, 6190);//Beacon11


    }

    public void stopMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.stopMonitoring(blueBerry);
                beaconManager.stopMonitoring(iceMarshmallow);
                beaconManager.stopMonitoring(beetrootA);
                beaconManager.stopMonitoring(candyFlossB);
                beaconManager.stopMonitoring(mintCocktailC);
                beaconManager.stopMonitoring(candyFlossD);
                beaconManager.stopMonitoring(beetrootE);
                beaconManager.stopMonitoring(blueberryF);
            }
        });
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(blueBerry);
                beaconManager.startMonitoring(iceMarshmallow);
                beaconManager.startMonitoring(beetrootA);
                beaconManager.startMonitoring(candyFlossB);
                beaconManager.startMonitoring(mintCocktailC);
                beaconManager.startMonitoring(candyFlossD);
                beaconManager.startMonitoring(beetrootE);
                beaconManager.startMonitoring(blueberryF);
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, Main.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Plaza Walk")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setStyle(new Notification.BigTextStyle()
                .bigText(message)
                .setSummaryText(title))
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notificationManager.notify(1, notification);
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
