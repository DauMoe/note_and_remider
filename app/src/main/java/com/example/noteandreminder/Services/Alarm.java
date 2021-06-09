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
        Intent alarm_intent = new Intent(context, TimeupActivity.class);
        alarm_intent.putExtra("alarm_data", data);
        context.startActivity(alarm_intent);

    }
}
