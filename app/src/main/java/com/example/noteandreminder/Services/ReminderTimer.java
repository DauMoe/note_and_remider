package com.example.noteandreminder.Services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTimer extends Service {
//    Doc: https://stackoverflow.com/questions/30525784/android-keep-service-running-when-app-is-killed
    public static final long DELAY_TIMER = 30000L; //ms ~3s
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.REMINDER_DB_PATH);
    private Map<String, String> timeStamp = new HashMap<>();
    private Calendar c = Calendar.getInstance();

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            //Check here
            String currentTime = c.get(Calendar.HOUR) + ":"+ c.get(Calendar.MINUTE);
            System.out.println(currentTime);
            if (timeStamp.containsKey(currentTime)) {
                Toast.makeText(getApplicationContext(), timeStamp.get(currentTime), Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        readDB();
        if (timeStamp.size()>0) startTimer();
    }

    private void readDB() {
        timeStamp.clear();
        //Query all event TODAY
        String today = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        ref.orderByChild(today).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()) {
                    Reminder chid_item = item.getValue(Reminder.class);
                    timeStamp.put(chid_item.getReminder_time(), chid_item.getReminder_title());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startTimer() {
        Timer timer = new Timer(GlobalDefine.REMINDER);
        timer.schedule(timerTask, 0, DELAY_TIMER);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
