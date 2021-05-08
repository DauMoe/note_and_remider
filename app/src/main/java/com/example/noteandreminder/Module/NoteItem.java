package com.example.noteandreminder.Module;

import android.widget.RadioButton;

public class NoteItem {
    private boolean note_btn;
    private String note_content;

    public NoteItem() {};
    public NoteItem(boolean note_btn, String note_content) {
        this.note_btn = note_btn;
        this.note_content = note_content;
    }

    public boolean getNoteComplete() {
        return note_btn;
    }

    public void setNoteComplete(boolean note_btn) {
        this.note_btn = note_btn;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }
}
