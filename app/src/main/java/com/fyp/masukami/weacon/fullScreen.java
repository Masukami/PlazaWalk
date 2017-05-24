package com.fyp.masukami.weacon;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Suhail on 5/16/2017.
 */

public class fullScreen extends AppCompatActivity {

    private ImageView ivFloorPlan;
    private final String ipAddress = "http://192.168.1.176/";
    private String address, floor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewimagefull);
        ivFloorPlan = (ImageView)findViewById(R.id.ivFloorPlan);
        Bundle extra = getIntent().getExtras();
        address = extra.getString("address");
        floor = address.substring(7, 13);

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
