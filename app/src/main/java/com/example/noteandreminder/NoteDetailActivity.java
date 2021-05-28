package com.example.noteandreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.noteandreminder.Module.ColorCode;
import com.example.noteandreminder.Module.Note;
import com.example.noteandreminder.Module.Reminder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.noteandreminder.MainActivity.listColor;

public class NoteDetailActivity extends AppCompatActivity {
    private ConstraintLayout note_detail;
    private ImageView note_detail_back, note_detail_save, note_detail_theme;
    private EditText note_detail_title, note_detail_content;
    public Typeface OpenSans_bold, OpenSans_regular;
    private int selected_themeID = 1;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference("note");;

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
                String title = note_detail_title.getText().toString();
                String content = note_detail_content.getText().toString();
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Title or content can't be empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                //Write to DB
                String key = ref.push().getKey();
                ref.child(key).setValue(new Note(title, content, selected_themeID)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(NoteDetailActivity.this, MainActivity.class));
                    }
                });
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

        //Set Font Family for Edittext
        note_detail_title.setTypeface(OpenSans_bold);
        note_detail_content.setTypeface(OpenSans_regular);

        //Register context menu
        this.registerForContextMenu(note_detail_theme);

        //Get Intent Data
        Intent IntentData = getIntent();
        if (IntentData.getStringExtra("state").equals("new")) {
            note_detail_title.setText("");
            note_detail_content.setText("");
            note_detail.setBackgroundColor(Color.parseColor(listColor.get(3).getBackgroundColorCode()));
            note_detail_title.setTextColor(Color.parseColor(listColor.get(3).getContentColorCode()));
            note_detail_content.setTextColor(Color.parseColor(listColor.get(3).getContentColorCode()));
        }
        if (IntentData.getStringExtra("state").equals("edit")) {
            Note data = (Note) IntentData.getSerializableExtra("data");
            note_detail_title.setText(data.getNote_title());
            note_detail_content.setText(data.getNote_content());
            note_detail.setBackgroundColor(Color.parseColor(listColor.get(data.getThemeID()).getBackgroundColorCode()));
            note_detail_title.setTextColor(Color.parseColor(listColor.get(data.getThemeID()).getContentColorCode()));
            note_detail_content.setTextColor(Color.parseColor(listColor.get(data.getThemeID()).getContentColorCode()));
        }
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
        selected_themeID = item.getItemId();
        return true;
    }
}