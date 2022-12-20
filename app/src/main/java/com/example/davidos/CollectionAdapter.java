package com.example.davidos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> screens = new ArrayList<Fragment>();

    public CollectionAdapter(FragmentActivity fragment) {
        super(fragment);
        screens.add(new EDUViewFragment());
        screens.add(new AddFragment());

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        System.out.println("The position is " + position);
        return screens.get(position);
    }

    @Override
    public int getItemCount() {
        return screens.size();
    }
}
