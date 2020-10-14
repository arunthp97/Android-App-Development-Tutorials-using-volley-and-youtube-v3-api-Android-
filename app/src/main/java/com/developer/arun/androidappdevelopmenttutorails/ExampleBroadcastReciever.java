package com.developer.arun.androidappdevelopmenttutorails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class ExampleBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
        {
            boolean noConnectivity=intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY,false
            );
            if (noConnectivity)
            {
                Toast.makeText(context,"No Internet Connection!!!",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"You are online!!!",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
