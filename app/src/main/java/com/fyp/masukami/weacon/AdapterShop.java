package com.fyp.masukami.weacon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
