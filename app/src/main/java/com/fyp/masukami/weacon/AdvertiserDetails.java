package com.fyp.masukami.weacon;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
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
    private final String ipAddress = "http://172.20.10.4/";
    ImageView ivBanner, ivFloorPlan;
    CardView cvFloorPlan;

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
        cvFloorPlan = (CardView)findViewById(R.id.cvFloorPLan);
        ivFloorPlan.setOnClickListener(this);

        Glide.with(this).load(ipAddress + advertiser.getLogo())
                .override(600,150)
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(ivBanner);

        Glide.with(this)
                .load(ipAddress + "plazawalk/advertisers/firstfloor.png")
                .override(600,200)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                .crossFade()
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

    private boolean zoomOut = false;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cvFloorPLan){
//            Intent viewFullScreen = new Intent(AdvertiserDetails.this, fullScreen.class);
//            startActivity(viewFullScreen);
            if(zoomOut){
                Toast.makeText(getApplicationContext(), "NORMAL SIZE!", Toast.LENGTH_LONG).show();
                ivFloorPlan.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                ivFloorPlan.setAdjustViewBounds(true);
                zoomOut = false;
            }else{
                Toast.makeText(getApplicationContext(), "FULLSCREEN!", Toast.LENGTH_LONG).show();
                ivFloorPlan.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                ivFloorPlan.setScaleType(ImageView.ScaleType.FIT_XY);
                zoomOut = true;
            }
        }
    }
}
