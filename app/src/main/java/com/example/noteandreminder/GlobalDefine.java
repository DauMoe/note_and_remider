package com.example.noteandreminder;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noteandreminder.Module.ColorCode;

import java.util.ArrayList;
import java.util.List;

public class GlobalDefine {
    //DB path
    public static final String REMINDER_DB_PATH = "reminder";
    public static final String NOTE_DB_PATH = "note";

    //Service
    public static final String REMINDER = "check_reminder_task";

    //Noti
    public static final String ALARM_NOTI_ID = "background_alarm_noti";
}
