package com.fyp.masukami.weacon;

import android.app.Application;
import android.util.Log;

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

    @Override
    public void onCreate() {
        super.onCreate();
        EstimoteSDK.initialize(getApplicationContext(), "ariff-ishigaki-yahoo-com-s-7rp", "5d716e7a46df2d5149ccb981b2cb2ca8");
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
