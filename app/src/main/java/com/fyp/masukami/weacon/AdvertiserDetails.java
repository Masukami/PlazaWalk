package com.fyp.masukami.weacon;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suhail on 5/13/2017.
 */

public class AdvertiserDetails extends AppCompatActivity{

    Advertisers advertiser;
    TextView tvName, tvProduct, tvLocation, tvDescription;
    private final String ipAddress = "http://192.168.1.176/";
    ImageView ivBanner, ivFloorPlan;

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

        Glide.with(this).load(ipAddress + advertiser.getLogo())
                .override(600,150)
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(ivBanner);

        Glide.with(this)
                .load("http://192.168.1.176/plazawalk/advertisers/Levi's/levispathway_1.png")
                .override(600,200)
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(ivFloorPlan);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backToMain = new Intent(this, Main.class);
        startActivity(backToMain);
        finish();
    }
}
