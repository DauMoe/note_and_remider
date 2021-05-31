package com.example.noteandreminder.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.R;

import java.util.List;

public class ReminderItemAdapter extends RecyclerView.Adapter<ReminderItemAdapter.ReminderItemViewHolder> {
    private List<Reminder> data;

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
        holder.reminder_detail_time.setText(String.valueOf(item.getReminder_time()));
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
