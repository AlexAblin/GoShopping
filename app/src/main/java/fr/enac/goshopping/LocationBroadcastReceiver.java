package fr.enac.goshopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Geekette on 10/11/2016.
 */

public class LocationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Location update received");
        Toast.makeText(context,"Test de réception",Toast.LENGTH_LONG).show();
    }
}
