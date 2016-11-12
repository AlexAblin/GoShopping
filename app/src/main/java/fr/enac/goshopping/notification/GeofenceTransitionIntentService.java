package fr.enac.goshopping.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import fr.enac.goshopping.MainActivity;
import fr.enac.goshopping.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Geekette on 11/11/2016.
 */

public class GeofenceTransitionIntentService extends IntentService {

    private NotificationManager mNotificationManager;

    public GeofenceTransitionIntentService() {
        super("Geofence");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GeofenceTransitionIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        //Toast.makeText(this, "Geofence recu", Toast.LENGTH_SHORT).show();
        //System.out.println("Test geofence");
        if (geofencingEvent.hasError()) {
            /*String errorMessage = getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);*/
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER /*||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT*/) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );
            // Send notification and log the transition details.

            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            /*Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
                    geofenceTransition));*/
        }
    }

    private String getGeofenceTransitionDetails(GeofenceTransitionIntentService geofenceTransitionIntentService, int geofenceTransition, List triggeringGeofences) {
        return "Toto";
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Go!Shopping")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Vous êtes à proximité de l'un de vos magasins! N'oubliez rien, consultez votre liste de courses."))
                        .setContentText("Vous êtes à proximité de l'un de vos magasins!")
                        .setSmallIcon(R.drawable.shop_icon)
                        .setDefaults(Notification.DEFAULT_ALL);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
