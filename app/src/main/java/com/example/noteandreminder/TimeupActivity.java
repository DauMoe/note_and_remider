package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.noteandreminder.Module.Note;
import com.example.noteandreminder.Module.Reminder;

public class TimeupActivity extends AppCompatActivity {
    private Ringtone tone;
    private Button timeup_stop;
    private TextView timeup_title, timeup_desc;
    private Reminder data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeup);
        Intent IntentData = getIntent();
        data = (Reminder) IntentData.getSerializableExtra("alarm_data");
        tone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        tone.play();
        timeup_stop = findViewById(R.id.timeup_stop);
        timeup_title = findViewById(R.id.timeup_title);
        timeup_desc = findViewById(R.id.timeup_desc);

        timeup_title.setText(String.valueOf(data.getReminder_title()));
        timeup_desc.setText(String.valueOf(data.getReminder_desc()));
//        timeup_title.setText("This is title");
        timeup_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tone.stop();
                startActivity(new Intent(TimeupActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}