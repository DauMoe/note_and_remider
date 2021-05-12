package com.example.noteandreminder.Module;

import java.time.LocalDateTime;

public class Reminder {
    private String reminder_title, reminder_desc;
    private int themeID;
    private LocalDateTime remind_time;
    private boolean reminder_completed;

    public Reminder(String reminder_title, String reminder_desc, int themeID, LocalDateTime remind_time, boolean reminder_completed) {
        this.reminder_title = reminder_title;
        this.reminder_desc = reminder_desc;
        this.themeID = themeID;
        this.remind_time = remind_time;
        this.reminder_completed = reminder_completed;
    }

    public String getReminder_title() {
        return reminder_title;
    }

    public void setReminder_title(String reminder_title) {
        this.reminder_title = reminder_title;
    }

    public String getReminder_desc() {
        return reminder_desc;
    }

    public void setReminder_desc(String reminder_desc) {
        this.reminder_desc = reminder_desc;
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

    public boolean isReminder_completed() {
        return reminder_completed;
    }

    public void setReminder_completed(boolean reminder_completed) {
        this.reminder_completed = reminder_completed;
    }
}
