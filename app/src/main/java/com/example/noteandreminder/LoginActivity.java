package com.example.noteandreminder;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText login_username, login_password;
    private TextView login_regis;
    private LinearLayout btn_login;
    protected FirebaseAuth auth;

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
                String email = login_username.getText().toString();
                String pass = login_password.getText().toString();
                if (email.isEmpty()) {
                    login_username.setError("Email is required!");
                    return;
                }
                if (pass.isEmpty()) {
                    login_password.setError("Password is required!");
                    return;
                }
                if (pass.length() < 6) {
                    login_password.setError("Password must be longer than 6!");
                    return;
                }
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent main_activity = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(main_activity);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void initVariable() {
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_regis = findViewById(R.id.login_regis);
        btn_login = findViewById(R.id.login_btn_area);
        auth = FirebaseAuth.getInstance();
        login_regis.setPaintFlags(login_regis.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}