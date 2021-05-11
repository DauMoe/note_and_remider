package com.example.noteandreminder.Module;

import java.time.LocalDateTime;

public class Reminder {
    private String reminder_title, reminder_desc;
    private int themeID;
    private LocalDateTime remind_time;

    public Reminder(String reminder_title, String reminder_content, int themeID, LocalDateTime remind_time) {
        this.reminder_title = reminder_title;
        this.reminder_desc = reminder_content;
        this.themeID = themeID;
        this.remind_time = remind_time;
    }

    public String getReminder_title() {
        return reminder_title;
    }

    public void setReminder_title(String reminder_title) {
        this.reminder_title = reminder_title;
    }

    public String getReminder_content() {
        return reminder_desc;
    }

    public void setReminder_content(String reminder_content) {
        this.reminder_desc = reminder_content;
    }

    public int getThemeID() {
        return themeID;
    }

    public void setThemeID(int themeID) {
        this.themeID = themeID;
    }

    public LocalDateTime getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(LocalDateTime remind_time) {
        this.remind_time = remind_time;
    }
}
