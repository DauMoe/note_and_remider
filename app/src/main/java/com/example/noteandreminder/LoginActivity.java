package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText login_username, login_password;
    private TextView login_regis;
    private LinearLayout btn_login;

    //Set title bar to invisible, edit in theme.xml to "DayNight.NoActionBar"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVariable();
        handling_event();
    }

    private void handling_event() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login condition here
                if (true) {
                    Intent main_activity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(main_activity);
                    finish();
                }
            }
        });
    }

    private void initVariable() {
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_regis = findViewById(R.id.login_regis);
        btn_login = findViewById(R.id.login_btn_area);

        login_regis.setPaintFlags(login_regis.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}