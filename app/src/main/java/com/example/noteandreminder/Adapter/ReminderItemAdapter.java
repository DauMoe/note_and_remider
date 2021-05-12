package com.example.noteandreminder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.Module.Reminder;
import com.example.noteandreminder.R;

import java.util.List;

public class ReminderItemAdapter extends RecyclerView.Adapter<ReminderItemAdapter.ReminderItemViewHolder> {
    private Context context;
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
        holder.reminder_detail_time.setText(String.valueOf(item.getRemind_time()));
        holder.reminder_detail_completed.setChecked(item.isReminder_completed());
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public class ReminderItemViewHolder extends RecyclerView.ViewHolder {
        private TextView remider_detail_title, reminder_detail_desc, reminder_detail_time;
        private CheckBox reminder_detail_completed;
        public ReminderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            remider_detail_title = itemView.findViewById(R.id.reminder_detail_title);
            reminder_detail_desc = itemView.findViewById(R.id.reminder_detail_desc);
            reminder_detail_completed = itemView.findViewById(R.id.reminder_detail_checkbox);
            reminder_detail_time = itemView.findViewById(R.id.remider_detail_time);
        }
    }
}
