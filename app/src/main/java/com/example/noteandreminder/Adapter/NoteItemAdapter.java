package com.example.noteandreminder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.Module.NoteItem;
import com.example.noteandreminder.R;

import java.util.List;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteViewHolder> {
    private List<NoteItem> listNotes;
//    private Context context;

//    public NoteItemAdapter(Context context) {
//        this.context = context;
//    }

    public void setData (List<NoteItem> data) {
        this.listNotes = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteItem note_item = listNotes.get(position);
        if (note_item == null) return;
        holder.note_btn_status.setChecked(note_item.getNoteComplete());
        holder.note_content.setText(note_item.getNote_content());
    }

    @Override
    public int getItemCount() {
        if (listNotes != null) return listNotes.size();
        return 0;
    }

    public void addNoteItem(NoteItem c) {
        listNotes.add(c);
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private CheckBox note_btn_status;
        private TextView note_content;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note_btn_status = itemView.findViewById(R.id.note_checkbox);
            note_content = itemView.findViewById(R.id.note_content);
        }
    }
}
