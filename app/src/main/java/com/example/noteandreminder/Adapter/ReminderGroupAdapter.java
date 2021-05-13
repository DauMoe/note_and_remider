package com.example.noteandreminder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.Module.ReminderGroup;
import com.example.noteandreminder.R;

import java.util.List;

public class ReminderGroupAdapter extends RecyclerView.Adapter<ReminderGroupAdapter.ReminderGroupViewHolder> {
    private Context context;
    private List<ReminderGroup> data;

    public ReminderGroupAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ReminderGroup> x) {
        this.data = x;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReminderGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_group, parent, false);
        return new ReminderGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderGroupViewHolder holder, int position) {
        ReminderGroup item = data.get(position);
        if(item == null) return;
        holder.reminder_group_date.setText(String.valueOf(item.getDate()));
        ReminderItemAdapter adapter = new ReminderItemAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.reminder_group_content.setLayoutManager(manager);
        adapter.setData(item.getData());
        holder.reminder_group_content.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public class ReminderGroupViewHolder extends RecyclerView.ViewHolder {
        private TextView reminder_group_date;
        private RecyclerView reminder_group_content;

        public ReminderGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder_group_date = itemView.findViewById(R.id.reminder_group_day);
            reminder_group_content = itemView.findViewById(R.id.reminder_group_content);
        }
    }
}
