package com.example.noteandreminder.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteandreminder.Adapter.ReminderGroupAdapter;
import com.example.noteandreminder.Adapter.ReminderItemAdapter;
import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.Module.Note;
import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.Module.ReminderGroup;
import com.example.noteandreminder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderFragment.
     */
    // TODO: Rename and change types and number of parameters


    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public List<Reminder> deepCloneList(List<Reminder> x) {
        List<Reminder> y = new ArrayList<>();
        for (Reminder i : x) {
            y.add(i);
        }
        return y;
    }

    private RecyclerView reminder_frag;
    protected List<ReminderGroup> list = new ArrayList<>();
    private FirebaseDatabase mDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init DB
        mDB = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDB.getReference(GlobalDefine.REMINDER_DB_PATH);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reminder, container, false);
        reminder_frag = v.findViewById(R.id.reminder_frag);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        ReminderGroupAdapter adapter = new ReminderGroupAdapter(getContext());
        reminder_frag.setLayoutManager(manager);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, List<Reminder>> data = new HashMap<>();
                List<Reminder> tempArr = new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    tempArr.clear();
                    Reminder chid_item = item.getValue(Reminder.class);
                    if (!data.containsKey(chid_item.getReminder_date())) {
                        tempArr.add(chid_item);
                        data.put(chid_item.getReminder_date(), deepCloneList(tempArr));
                    } else {
                        tempArr = data.get(chid_item.getReminder_date());
                        tempArr.add(chid_item);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            data.replace(chid_item.getReminder_date(), deepCloneList(tempArr));
                        }
                    }
                }
                //Convert hashmap to list
                list.clear();
                for (Map.Entry<String, List<Reminder>> i : data.entrySet()) {
                    list.add(new ReminderGroup(i.getKey(), i.getValue()));
                }
                adapter.setData(list);
                reminder_frag.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}