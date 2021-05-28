package com.example.noteandreminder.Module;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reminder {
    private String reminder_title, reminder_desc, reminder_id, reminder_date, reminder_time;
    private boolean reminder_completed;

    public Reminder() {
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

    public String getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(String reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getReminder_date() {
        return reminder_date;
    }

    public void setReminder_date(String reminder_date) {
        this.reminder_date = reminder_date;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
    }

    public boolean isReminder_completed() {
        return reminder_completed;
    }

    public void setReminder_completed(boolean reminder_completed) {
        this.reminder_completed = reminder_completed;
    }

    public Reminder(String reminder_id, String reminder_title, String reminder_desc, String reminder_date, String reminder_time, boolean reminder_completed) {
        this.reminder_title = reminder_title;
        this.reminder_desc = reminder_desc;
        this.reminder_id = reminder_id;
        this.reminder_date = reminder_date;
        this.reminder_time = reminder_time;
        this.reminder_completed = reminder_completed;
    }
}
