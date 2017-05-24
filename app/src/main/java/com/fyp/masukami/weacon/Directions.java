package com.fyp.masukami.weacon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by Suhail on 5/24/2017.
 */

public class Directions extends AppCompatActivity {

    Advertisers advertiser;
    ImageView ivDirection;
    private final String ipAddress = "http://192.168.1.176/";

    private final Region iceRegion = new Region("Ice Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 41073, 32690); //Beacon 8
    private final Region blueberryRegion = new Region("Blueberry Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 63797, 8827); //Beacon 9
    private final Region lemonRegion = new Region("Lemon Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 40116, 9175);
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions);

        Intent advertiserDetails = getIntent();
        advertiser = (Advertisers)advertiserDetails.getSerializableExtra("advertiser");
        ivDirection = (ImageView)findViewById(R.id.ivDirection);
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
                    Glide.with(Directions.this)
                            .load(ipAddress + advertiser.getPathwayImage()[7])
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .placeholder(R.drawable.ic_alert_box)
                            .error(R.drawable.ic_alert_box)
                            .into(ivDirection);
                } else if (region.getIdentifier().equals("Blueberry Beacon")){
                    Glide.with(Directions.this)
                            .load(ipAddress + advertiser.getPathwayImage()[8])
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .placeholder(R.drawable.ic_alert_box)
                            .error(R.drawable.ic_alert_box)
                            .into(ivDirection);
                } else if (region.getIdentifier().equals("Lemon Beacon")){
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                //Exit region event
            }
        });
    }

}
