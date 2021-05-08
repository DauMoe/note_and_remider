package com.example.noteandreminder.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteandreminder.Adapter.NoteContainerAdapter;
import com.example.noteandreminder.Module.NoteContainer;
import com.example.noteandreminder.Module.NoteItem;
import com.example.noteandreminder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView note_recycle;
    private NoteContainerAdapter note_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        note_recycle = v.findViewById(R.id.note_recycleView);
        note_adapter = new NoteContainerAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        note_recycle.setLayoutManager(manager);
        note_adapter.setData(fetchListItem());
        note_recycle.setAdapter(note_adapter);
        return v;
    }

    private List<NoteContainer> fetchListItem() {
        List<NoteContainer> data = new ArrayList<>();
        List<NoteItem> list = new ArrayList<>();
        String title1 = "Mar 04, 2021";
        String title2 = "Jun 12, 2022";
        list.add(new NoteItem(false, "this is test content"));
        list.add(new NoteItem(true, "this is test content1"));
        list.add(new NoteItem(false, "this is test content3"));
        data.add(new NoteContainer(title1, list));
        data.add(new NoteContainer(title2, list));
        return data;
    }
}