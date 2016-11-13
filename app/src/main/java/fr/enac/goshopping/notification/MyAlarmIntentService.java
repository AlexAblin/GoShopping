package fr.enac.goshopping.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import fr.enac.goshopping.MainActivity;
import fr.enac.goshopping.R;

/**
 * Created by alexandre on 12/11/2016.
 */

public class MyAlarmIntentService extends IntentService {

    private NotificationManager mNotificationManager;

    public MyAlarmIntentService(){
        super("Alarm");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyAlarmIntentService(String name) {
        super(name);
    }

    private void sendNotification() {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Go!Shopping")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("C'est le moment de faire un peu de Shopping! Ceci est un rappel de liste Go!Shopping."))
                        .setContentText("Rappel de liste.")
                        .setSmallIcon(R.drawable.shop_icon)
                        .setDefaults(Notification.DEFAULT_ALL);
                        /*.addAction (R.drawable.list_icon,
                                "Voir la liste", null)
                        .addAction (R.drawable.calendar_icon,
                                "Me rappeler plus tard", null);*/
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification();
    }
}

