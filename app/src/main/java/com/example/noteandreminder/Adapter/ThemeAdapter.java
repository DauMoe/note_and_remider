package com.example.noteandreminder.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteandreminder.GlobalDefine;
import com.example.noteandreminder.Module.ColorCode;
import com.example.noteandreminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {
    private List<ColorCode> data;
    private Context context;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.THEME_PATH);

    public ThemeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ColorCode> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_item, parent, false);
        return new ThemeAdapter.ThemeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        ColorCode item = data.get(position);
        if (item == null) return;

        holder.theme_name.setText(item.getNameColor());
        holder.theme_name.setTextColor(Color.parseColor(item.getContentColorCode()));
        holder.theme_content.setTextColor(Color.parseColor(item.getContentColorCode()));
        holder.theme_bg.setBackgroundColor(Color.parseColor(item.getBackgroundColorCode()));

        DialogInterface.OnClickListener deleteThemeOrNot = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        System.out.println("Delete theme at: "+position);
                        ref.child(String.valueOf(item.getID())).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        holder.theme_bg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setMessage("Delete theme?")
                        .setPositiveButton("Yes", deleteThemeOrNot).setNegativeButton("No", deleteThemeOrNot).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout theme_bg;
        private TextView theme_name, theme_content;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            theme_bg        = itemView.findViewById(R.id.theme_bg);
            theme_name      = itemView.findViewById(R.id.theme_name);
            theme_content   = itemView.findViewById(R.id.theme_content);
        }
    }
}
