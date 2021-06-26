package com.example.noteandreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteandreminder.Module.ColorCode;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.noteandreminder.MainActivity.listColor;

public class ColorPickerActivity extends AppCompatActivity {
    private TextView title, background;
    private ImageView save, cancel;
    private LinearLayout preview_bg, bg_picker;
    private EditText theme_name;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GlobalDefine.THEME_PATH);
    private String bg_code, content_code;
    private boolean isChooseTitle = false, isChooseBg= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
    }

    @Override
    protected void onStart() {
        super.onStart();
        title       = findViewById(R.id.preview_title);
        background  = findViewById(R.id.preview_background);
        save        = findViewById(R.id.save_color);
        cancel      = findViewById(R.id.back);
        preview_bg  = findViewById(R.id.preview_bg);
        bg_picker   = findViewById(R.id.bg_picker);
        theme_name  = findViewById(R.id.theme_name);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addColor(false);
            }
        });

        bg_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addColor(true);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = theme_name.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Set theme name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isChooseBg || !isChooseTitle) {
                    Toast.makeText(getApplicationContext(), "Set theme name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                isChooseBg      = false;
                isChooseTitle   = false;
                String key      = ref.push().getKey();
                ref.child(key).setValue(new ColorCode(listColor.size(), name, bg_code, content_code)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(ColorPickerActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Save failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void addColor(boolean isBackgroundColorPicker) {
        ColorPickerDialogBuilder
                .with(ColorPickerActivity.this)
                .setTitle((isBackgroundColorPicker)? "Choose background color": "Choose title color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .initialColor(0xffffffff)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        Toast.makeText(getApplicationContext(), "Color: "+ Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Preview", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        if (isBackgroundColorPicker) {
                            isChooseBg      = true;
                            bg_code         = "#"+Integer.toHexString(selectedColor);
                            preview_bg.setBackgroundColor(Color.parseColor(String.valueOf("#"+Integer.toHexString(selectedColor))));
                        } else {
                            isChooseTitle   = true;
                            content_code    = "#"+Integer.toHexString(selectedColor);
                            title.setTextColor(Color.parseColor(String.valueOf("#"+Integer.toHexString(selectedColor))));
                            background.setTextColor(Color.parseColor(String.valueOf("#"+Integer.toHexString(selectedColor))));
                        }
                        Toast.makeText(getApplicationContext(), "Color: "+ Integer.toHexString(selectedColor), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}