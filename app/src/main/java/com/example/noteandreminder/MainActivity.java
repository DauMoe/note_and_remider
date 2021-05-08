package com.example.noteandreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.noteandreminder.Adapter.TabLayoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabLayout main_tablayout;
    private ViewPager main_viewpager;
    private TabLayoutAdapter adapter;
    private FloatingActionButton main_fab, fab_note, fab_reminder;
    public static Animation rotate_out_fab, rotate_in_fab, to_bottom, to_top;
    private boolean fab_clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        initAnimation();
        hanldingFABEvemt();
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
                Intent AddNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(AddNoteIntent);
            }
        });

        //Add Reminder FAB Hanlde
        fab_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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