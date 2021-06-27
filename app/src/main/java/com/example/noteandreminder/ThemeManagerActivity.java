package com.example.noteandreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.noteandreminder.Adapter.NoteAdapter;
import com.example.noteandreminder.Adapter.ThemeAdapter;
import com.example.noteandreminder.Module.ColorCode;
import com.example.noteandreminder.Module.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThemeManagerActivity extends AppCompatActivity {
    private RecyclerView theme_rcv;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.THEME_PATH);
    private List<ColorCode> data = new ArrayList<>();
    private ThemeAdapter theme_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        theme_rcv                   = findViewById(R.id.theme_manager_rcv);
        theme_adapter               = new ThemeAdapter(ThemeManagerActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);

        theme_rcv.setLayoutManager(manager);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    ColorCode chid_item = item.getValue(ColorCode.class);
                    data.add(chid_item);
                }
                theme_adapter.setData(data);
                theme_rcv.setAdapter(theme_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "DB error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}