package com.example.noteandreminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.noteandreminder.Adapter.TabLayoutAdapter;
import com.example.noteandreminder.Module.ColorCode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TabLayout main_tablayout;
    private ViewPager main_viewpager;
    private TabLayoutAdapter adapter;
    private FloatingActionButton main_fab, fab_note, fab_reminder;
    public static Animation rotate_out_fab, rotate_in_fab, to_bottom, to_top;
    private boolean fab_clicked = false;
    public static List<ColorCode> listColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initColor();
        initVariable();
        initAnimation();
        hanldingFABEvemt();
    }

    private void initColor() {
        listColor = new ArrayList<>();
        listColor.add(new ColorCode(0, "Pink", "#FDC1CF", "#B80202"));
        listColor.add(new ColorCode(1, "Yellow", "#FFEDAD", "#D6691A"));
        listColor.add(new ColorCode(2, "Blue", "#A3F8FD", "#167BD9"));
        listColor.add(new ColorCode(3, "White", "#FFFFFF", "#45415F"));
    }

    private void initAnimation() {
        rotate_out_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_out_fab);
        rotate_in_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in_fab);
        to_bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_to_bottom);
        to_top = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_to_top);
    }

    private void hanldingFABEvemt() {
        //Main FAB handle
        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_clicked = !fab_clicked;
                System.out.println("Clicked: "+fab_clicked);
                if (fab_clicked) {
                    v.startAnimation(rotate_out_fab);
                    fab_note.startAnimation(to_top);
                    fab_reminder.startAnimation(to_top);
                    fab_note.setVisibility(View.VISIBLE);
                    fab_reminder.setVisibility(View.VISIBLE);
                } else {
                    v.startAnimation(rotate_in_fab);
                    fab_note.startAnimation(to_bottom);
                    fab_reminder.startAnimation(to_bottom);
                    fab_note.setVisibility(View.GONE);
                    fab_reminder.setVisibility(View.GONE);
                }
            }
        });

        //Add Note FAB Hanlde
        fab_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoteDetailIntent = new Intent(MainActivity.this, NoteDetailActivity.class);
                NoteDetailIntent.putExtra("state", "new");
                startActivity(NoteDetailIntent);
            }
        });

        //Add Reminder FAB Hanlde
        fab_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderDialog();
            }
        });
    }

    private void addReminderDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.create_reminder, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("New Reminder!");
        builder.setView(v);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        //Create new reminder
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "This is a message", Toast.LENGTH_LONG).show();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initVariable() {
        main_tablayout = findViewById(R.id.main_tablayout);
        main_viewpager = findViewById(R.id.main_viewpager);
        main_fab = findViewById(R.id.main_fab);
        fab_note = findViewById(R.id.fab_notes);
        fab_reminder = findViewById(R.id.fab_reminder);

        //setup viewpager
        adapter = new TabLayoutAdapter(getSupportFragmentManager(), TabLayoutAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        main_viewpager.setAdapter(adapter);
        main_viewpager.setPageTransformer(true, View::setRotationX);

        //setup tablayout
        main_tablayout.setupWithViewPager(main_viewpager);
        main_tablayout.getTabAt(0).setIcon(R.drawable.ic_note);
        main_tablayout.getTabAt(1).setIcon(R.drawable.ic_reminder);
    }
}