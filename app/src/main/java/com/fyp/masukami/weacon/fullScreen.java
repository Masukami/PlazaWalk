package com.fyp.masukami.weacon;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Suhail on 5/16/2017.
 */

public class fullScreen extends AppCompatActivity {

    private ImageView ivFloorPlan, ivShopLogo;
    private final String ipAddress = "http://192.168.1.176/";
    private String address, floor;
    private Advertisers advertiser;
    private TextView tvShopName, tvShopAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.viewimagefull);
        ivFloorPlan = (ImageView)findViewById(R.id.ivFloorPlan);
        ivShopLogo = (ImageView)findViewById(R.id.ivShopLogo);
        tvShopName = (TextView)findViewById(R.id.tvShopName);
        tvShopAddress = (TextView)findViewById(R.id.tvAddress);

        Intent advertiserDetails = getIntent();
        advertiser = (Advertisers)advertiserDetails.getSerializableExtra("advertiser");
        address = advertiser.getAddress();
        floor = address.substring(7, 13);

        Glide.with(fullScreen.this).load(ipAddress + advertiser.getLogo())
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(ivShopLogo);

        tvShopName.setText(advertiser.getName());
        tvShopAddress.setText(advertiser.getAddress());

        if(floor.equals("First ")){
            Glide.with(fullScreen.this)
                    .load(ipAddress + "plazawalk/advertisers/firstfloor.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .crossFade()
                    .into(ivFloorPlan);
        }else if (floor.equals("Ground")){
            Glide.with(fullScreen.this)
                    .load(ipAddress + "plazawalk/advertisers/groundfloor.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .crossFade()
                    .into(ivFloorPlan);
        }
    }

}
