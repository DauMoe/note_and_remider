package com.example.noteandreminder.Module;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ReminderGroup {
    private String reminder_date;
    private List<Reminder> data;

    public ReminderGroup(String date, List<Reminder> data) {
        this.reminder_date = date;
        this.data = data;
    }

    public String getDate() {
        return reminder_date;
    }

    public void setDate(String date) {
        this.reminder_date = date;
    }

    public List<Reminder> getData() {
        return data;
    }

    public void setData(List<Reminder> data) {
        this.data = data;
    }
}
