package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;
import com.snoopytbe.cava.ViewPager.etatAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment_ViewPager extends Fragment {
    public View myView;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    private etatAdapter adapter;

    public MainFragment_ViewPager() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (myView == null) {
            myView = inflater.inflate(R.layout.fragment_viewpager, container, false);
            ButterKnife.bind(this, myView);
            ConfigureViewPager();
        }
        return myView;
    }

    private void ConfigureViewPager() {
        adapter = new etatAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void setEtats(List<etat> etats) {
        adapter.setEtats(etats);
    }
}
