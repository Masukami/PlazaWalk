package com.fyp.masukami.weacon;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suhail on 5/13/2017.
 */

public class AdvertiserDetails extends AppCompatActivity implements View.OnClickListener{

    Advertisers advertiser;
    TextView tvName, tvProduct, tvLocation, tvDescription;
    private final String ipAddress = "http://192.168.1.176/";
    ImageView ivBanner, ivFloorPlan;
    private String floor;
    private Button btnDirection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertiserdetails);

        Intent advertiserDetail = getIntent();
        advertiser = (Advertisers)advertiserDetail.getSerializableExtra("advertiser");

        tvName = (TextView)findViewById(R.id.tvName);
        tvName.setText(advertiser.getName());
        tvProduct = (TextView)findViewById(R.id.tvProduct);
        tvProduct.setText(advertiser.getProductName());
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        tvLocation.setText(advertiser.getAddress());
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvDescription.setText(advertiser.getDescription());
        ivBanner = (ImageView)findViewById(R.id.ivBanner);
        ivFloorPlan = (ImageView)findViewById(R.id.ivFloorPlan);
        ivFloorPlan.setOnClickListener(this);
        floor = advertiser.getAddress().substring(7, 13);
        btnDirection = (Button)findViewById(R.id.btnDirection);
        btnDirection.setOnClickListener(this);

        Glide.with(this).load(ipAddress + advertiser.getLogo())
                .override(600,150)
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(ivBanner);

        if(floor.equals("First ")){
            Glide.with(this)
                    .load(ipAddress + "plazawalk/advertisers/firstfloor.png")
                    .override(600,200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .placeholder(R.drawable.ic_alert_box)
                    .error(R.drawable.ic_alert_box)
                    .into(ivFloorPlan);
        }else if (floor.equals("Ground")){
            Glide.with(this)
                    .load(ipAddress + "plazawalk/advertisers/groundfloor.png")
                    .override(600,200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .placeholder(R.drawable.ic_alert_box)
                    .error(R.drawable.ic_alert_box)
                    .into(ivFloorPlan);
        }

    }

    private boolean zoomOut = false;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ivFloorPlan){
            Intent viewFullScreen = new Intent(AdvertiserDetails.this, fullScreen.class);
            viewFullScreen.putExtra("address", advertiser.getAddress());
            startActivity(viewFullScreen);
        }else if(v.getId() == R.id.btnDirection){
            Intent showDirection = new Intent(AdvertiserDetails.this, Directions.class);
            showDirection.putExtra("advertiser", advertiser);
            startActivity(showDirection);
        }
    }
}
