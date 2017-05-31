package com.fyp.masukami.weacon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Suhail on 5/9/2017.
 */

public class AdapterHolder extends RecyclerView.ViewHolder{

        TextView shopName, productName, shopLocation;
        ImageView shopLogo;
        MyApplication app;

        public AdapterHolder(View itemView) {
            super(itemView);
            shopName = (TextView) itemView.findViewById(R.id.tvName);
            productName = (TextView) itemView.findViewById(R.id.tvProductName);
            shopLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            shopLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
        }

}
