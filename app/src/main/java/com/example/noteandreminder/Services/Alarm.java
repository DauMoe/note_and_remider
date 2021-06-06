package com.example.noteandreminder.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.noteandreminder.GlobalDefine;
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ALARM_NOTI_ID)
                .setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle("It's time!")
                .setContentText(noti_content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1000, builder.build());
    }
}
