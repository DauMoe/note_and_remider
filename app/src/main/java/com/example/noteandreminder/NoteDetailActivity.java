package com.example.noteandreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class NoteDetailActivity extends AppCompatActivity {
    private ConstraintLayout note_detail;
    private ImageView note_detail_back, note_detail_save, note_detail_theme;
    private EditText note_detail_title, note_detail_content;
    public Typeface OpenSans_bold, OpenSans_regular;
    public String[] colorArr = new String[] {"Pink", "Black", "White"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        OpenSans_bold = ResourcesCompat.getFont(getApplicationContext(), R.font.opensans_bold);
        OpenSans_regular = ResourcesCompat.getFont(getApplicationContext(), R.font.opensans_regular);
        initVariable();
        goBack();
        chooseTheme();
        saveNote();
    }



    private void chooseTheme() {
        note_detail_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(v);
            }
        });
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
        note_detail_theme = findViewById(R.id.note_detail_theme);
        note_detail_title = findViewById(R.id.note_detail_title);
        note_detail_content = findViewById(R.id.note_detail_content);

        //Set Font Family for Edittext
        note_detail_title.setTypeface(OpenSans_bold);
        note_detail_content.setTypeface(OpenSans_regular);

        //Register context menu
        this.registerForContextMenu(note_detail_theme);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose theme!");
        menu.add(0, v.getId(), 0,"Yellow");
        menu.add(0, v.getId(), 0,"Green");
        menu.add(0, v.getId(), 0,"Blue");
        menu.add(0, v.getId(), 0,"White");
    }

    //Change theme
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() == "Yellow") {

        }
        return true;
    }
}