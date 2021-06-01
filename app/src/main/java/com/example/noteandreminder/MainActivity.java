package com.example.noteandreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.noteandreminder.Adapter.TabLayoutAdapter;
import com.example.noteandreminder.Module.ColorCode;
import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.Services.ReminderTimer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TabLayout main_tablayout;
    private ViewPager main_viewpager;
    private TabLayoutAdapter adapter;
    private FloatingActionButton main_fab, fab_note, fab_reminder;
    private boolean fab_clicked = false;
    private ReminderTimer R_timer;

    //List color code
    public static List<ColorCode> listColor;
    private int day, month, year, hour, min;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference("reminder");

    //Animation
    public static Animation rotate_out_fab, rotate_in_fab, to_bottom, to_top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initColor();
        initAnimation();
        initVariable();
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
        EditText reminder_create_datepicker = v.findViewById(R.id.reminder_create_datepicker);
        EditText reminder_create_timepicker = v.findViewById(R.id.reminder_create_timepicker);
        EditText reminder_create_title = v.findViewById(R.id.reminder_create_title);

        //Date picker
        reminder_create_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                DatePickerDialog datepicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        reminder_create_datepicker.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });

        //Time picker
        reminder_create_timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                min = c.get(Calendar.MINUTE);
                TimePickerDialog timepicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        reminder_create_timepicker.setText(hourOfDay+":"+minute);
                    }
                }, hour, min, true);
                timepicker.show();
            }
        });

        //Setup dialog
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
                String key = ref.push().getKey();
                String title = reminder_create_title.getText().toString();
                String datereminder = reminder_create_datepicker.getText().toString();
                String timereminder = reminder_create_timepicker.getText().toString();
                ref.child(key).setValue(new Reminder(key, title, "", datereminder, timereminder, 1, false)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Create fail!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
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

        //Background services
        R_timer = new ReminderTimer();
        startService(new Intent(MainActivity.this, ReminderTimer.class));

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