package com.fyp.masukami.weacon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.fyp.masukami.weacon.estimote.BeaconID;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetails;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetailsFactory;
import com.fyp.masukami.weacon.estimote.ProximityContentManager;

import java.util.Arrays;

/**
 * Created by Suhail on 5/24/2017.
 */

public class Directions extends AppCompatActivity {

    Advertisers advertiser;
    ImageView ivDirection;
    MyApplication app;
    private boolean isConnected;
    public WifiManager wifiManager;
    private String currentIP = "";
//    Change of mind. Use monitoring for advertising
//    private final Region iceRegion = new Region("Ice Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 41073, 32690); //Beacon 8
//    private final Region blueberryRegion = new Region("Blueberry Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 63797, 8827); //Beacon 9
//    private final Region lemonRegion = new Region("Lemon Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 40116, 9175);
    private BeaconManager beaconManager;
    private ProximityContentManager proximityContentManager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        checkWifi();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.directions);
        Intent advertiserDetails = getIntent();
        advertiser = (Advertisers)advertiserDetails.getSerializableExtra("advertiser");
        ivDirection = (ImageView)findViewById(R.id.ivDirection);
        beaconManager = new BeaconManager(getApplicationContext());

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 48201, 32369), //suhail-candyfloss
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 63797, 8827), //suhail-blueberry
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 41073, 32690)), //suhail-ice
                new EstimoteCloudBeaconDetailsFactory());

        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if(beaconDetails.getBeaconName().equals("suhail-blueberry")){
                        Glide.with(Directions.this)
                                .load(app.ipAddress + advertiser.getPathwayImage()[8])
                                .into(ivDirection);
                    } else if (beaconDetails.getBeaconName().equals("suhail-ice")){
                        Glide.with(Directions.this)
                                .load(app.ipAddress + advertiser.getPathwayImage()[7])
                                .into(ivDirection);
                    } else if (beaconDetails.getBeaconName().equals("suhail-candyfloss")){
                        Glide.with(Directions.this)
                                .load(app.ipAddress + advertiser.getPathwayImage()[3])
                                .into(ivDirection);
                    }
                }else if (isConnected){
                    currentIP = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                    int router = getRouters(currentIP.charAt(10));
                    Glide.with(Directions.this)
                            .load(app.ipAddress + advertiser.getPathwayImage()[router])
                            .into(ivDirection);
                }else{
                    Glide.with(Directions.this)
                            .load(app.ipAddress + "plazawalk/advertisers/directionOOR.png")
                            .into(ivDirection);
                }
            }
        });
//        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
//            @Override
//            public void onServiceReady() {
//                beaconManager.startMonitoring(iceRegion);
//                beaconManager.startMonitoring(blueberryRegion);
//                beaconManager.startMonitoring(lemonRegion);
//            }
//        });



//        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener(){
//            @Override
//            public void onEnteredRegion(Region region, List<Beacon> list) {
//                if (region.getIdentifier().equals("Ice Beacon")){
//                    Glide.with(Directions.this)
//                            .load(app.ipAddress + advertiser.getPathwayImage()[7])
//                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
//                            .placeholder(R.drawable.ic_alert_box)
//                            .error(R.drawable.ic_alert_box)
//                            .into(ivDirection);
//                } else if (region.getIdentifier().equals("Blueberry Beacon")){
//                    Glide.with(Directions.this)
//                            .load(app.ipAddress + advertiser.getPathwayImage()[8])
//                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
//                            .placeholder(R.drawable.ic_alert_box)
//                            .error(R.drawable.ic_alert_box)
//                            .into(ivDirection);
//                } else if (region.getIdentifier().equals("Lemon Beacon")){
//                }
//            }
//            @Override
//            public void onExitedRegion(Region region) {
//                //Exit region event
//                if (region.getIdentifier().equals("Ice Beacon")){
//                    Log.d("Directions", "Exited Marshmallow Region");
//                } else if (region.getIdentifier().equals("Blueberry Beacon")){
//                    Log.d("Directions", "Exited Blueberry Region");
//                } else if (region.getIdentifier().equals("Lemon Beacon")){
//                    Log.d("Directions", "Exited Mint Region");
//                }
//            }
//        });
    }

    private int getRouters(char ip) {
        int router = 0;

        if (ip == '1')
            router = 1;
        else if (ip == '2')
            router = 2;
        else if (ip == '3')
            router = 3;
        else if (ip == '4')
            router = 4;
        else if (ip == '5')
            router = 5;
        else if (ip == '6')
            router = 6;
        else if (ip == '7')
            router = 7;
        else if (ip == '8')
            router = 8;

        return router;
    }

    @SuppressWarnings("deprecation")
    private void checkWifi() {
        ConnectivityManager connManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (netInfo.isConnected())
            isConnected = true;
        else
            isConnected = false;
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();
        proximityContentManager.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemRequirementsChecker.checkWithDefaultDialogs(this))
            proximityContentManager.startContentUpdates();
//        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
//            @Override
//            public void onServiceReady() {
//                beaconManager.startMonitoring(iceRegion);
//                beaconManager.startMonitoring(blueberryRegion);
//                beaconManager.startMonitoring(lemonRegion);
//            }
//        });
    }
}
