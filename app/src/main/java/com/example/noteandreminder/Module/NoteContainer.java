package com.example.noteandreminder.Module;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteContainer {
    private String title;
    private List<NoteItem> content;

    public NoteContainer(String title, List<NoteItem> content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NoteItem> getContent() {
        return content;
    }

    public void setContent(List<NoteItem> content) {
        this.content = content;
    }
}
