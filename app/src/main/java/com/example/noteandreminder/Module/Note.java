package com.example.noteandreminder.Module;

import android.widget.TextView;

import java.io.Serializable;

public class Note implements Serializable {
    private String note_id, note_title, note_content;
    private int ThemeID;

    public Note() {
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
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

    public Note(String note_id, String note_title, String note_content, int themeID) {
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
        ThemeID = themeID;
    }
}
