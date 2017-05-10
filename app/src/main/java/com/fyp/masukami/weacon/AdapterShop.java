package com.fyp.masukami.weacon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by Suhail on 5/9/2017.
 */

public class AdapterShop extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    List<Advertisers> data = Collections.emptyList();
    Advertisers current;
    int currentPos = 0;

    //Create constructor to initialize context and data sent from Main Activity

    public AdapterShop(Context context, List<Advertisers> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_shop, parent, false);
        AdapterHolder holder = new AdapterHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterHolder myHolder = (AdapterHolder) holder;
        Advertisers current = data.get(position);
        myHolder.shopName.setText(current.getName());
        myHolder.productName.setText(current.getProductName());
        myHolder.shopLocation.setText("Location : " + current.getAddress());

        //load image using Glide
        Glide.with(context).load(current.getLogo())
                .placeholder(R.drawable.ic_alert_box)
                .error(R.drawable.ic_alert_box)
                .into(myHolder.shopLogo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
