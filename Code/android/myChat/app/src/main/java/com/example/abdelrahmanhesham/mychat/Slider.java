package com.example.abdelrahmanhesham.mychat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Abdelrahman Hesham on 4/30/2017.
 */

public class Slider extends FragmentPagerAdapter {
    public Slider(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Profile();
            case 1:
                return new Chat();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "My Profile";
            case 1:
                return "My Chat";
            default:
                return null;
        }
    }
}
