package fr.enac.goshopping.notification;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import fr.enac.goshopping.MainActivity;
import fr.enac.goshopping.R;
import fr.enac.goshopping.fragment.RappelsFragment;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by alexandre on 12/11/2016.
 */

public class MyBroacastIntentService extends IntentService {

    private NotificationManager mNotificationManager;

    public MyBroacastIntentService(){
        super("Alarm");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyBroacastIntentService(String name) {
        super(name);
    }

    //@Override
    public void onReceive(final Context context, Intent intent) {
        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        /*Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
               AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);*/
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
                        .setDefaults(Notification.DEFAULT_ALL)
                        .addAction (R.drawable.list_icon,
                                "Voir la liste", null)
                        .addAction (R.drawable.calendar_icon,
                                "Me rappeler plus tard", null);;
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification();
    }
}

