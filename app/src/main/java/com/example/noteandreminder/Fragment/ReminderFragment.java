package com.example.noteandreminder.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.noteandreminder.Adapter.ReminderGroupAdapter;
import com.example.noteandreminder.Adapter.ReminderItemAdapter;
import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.Module.ReminderGroup;
import com.example.noteandreminder.R;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    private RecyclerView reminder_frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reminder, container, false);
        reminder_frag = v.findViewById(R.id.reminder_frag);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        ReminderGroupAdapter adapter = new ReminderGroupAdapter(getContext());
//        ReminderItemAdapter adapter = new ReminderItemAdapter(getContext());
        reminder_frag.setLayoutManager(manager);
        adapter.setData(fetchRemidnerData());
        reminder_frag.setAdapter(adapter);
        return v;
    }


    private List<ReminderGroup> fetchRemidnerData() {
        List<ReminderGroup> data = new ArrayList<>();
        List<Reminder> child_data = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            child_data.add(new Reminder("Reminder1", "déc", 2, LocalTime.now(), false));
            child_data.add(new Reminder("Reminder2", "", 1, LocalTime.now(), true));
        }
        data.add(new ReminderGroup("Today", child_data));
        return data;
    }
}