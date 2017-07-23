package com.fyp.masukami.weacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by suhai on 7/24/2017.
 */

public class WifiReceiverMain extends BroadcastReceiver {

    private Main main;

    public WifiReceiverMain(Main main) {
        this.main = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI){
            main.wifiChangeState(true);
            Log.d("WifiReceiver", "Wifi Enabled");
        }
        else{
            main.wifiChangeState(false);
            Log.d("WifiReceiver", "Wifi Disabled");
        }
    }
}
