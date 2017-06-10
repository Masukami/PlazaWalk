package com.fyp.masukami.weacon;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
/**
 * Created by Suhail on 6/7/2017.
 */

public class AdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> data = new ArrayList<>();
    private Typeface SansSemiBold;

    public AdapterProduct(Context context, ArrayList<String> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_product, parent, false);
        AdapterHolder holder = new AdapterHolder(view);
        SansSemiBold = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/SourceSansPro-Semibold.otf");
        holder.productName.setTypeface(SansSemiBold);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterHolder myHolder = (AdapterHolder) holder;
        if(!data.isEmpty()){
            String product = data.get(position);
            myHolder.productName.setText(product);
        }else{
            Log.d("AdapterShop", "Products data is empty");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
