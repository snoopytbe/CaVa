package com.snoopytbe.cava.ViewPager;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.Fragments.JourneeFragment;
import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.db.etatViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class etatAdapter extends FragmentStatePagerAdapter {

    private List<etat> mEtats = new ArrayList<etat>();
    private etatViewModel etatViewModel;

    public etatAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        etatViewModel = ViewModelProviders.of(activity).get(etatViewModel.class);
    }

    @Override
    public Fragment getItem(int i) {
        //Timber.e("getItem: " + mEtats.get(i).DateLisible());
        return (JourneeFragment.newInstance(mEtats.get(i)));
    }


    public int getItemPosition(Object object) {
        if (object instanceof JourneeFragment) {
            // Create a new method notifyUpdate() in your fragment
            // it will get call when you invoke
            // notifyDatasetChaged();
            ((JourneeFragment) object).notifyUpdate();
        }
        //don't return POSITION_NONE, avoid fragment recreation.
        return super.getItemPosition(object);
    }


    public void setEtats(List<etat> etats) {
        Timber.e("setEtats");
        this.mEtats = etats;
        //Log.e("test", "setEtats: " + mEtats.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEtats.size();
    }
}
