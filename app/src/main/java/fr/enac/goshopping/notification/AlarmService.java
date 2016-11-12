package fr.enac.goshopping.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import fr.enac.goshopping.R;
import fr.enac.goshopping.fragment.RappelsFragment;

/**
 * Created by alexandre on 12/11/2016.
 */

public class AlarmService  extends IntentService {
        private NotificationManager alarmNotificationManager;

        public AlarmService() {
            super("AlarmService");
        }

        @Override
        public void onHandleIntent(Intent intent) {
            sendNotification("Wake Up! Wake Up!");
        }

        private void sendNotification(String msg) {
            Log.d("AlarmService", "Preparing to send notification...: " + msg);
            alarmNotificationManager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, RappelsFragment.class), 0);

            NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                    this).setContentTitle("Alarm").setSmallIcon(R.drawable.ic_menu_camera)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg);


            alamNotificationBuilder.setContentIntent(contentIntent);
            alarmNotificationManager.notify(1, alamNotificationBuilder.build());
            Log.d("AlarmService", "Notification sent.");
        }
}

