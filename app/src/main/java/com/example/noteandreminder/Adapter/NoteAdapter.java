package com.example.noteandreminder.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.Fragment.NoteFragment;
import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.MainActivity;
import com.example.noteandreminder.Module.ColorCode;
import com.example.noteandreminder.Module.Note;
import com.example.noteandreminder.NoteDetailActivity;
import com.example.noteandreminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.noteandreminder.MainActivity.listColor;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> data;
    private Context context;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.NOTE_DB_PATH);

    public NoteAdapter(Context c){
        this.context = c;
    }

    public void setData (List<Note> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_preview, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note_preview_item = data.get(position);
        if (note_preview_item == null) return;
        holder.note_preview_title.setText(note_preview_item.getNote_title());
        holder.note_preview_content.setText(note_preview_item.getNote_content());
        holder.note_preview_title.setTextColor(Color.parseColor(listColor.get(note_preview_item.getThemeID()).getContentColorCode()));
        holder.note_preview_content.setTextColor(Color.parseColor(listColor.get(note_preview_item.getThemeID()).getContentColorCode()));
        holder.note_preview.setBackgroundColor(Color.parseColor(listColor.get(note_preview_item.getThemeID()).getBackgroundColorCode()));

        holder.note_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoteDetailIntent = new Intent(context, NoteDetailActivity.class);
                NoteDetailIntent.putExtra("state", "edit");
                NoteDetailIntent.putExtra("data", note_preview_item);
                context.startActivity(NoteDetailIntent);
            }
        });

        DialogInterface.OnClickListener deleteNoteOrNot = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        System.out.println("Delete note at: "+position);
                        ref.child(note_preview_item.getNote_id()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Deleted!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        holder.note_preview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete note?")
                        .setPositiveButton("Yes", deleteNoteOrNot).setNegativeButton("No", deleteNoteOrNot).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public void addNoteItem(Note c) {
        data.add(c);
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView note_preview_title, note_preview_content;
        private CardView note_preview;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note_preview_title = itemView.findViewById(R.id.note_preview_title);
            note_preview_content = itemView.findViewById(R.id.note_preview_content);
            note_preview = itemView.findViewById(R.id.note_preview);
        }
    }
}
