package com.viostaticapp.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                status = "Wifi enabled";
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                status = "Mobile data enabled";

        } else
            status = "No internet is available";

        return status;
    }
}
