package com.snoopytbe.cava.AbstractFragments;

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
import timber.log.Timber;

public abstract class FragmentCaVa extends Fragment {

    protected FragmentCaVa.FragmentCaVaCallback activityCallback;
    protected etat etat;

    protected abstract int getFragmentLayout();
    protected abstract void LoadEtatInUI();
    protected abstract void SaveEtatFromUI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(this.getFragmentLayout(), container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        LoadEtatInUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Timber.e("Menu général");
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
        super.onPause();
        SaveEtatFromUI();
        activityCallback.sauveEtat(etat);
    }

    public interface FragmentCaVaCallback {
        void sauveEtat(etat etat);

        void ShowGraphiqueFragment();

        etatViewModel getEtatViewModel();
    }

}
