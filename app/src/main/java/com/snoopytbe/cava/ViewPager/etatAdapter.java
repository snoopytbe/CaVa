package com.snoopytbe.cava.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snoopytbe.cava.Fragments.JourneeFragment;
import com.snoopytbe.cava.db.etat;

import java.util.ArrayList;
import java.util.List;

public class etatAdapter extends FragmentStatePagerAdapter {

    private List<etat> mEtats = new ArrayList<etat>();//Collections.emptyList();

    public etatAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return (JourneeFragment.newInstance(mEtats.get(i)));
    }

    public void setEtats(List<etat> etats) {
        this.mEtats = etats;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEtats.size();
    }
}
