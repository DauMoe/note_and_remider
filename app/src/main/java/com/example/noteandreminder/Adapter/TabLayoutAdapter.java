package com.example.noteandreminder.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.noteandreminder.Fragment.NoteFragment;
import com.example.noteandreminder.Fragment.ReminderFragment;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private static final int numofTab = 2;

    public TabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NoteFragment();
            case 1:
                return new ReminderFragment();
            default:
                return new NoteFragment();
        }
    }

    @Override
    public int getCount() {
        return numofTab;
    }
}
