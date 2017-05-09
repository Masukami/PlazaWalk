package com.fyp.masukami.weacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.fyp.masukami.weacon.estimote.BeaconID;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetails;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetailsFactory;
import com.fyp.masukami.weacon.estimote.ProximityContentManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView nearbyStores;
    private AdapterShop shopAdapter;

    //Beacon
    private BeaconManager beaconManager;
    private static final String TAG = "MainActivity";
    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = new BeaconManager(this);

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 63797, 8827),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 3878, 23708),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 41073, 32690)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {

                String text = "";
                Drawable background;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if(beaconDetails.getBeaconName().equals("suhail-blueberry")){
                        text = "You are in" + "\nBlueberry Area";
                        background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.blueberrygradient);
                    }else if(beaconDetails.getBeaconName().equals("suhail-ice")){
                        text = "You are in" + "\nMarshmallow Area";
                        background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icegradient);
                    }else{
                        text = "You are in" + "\nMint Area";
                        background = ContextCompat.getDrawable(getApplication(), R.drawable.mintgradient);
                    }
                } else {
                    text = "You are\nOut of range";
                    background = ContextCompat.getDrawable(getApplication(), R.drawable.maingradient);
                }
                ((TextView) findViewById(R.id.tvBLEinRange)).setText(text);
                findViewById(R.id.upperLayout).setBackground(
                        background != null ? background : ContextCompat.getDrawable(getApplicationContext(), R.drawable.maingradient));
            }
        });
    }

    protected void onPause() {
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();
        proximityContentManager.destroy();
        super.onDestroy();
    }

    protected void onResume() {
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
