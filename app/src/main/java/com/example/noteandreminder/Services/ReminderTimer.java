package com.example.noteandreminder.Services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.Module.Reminder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTimer extends Service {
//    Doc: https://stackoverflow.com/questions/30525784/android-keep-service-running-when-app-is-killed
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.REMINDER_DB_PATH);
    private Map<String, String> timeStamp = new HashMap<>();
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss"); //24-hour format
    private Date now, time_stamp;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timeStamp.clear();
        //Query all event TODAY
        String today = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
        ref.orderByChild("reminder_date").equalTo(today).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                now = new Date();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Reminder chid_item = item.getValue(Reminder.class);
                    try {
                        time_stamp = formatter.parse(today+" "+chid_item.getReminder_time()+":00");
                        if (chid_item.isReminder_completed()==false && time_stamp.getTime()>now.getTime()) {
                            Log.v(GlobalDefine.TAG, "A timer is set!");
                            new Timer().schedule(new Alarm(getApplicationContext(), chid_item), time_stamp.getTime()-now.getTime());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
