package com.example.noteandreminder.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.Module.NoteContainer;
import com.example.noteandreminder.R;

import java.util.List;

public class NoteContainerAdapter extends RecyclerView.Adapter<NoteContainerAdapter.NoteContainerViewHolder> {
    private Context C;
    private List<NoteContainer> listofNotes;

    public NoteContainerAdapter(Context c) {
        this.C = c;
    }

    public void setData(List<NoteContainer> data) {
        this.listofNotes = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteContainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_sticky, parent, false);
        return new NoteContainerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteContainerViewHolder holder, int position) {
        NoteContainer sticky_note = listofNotes.get(position);
        if (sticky_note == null) return;
        holder.note_container_title.setText(sticky_note.getTitle());
        NoteItemAdapter adapter = new NoteItemAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(C, RecyclerView.VERTICAL, false);
        holder.note_container_content.setLayoutManager(manager);
        adapter.setData(sticky_note.getContent());
        holder.note_container_content.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if (listofNotes != null) return listofNotes.size();
        return 0;
    }

    public class NoteContainerViewHolder extends RecyclerView.ViewHolder {
        private TextView note_container_title;
        private RecyclerView note_container_content;

        public NoteContainerViewHolder(@NonNull View itemView) {
            super(itemView);
            note_container_title = itemView.findViewById(R.id.note_container_title);
            note_container_content = itemView.findViewById(R.id.note_container_content);
        }
    }
}
