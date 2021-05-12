package com.example.noteandreminder.Module;

import java.util.Date;
import java.util.List;

public class ReminderGroup {
    private Date reminder_date;
    private List<Reminder> data;

    public ReminderGroup(Date date, List<Reminder> data) {
        this.reminder_date = date;
        this.data = data;
    }

    public Date getDate() {
        return reminder_date;
    }

    public void setDate(Date date) {
        this.reminder_date = date;
    }

    public List<Reminder> getData() {
        return data;
    }

    public void setData(List<Reminder> data) {
        this.data = data;
    }
}
