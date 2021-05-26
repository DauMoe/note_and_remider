package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView logo;
    protected FirebaseUser auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        logo = findViewById(R.id.logo);
        Animation blink_logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_logo);

        logo.startAnimation(blink_logo);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        if (auth == null) {
            Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(login_intent);
            finish();
        } else {
            Intent login_intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(login_intent);
            finish();
        }
    }
}