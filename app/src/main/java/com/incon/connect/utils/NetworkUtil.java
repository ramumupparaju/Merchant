package com.incon.connect.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.incon.connect.ConnectApplication;

public class NetworkUtil {

    public static boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) ConnectApplication.getAppContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


}
