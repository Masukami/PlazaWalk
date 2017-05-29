package com.fyp.masukami.weacon;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Suhail on 5/26/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Advertisers> {

    private Context mContext;
    private int layoutResourceID;
    private List<Advertisers> mGridData = Collections.emptyList();
    private final String ipAddress = "http://192.168.1.176/";

    public GridViewAdapter(Context mContext, int layoutResourceID, List<Advertisers> mGridData){
        super(mContext, layoutResourceID, mGridData);
        this.layoutResourceID = layoutResourceID;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<Advertisers> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if(row == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceID, parent, false);
            holder = new ViewHolder();
            holder.tvShopName = (TextView) row.findViewById(R.id.grid_item_title);
            holder.ivLogo = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Advertisers advertiser = mGridData.get(position);
        holder.tvShopName.setText(advertiser.getName());

        Glide.with(getContext()).load(ipAddress + advertiser.getLogo())
                .thumbnail(0.1f)
                .placeholder(R.drawable.shoplogoplaceholder)
                .error(R.drawable.ic_alert_box)
                .into(holder.ivLogo);
        return row;
    }

    static class ViewHolder {
        TextView tvShopName;
        ImageView ivLogo;
    }
}
