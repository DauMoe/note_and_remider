package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {
    private static int WELCOME_SCREEN_TIME = 4000; //ms
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        logo = findViewById(R.id.logo);
        Animation blink_logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_logo);

        logo.startAnimation(blink_logo);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                startActivity(login_intent);
//                finish();
//            }
//        }, WELCOME_SCREEN_TIME);
    }
}