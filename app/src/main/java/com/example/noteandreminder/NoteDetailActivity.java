package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class NoteDetailActivity extends AppCompatActivity {
    private ImageView note_detail_back;
    private EditText note_detail_title, note_detail_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initVariable();
        goBack();
    }

    private void goBack() {
        note_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initVariable() {
        note_detail_back = findViewById(R.id.note_detail_back);
        note_detail_title = findViewById(R.id.note_detail_title);
        note_detail_content = findViewById(R.id.note_detail_content);
    }
}