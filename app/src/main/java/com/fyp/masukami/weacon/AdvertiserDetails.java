package com.fyp.masukami.weacon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Suhail on 5/13/2017.
 */

public class AdvertiserDetails extends AppCompatActivity implements View.OnClickListener, DescriptionFragment.OnFragmentInteractionListener,
FloorplanFragment.OnFragmentInteractionListener{

    Advertisers advertiser;
    TextView tvName, tvProduct, tvLocation;
    MyApplication app;
    ImageView ivBanner;
    private Button btnDirection;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertiserdetails);
        Intent advertiserDetail = getIntent();
        advertiser = (Advertisers)advertiserDetail.getSerializableExtra("advertiser");
        app = (MyApplication) getApplication();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tvName = (TextView)findViewById(R.id.tvName);
        tvName.setTypeface(app.SansSemiBold);
        tvName.setText(advertiser.getName());
        tvProduct = (TextView)findViewById(R.id.tvProduct);
        tvProduct.setTypeface(app.SansRegular);
        tvProduct.setText(advertiser.getProductName());
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        tvLocation.setTypeface(app.SansRegular);
        tvLocation.setText(advertiser.getAddress());
        ivBanner = (ImageView)findViewById(R.id.ivBanner);
        btnDirection = (Button)findViewById(R.id.btnDirection);
        btnDirection.setOnClickListener(this);

        Glide.with(this).load(app.ipAddress + advertiser.getLogo())
                .override(600,150)
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(ivBanner);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Send data to fragments
        Bundle bundleDesc = new Bundle();
        bundleDesc.putString("description", advertiser.getDescription());
        DescriptionFragment descFragment = new DescriptionFragment();
        descFragment.setArguments(bundleDesc);

        Bundle bundleAddress = new Bundle();
        bundleAddress.putString("address", advertiser.getAddress());
        FloorplanFragment floorplanFragment = new FloorplanFragment();
        floorplanFragment.setArguments(bundleAddress);

        adapter.addFragment(descFragment, "Description");
        adapter.addFragment(floorplanFragment, "Floor Plan");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDirection){
            Intent showDirection = new Intent(AdvertiserDetails.this, Directions.class);
            showDirection.putExtra("advertiser", advertiser);
            startActivity(showDirection);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
