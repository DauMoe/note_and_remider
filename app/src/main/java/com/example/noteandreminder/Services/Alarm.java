package com.example.noteandreminder.Services;

import android.content.Context;

import java.util.TimerTask;

public class Alarm extends TimerTask {
    private Context context;
    private String noti_content;

    public Alarm(Context context, String noti_content) {
        this.context = context;
        this.noti_content = noti_content;
    }

    @Override
    public void run() {
        System.out.println(noti_content);
    }
}
