package com.example.noteandreminder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.MainActivity;
import com.example.noteandreminder.Module.Note;
import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.NoteDetailActivity;
import com.example.noteandreminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.noteandreminder.MainActivity.listColor;

public class ReminderItemAdapter extends RecyclerView.Adapter<ReminderItemAdapter.ReminderItemViewHolder> {
    private List<Reminder> data;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.REMINDER_DB_PATH);

    public void setData(List<Reminder> x) {
        this.data = x;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReminderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new ReminderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderItemViewHolder holder, int position) {
        Reminder item = data.get(position);
        if (item == null) return;
        holder.remider_detail_title.setText(item.getReminder_title());
        holder.reminder_detail_desc.setText(item.getReminder_desc());
        holder.reminder_detail.setBackgroundColor(Color.parseColor(listColor.get(item.getThemeID()).getBackgroundColorCode()));
        holder.remider_detail_title.setTextColor(Color.parseColor(listColor.get(item.getThemeID()).getContentColorCode()));
        holder.reminder_detail_desc.setTextColor(Color.parseColor(listColor.get(item.getThemeID()).getContentColorCode()));

        //Display time
        DateFormat sdf = new SimpleDateFormat("kk:mm");
        try {
            Date date = sdf.parse(item.getReminder_time());
            holder.reminder_detail_time.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Checkbox
        holder.reminder_detail_completed.setChecked(item.isReminder_completed());
        if (item.isReminder_completed()) {
            holder.remider_detail_title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.reminder_detail_desc.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.reminder_detail_completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.remider_detail_title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.reminder_detail_desc.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.remider_detail_title.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                    holder.reminder_detail_desc.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                }
                ref.child(item.getReminder_id()).setValue(new Reminder(item.getReminder_id(), item.getReminder_title(), item.getReminder_desc(), item.getReminder_date(), item.getReminder_time(), item.getThemeID(), isChecked)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {}
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public class ReminderItemViewHolder extends RecyclerView.ViewHolder {
        private TextView remider_detail_title, reminder_detail_desc, reminder_detail_time;
        private CheckBox reminder_detail_completed;
        private ConstraintLayout reminder_detail;

        public ReminderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder_detail = itemView.findViewById(R.id.reminder_detail);
            remider_detail_title = itemView.findViewById(R.id.reminder_detail_title);
            reminder_detail_desc = itemView.findViewById(R.id.reminder_detail_desc);
            reminder_detail_completed = itemView.findViewById(R.id.reminder_detail_checkbox);
            reminder_detail_time = itemView.findViewById(R.id.remider_detail_time);
        }
    }
}
