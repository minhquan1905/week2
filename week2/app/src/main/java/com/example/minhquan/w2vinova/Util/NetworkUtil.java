package com.example.minhquan.w2vinova.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    private static Context context;
    public static void setContext(Context context){
        NetworkUtil.context = context;
    }

    public static boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if(connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo!=null && networkInfo.isConnectedOrConnecting();
        }
        else return false;
    }
}
