package com.fyp.masukami.weacon;

import android.view.View;

/**
 * Created by Suhail on 5/13/2017.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}
