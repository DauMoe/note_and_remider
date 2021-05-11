package com.example.noteandreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.noteandreminder.Module.ColorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoteDetailActivity extends AppCompatActivity {
    private ConstraintLayout note_detail;
    private ImageView note_detail_back, note_detail_save, note_detail_theme;
    private EditText note_detail_title, note_detail_content;
    public Typeface OpenSans_bold, OpenSans_regular;
    public List<ColorCode> listColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        OpenSans_bold = ResourcesCompat.getFont(getApplicationContext(), R.font.opensans_bold);
        OpenSans_regular = ResourcesCompat.getFont(getApplicationContext(), R.font.opensans_regular);
        initColor();
        initVariable();
        goBack();
        chooseTheme();
        saveNote();
    }

    private void initColor() {
        listColor = new ArrayList<>();
        listColor.add(new ColorCode(0, "Pink", "#FDC1CF", "#B80202"));
        listColor.add(new ColorCode(1, "Yellow", "#FFEDAD", "#D6691A"));
        listColor.add(new ColorCode(2, "Blue", "#A3F8FD", "#167BD9"));
        listColor.add(new ColorCode(3, "White", "#FFFFFF", "#45415F"));
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
        note_detail = findViewById(R.id.note_detail);

        //Default theme
        note_detail.setBackgroundColor(Color.parseColor(listColor.get(3).getBackgroundColorCode()));
        note_detail_title.setTextColor(Color.parseColor(listColor.get(3).getContentColorCode()));
        note_detail_content.setTextColor(Color.parseColor(listColor.get(3).getContentColorCode()));

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
        for (int i=0; i<listColor.size(); i++) {
            menu.add(0, listColor.get(i).getID(), 0,listColor.get(i).getNameColor());
        }
    }

    //Change theme
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        note_detail.setBackgroundColor(Color.parseColor(listColor.get(item.getItemId()).getBackgroundColorCode()));
        note_detail_title.setTextColor(Color.parseColor(listColor.get(item.getItemId()).getContentColorCode()));
        note_detail_content.setTextColor(Color.parseColor(listColor.get(item.getItemId()).getContentColorCode()));
        return true;
    }
}