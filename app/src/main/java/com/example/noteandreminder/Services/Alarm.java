package com.example.noteandreminder.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.TimeupActivity;

import java.util.TimerTask;

public class Alarm extends TimerTask {
    private Context context;
    private Reminder data;

    public Alarm(Context context, Reminder x) {
        this.context = context;
        this.data = x;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        Log.v(GlobalDefine.TAG, "Say hi");
//        Intent mainIntent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
//        Notification noti = new Notification.Builder(context)
//                .setAutoCancel(true)
//                .setContentIntent(PendingIntent.getActivity(context, 131314, mainIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT))
//                .setContentTitle("We Miss You!")
//                .setContentText("Please play our game again soon.")
//                .setContentIntent(pendingIntent)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setSmallIcon(R.drawable.ic_calendar)
//                .setTicker("We Miss You! Please come back and play our game again soon.")
//                .setWhen(System.currentTimeMillis())
//                .build();
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(131315, noti);
        Intent alarm_intent = new Intent(context, TimeupActivity.class);
        alarm_intent.putExtra("alarm_data", data);
        context.startActivity(alarm_intent);

    }
}
