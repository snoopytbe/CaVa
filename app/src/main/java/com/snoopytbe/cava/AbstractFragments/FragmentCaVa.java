package com.snoopytbe.cava.AbstractFragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.R;
import com.snoopytbe.cava.db.etatViewModel;

import butterknife.ButterKnife;

public abstract class FragmentCaVa extends Fragment {

    protected FragmentCaVaCallback activityCallback;
    protected etat etat;
    protected etatViewModel etatViewModel;

    protected abstract int getFragmentLayout();
    protected abstract void LoadEtatInUI();
    protected abstract void SaveEtatFromUI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(this.getFragmentLayout(), container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        etatViewModel = ViewModelProviders.of(getActivity()).get(etatViewModel.class);
        etatViewModel.getEtatActuel().observe(this, etatFromGet -> {
            this.etat = etatFromGet;
            LoadEtatInUI();
        });

        LoadEtatInUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case (R.id.menu_graphique):
                etatViewModel.setEtatActuel(etat);
                activityCallback.ShowGraphiqueFragment();
                return true;
            case (R.id.menu_about):
                Toast.makeText(this.getContext(), "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCaVa.FragmentCaVaCallback)
            activityCallback = (FragmentCaVa.FragmentCaVaCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }

    @Override
    public void onPause() {
        //Timber.e("Update du " + etat.DateLisible());
        super.onPause();
        SaveEtatFromUI();
        etatViewModel.update(etat);
    }

    public interface FragmentCaVaCallback {
        void ShowGraphiqueFragment();
    }

}
