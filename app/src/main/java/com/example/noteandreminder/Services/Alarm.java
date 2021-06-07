package com.example.noteandreminder.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.MainActivity;
import com.example.noteandreminder.R;

import java.util.TimerTask;

import static android.provider.Settings.System.getString;
import static com.example.noteandreminder.GlobalDefine.ALARM_NOTI_ID;

public class Alarm extends TimerTask {
    private Context context;
    private String noti_content;

    public Alarm(Context context, String noti_content) {
        this.context = context;
        this.noti_content = noti_content;
    }

    @Override
    public void run() {
        Log.v(GlobalDefine.TAG, "Say hi");
        Intent mainIntent = new Intent(context, MainActivity.class);
        Notification noti = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 131314, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("We Miss You!")
                .setContentText("Please play our game again soon.")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_calendar)
                .setTicker("We Miss You! Please come back and play our game again soon.")
//                .setWhen(System.currentTimeMillis())
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(131315, noti);
    }
}
