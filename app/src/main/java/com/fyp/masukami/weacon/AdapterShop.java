package com.fyp.masukami.weacon;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private final String ipAddress = "http://192.168.1.176/";
    private Typeface SansSemiBold, SansRegular;
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
        SansSemiBold = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/SourceSansPro-Semibold.otf");
        SansRegular = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/SourceSansPro-Regular.otf");
        holder.shopName.setTypeface(SansSemiBold);
        holder.productName.setTypeface(SansRegular);
        holder.shopLocation.setTypeface(SansRegular);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterHolder myHolder = (AdapterHolder) holder;
        if(!data.isEmpty()){
            Advertisers current = data.get(position);
            myHolder.shopName.setText(current.getName());
            myHolder.productName.setText(current.getProductName());
            myHolder.shopLocation.setText("Location : " + current.getAddress());
            Glide.with(context).load(ipAddress + current.getLogo())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.shoplogoplaceholder)
                    .error(R.drawable.ic_alert_box)
                    .into(myHolder.shopLogo);
        }else{
            Log.d("AdapterShop", "Advertisers data is empty");
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
