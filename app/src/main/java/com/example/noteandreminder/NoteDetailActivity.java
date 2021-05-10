package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class NoteDetailActivity extends AppCompatActivity {
    private ImageView note_detail_back, note_detail_save;
    private EditText note_detail_title, note_detail_content;
    public Typeface OpenSans_bold, OpenSans_regular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        OpenSans_bold = ResourcesCompat.getFont(getApplicationContext(), R.font.opensans_bold);
        OpenSans_regular = ResourcesCompat.getFont(getApplicationContext(), R.font.opensans_regular);
        initVariable();
        goBack();
        saveNote();
    }

    private void saveNote() {
        note_detail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save note
            }
        });
    }

    private void goBack() {
        note_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to previous intent
                onBackPressed();
            }
        });
    }


    private void initVariable() {
        note_detail_back = findViewById(R.id.note_detail_back);
        note_detail_save = findViewById(R.id.note_detail_save);
        note_detail_title = findViewById(R.id.note_detail_title);
        note_detail_content = findViewById(R.id.note_detail_content);

        //Set Font Family for Edittext
        note_detail_title.setTypeface(OpenSans_bold);
        note_detail_content.setTypeface(OpenSans_regular);
    }
}