package com.example.noteandreminder.Module;

import android.widget.TextView;

public class Note {
    private String note_title, note_content;
    private int ThemeID;

    public Note(String note_title, String note_content, int themeID) {
        this.note_title = note_title;
        this.note_content = note_content;
        ThemeID = themeID;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    public int getThemeID() {
        return ThemeID;
    }

    public void setThemeID(int themeID) {
        ThemeID = themeID;
    }
}
