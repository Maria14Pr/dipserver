package com.learn_django.cinemaguide;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

//проверка подключения к интернету
public class InternetUtil {

    public static   boolean myInternetConnection(Context context){

        // для новых версий api
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnline(Context context){

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }

    }


    public static boolean isInternetOnline(Context context){

        if (InternetUtil.isOnline(context)){

            return true;
        }else {
            Toast.makeText(context, "Check Internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}