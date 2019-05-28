package com.snoopytbe.cava.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.R;
import com.snoopytbe.cava.ViewPager.etatAdapter;
import com.snoopytbe.cava.db.etatViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainFragment_ViewPager extends Fragment {
    public View myView;

    @BindView(R.id.main_viewpager)
    ViewPager viewPager;

    private etatViewModel etatViewModel;
    private etatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (myView == null) {
            //Timber.e("Création ViewPager");
            myView = inflater.inflate(R.layout.fragment_viewpager, container, false);
            ButterKnife.bind(this, myView);

            etatViewModel = ViewModelProviders.of(getActivity()).get(etatViewModel.class);
            ConfigureViewPager();
        }
        return myView;
    }

    private void ConfigureViewPager() {
        //Timber.e("Configuration ViewPager");
        adapter = new etatAdapter(getActivity().getSupportFragmentManager(), (MainActivity) getActivity());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Timber.e("OnPageChangeListener : Page " + i);
                etatViewModel.setPosActuelle(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        goToPage(etatViewModel.getPosActuelle());
    }

    public void goToPage(Integer i) {
        Timber.e("Go to Page " + i);
        if (!(viewPager == null))
            viewPager.setCurrentItem(i);
    }

    public void setEtats(List<etat> etats) {
        Timber.e("setEtat");
        if (!(adapter == null))
            adapter.setEtats(etats);
    }
}
