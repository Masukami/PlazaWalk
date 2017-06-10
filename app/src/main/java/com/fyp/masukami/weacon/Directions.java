package com.fyp.masukami.weacon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.fyp.masukami.weacon.estimote.BeaconID;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetails;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetailsFactory;
import com.fyp.masukami.weacon.estimote.ProximityContentManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Suhail on 5/24/2017.
 */

public class Directions extends AppCompatActivity {

    Advertisers advertiser;
    ImageView ivDirection;
    MyApplication app;

//    Change of mind. Use monitoring for advertising
//    private final Region iceRegion = new Region("Ice Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 41073, 32690); //Beacon 8
//    private final Region blueberryRegion = new Region("Blueberry Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 63797, 8827); //Beacon 9
//    private final Region lemonRegion = new Region("Lemon Beacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 40116, 9175);
    private BeaconManager beaconManager;
    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.directions);
        Intent advertiserDetails = getIntent();
        advertiser = (Advertisers)advertiserDetails.getSerializableExtra("advertiser");
        ivDirection = (ImageView)findViewById(R.id.ivDirection);
        beaconManager = new BeaconManager(getApplicationContext());
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivDirection);

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 39324, 29378),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 48201, 32369),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 56450, 55624),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 15237, 17187),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 24024, 52596),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 49483, 6190),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 63797, 8827),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 41073, 32690)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if(beaconDetails.getBeaconName().equals("suhail-blueberry")){
                        Glide.with(Directions.this)
                                .load(app.ipAddress + advertiser.getPathwayImage()[8])
                                .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                                .placeholder(R.drawable.directionplaceholder)
                                .error(R.drawable.directionerror)
                                .into(ivDirection);
                    } else if (beaconDetails.getBeaconName().equals("suhail-ice")){
                        Glide.with(Directions.this)
                                .load(app.ipAddress + advertiser.getPathwayImage()[7])
                                .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                                .placeholder(R.drawable.directionplaceholder)
                                .error(R.drawable.directionerror)
                                .into(ivDirection);
                    } else if (beaconDetails.getBeaconName().equals("suhail-mint")){
                        //Mint beacon. Add more if want
                    }
                } else {
                    Glide.with(Directions.this)
                            .load(app.ipAddress + "plazawalk/advertisers/baskinrobbins/directionTest.gif")
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .placeholder(R.drawable.directionplaceholder)
                            .error(R.drawable.directionerror)
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
