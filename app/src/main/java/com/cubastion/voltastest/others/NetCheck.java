package com.cubastion.voltastest.others;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Aatish Rana on 2/17/2016.
 */
public class NetCheck {

    /*
    * This class is used for checking the network state*/
    Context acontext;
    public NetCheck(Context c)
    {
        acontext=c;
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                acontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
