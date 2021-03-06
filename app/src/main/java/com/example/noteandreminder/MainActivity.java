package com.example.noteandreminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.noteandreminder.Adapter.TabLayoutAdapter;
import com.example.noteandreminder.Module.ColorCode;
import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.Services.ReminderTimer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TabLayout main_tablayout;
    private ViewPager main_viewpager;
    private TabLayoutAdapter adapter;
    private FloatingActionButton main_fab, fab_note, fab_reminder, fab_theme;
    private Toolbar toolbar;
    private boolean fab_clicked = false;
    private int selectedThemeID=0;

    //List color code
    public static List<ColorCode> listColor;
    private int day, month, year, hour, min;
    protected DatabaseReference ref     = FirebaseDatabase.getInstance().getReference(GlobalDefine.REMINDER_DB_PATH);
    protected DatabaseReference theme   = FirebaseDatabase.getInstance().getReference(GlobalDefine.THEME_PATH);

    //Animation
    public static Animation rotate_out_fab, rotate_in_fab, to_bottom, to_top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initColor();
        initAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVariable();
        hanldingFABEvemt();
    }

    private void initColor() {
        listColor = new ArrayList<>();
        listColor.add(new ColorCode(0, "Pink", "#FDC1CF", "#B80202"));
        listColor.add(new ColorCode(1, "Yellow", "#FFEDAD", "#D6691A"));
        listColor.add(new ColorCode(2, "Blue", "#A3F8FD", "#167BD9"));
        listColor.add(new ColorCode(3, "White", "#FFFFFF", "#45415F"));
        listColor.add(new ColorCode(4, "Purple", "#DCA1DB", "#750573"));
        listColor.add(new ColorCode(5, "Green", "#73ECD0", "#03785D"));
        listColor.add(new ColorCode(6, "Chocolate", "#AA6440", "#50220A"));

        theme.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listColor.clear();
                listColor.add(new ColorCode(0, "Pink", "#FDC1CF", "#B80202"));
                listColor.add(new ColorCode(1, "Yellow", "#FFEDAD", "#D6691A"));
                listColor.add(new ColorCode(2, "Blue", "#A3F8FD", "#167BD9"));
                listColor.add(new ColorCode(3, "White", "#FFFFFF", "#45415F"));
                listColor.add(new ColorCode(4, "Purple", "#DCA1DB", "#750573"));
                listColor.add(new ColorCode(5, "Green", "#73ECD0", "#03785D"));
                listColor.add(new ColorCode(6, "Chocolate", "#AA6440", "#50220A"));
                for (DataSnapshot i: snapshot.getChildren()) {
                    ColorCode item = i.getValue(ColorCode.class);
                    listColor.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initAnimation() {
        rotate_out_fab  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_out_fab);
        rotate_in_fab   = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in_fab);
        to_bottom       = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_to_bottom);
        to_top          = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_to_top);
    }

    private void hanldingFABEvemt() {
        //Main FAB handle
        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_clicked = !fab_clicked;
//                System.out.println("Clicked: "+fab_clicked);
                if (fab_clicked) {
                    v.startAnimation(rotate_out_fab);
                    fab_note.startAnimation(to_top);
                    fab_reminder.startAnimation(to_top);
                    fab_theme.startAnimation(to_top);
                    fab_note.setVisibility(View.VISIBLE);
                    fab_reminder.setVisibility(View.VISIBLE);
                    fab_theme.setVisibility(View.VISIBLE);
                } else {
                    v.startAnimation(rotate_in_fab);
                    fab_note.startAnimation(to_bottom);
                    fab_reminder.startAnimation(to_bottom);
                    fab_theme.startAnimation(to_bottom);
                    fab_note.setVisibility(View.GONE);
                    fab_reminder.setVisibility(View.GONE);
                    fab_theme.setVisibility(View.GONE);
                }
            }
        });

        //Add Note FAB Hanlder
        fab_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoteDetailIntent = new Intent(MainActivity.this, NoteDetailActivity.class);
                NoteDetailIntent.putExtra("state", "new");
                startActivity(NoteDetailIntent);
            }
        });

        //Add Reminder FAB Hanlder
        fab_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderDialog();
            }
        });

        //Add Theme FAB Hanlder
        fab_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ColorPickerActivity.class));
            }
        });
    }

    private void addReminderDialog() {
        LayoutInflater inflater                 = getLayoutInflater();
        View v                                  = inflater.inflate(R.layout.create_reminder, null);
        AlertDialog.Builder builder             = new AlertDialog.Builder(this);
        EditText reminder_create_datepicker     = v.findViewById(R.id.reminder_create_datepicker);
        EditText reminder_create_timepicker     = v.findViewById(R.id.reminder_create_timepicker);
        EditText reminder_create_title          = v.findViewById(R.id.reminder_create_title);
        EditText reminder_create_desc           = v.findViewById(R.id.reminder_create_desc);
        Spinner reminder_create_theme           = v.findViewById(R.id.reminder_create_theme);
        List<String> theme_color                = new ArrayList<>();
        for (ColorCode i: listColor) {
            theme_color.add(i.getNameColor());
        }
        ArrayAdapter<String> adapter            = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, theme_color);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminder_create_theme.setAdapter(adapter);

        reminder_create_theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThemeID = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                        reminder_create_datepicker.setText(dayOfMonth+"/"+(month+1)+"/"+year);
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
                String desc = reminder_create_desc.getText().toString();
                String datereminder = reminder_create_datepicker.getText().toString();
                String timereminder = reminder_create_timepicker.getText().toString();
                ref.child(key)
                        .setValue(new Reminder(key, title, desc, datereminder, timereminder, selectedThemeID, false))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
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
        main_tablayout  = findViewById(R.id.main_tablayout);
        main_viewpager  = findViewById(R.id.main_viewpager);
        toolbar         = findViewById(R.id.toolbar);
        main_fab        = findViewById(R.id.main_fab);
        fab_note        = findViewById(R.id.fab_notes);
        fab_reminder    = findViewById(R.id.fab_reminder);
        fab_theme       = findViewById(R.id.fab_theme);

        //Add toolbar option
        setSupportActionBar(toolbar);

        //Background services
        startService(new Intent(MainActivity.this, ReminderTimer.class));

        //setup viewpager
        adapter         = new TabLayoutAdapter(getSupportFragmentManager(), TabLayoutAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        main_viewpager.setAdapter(adapter);
        main_viewpager.setPageTransformer(true, View::setRotationX);

        //setup tablayout
        main_tablayout.setupWithViewPager(main_viewpager);
        main_tablayout.getTabAt(0).setIcon(R.drawable.ic_note);
        main_tablayout.getTabAt(1).setIcon(R.drawable.ic_reminder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.theme_manager:
                startActivity(new Intent(MainActivity.this, ThemeManagerActivity.class));
                break;
        }
        return true;
    }
}