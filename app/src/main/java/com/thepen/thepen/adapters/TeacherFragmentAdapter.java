package com.thepen.thepen.adapters;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.thepen.thepen.fragments.FragmentMyVideos;
import com.thepen.thepen.fragments.FragmentUploadVideo;

public class TeacherFragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    public TeacherFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new FragmentMyVideos();
            case 1:
                return new FragmentUploadVideo();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
