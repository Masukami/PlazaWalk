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

    private boolean beaconNotificationsEnabled = false;;
    private final Region iceRegion = new Region("Ice Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 41073, 32690);
    private final Region blueberryRegion = new Region("Blueberry Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 63797, 8827);
    private final Region lemonRegion = new Region("Lemon Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 40116, 9175);
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        EstimoteSDK.initialize(getApplicationContext(), "ariff-ishigaki-yahoo-com-s-7rp", "5d716e7a46df2d5149ccb981b2cb2ca8");
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
        @Override
        public void onServiceReady() {
            beaconManager.startMonitoring(iceRegion);
            beaconManager.startMonitoring(blueberryRegion);
            beaconManager.startMonitoring(lemonRegion);
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener(){
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if (region.getIdentifier().equals("Ice Beacon")){
                    showPromotionAds();
                } else if (region.getIdentifier().equals("Blueberry Beacon")){
                    showPromotionAds();
                } else if (region.getIdentifier().equals("Lemon Beacon")){
                    showPromotionAds();
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                //Exit region event
            }
        });

    }

    public void showPromotionAds(){
      //AD POPUP CODING  
    }

    public void enableBeaconNotifications(int major, int minor, List<String> places) {
        if (beaconNotificationsEnabled) { return; }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);
        beaconNotificationsManager.addNotification(
                new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", major, minor),
                "Awesome stores nearby!" +
                        "\n" + places.get(0) +
                        "\n" + places.get(1) +
                        "\n" + places.get(2));
        beaconNotificationsManager.startMonitoring();

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }

}
