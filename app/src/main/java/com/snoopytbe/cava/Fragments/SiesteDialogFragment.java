package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.snoopytbe.cava.Classes.HeuresMinutes;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SiesteDialogFragment extends DialogHeuresMinutes {

    protected static String ARG_ETAT = "Etat";
    protected com.snoopytbe.cava.Classes.etat etat;
    @BindView(R.id.dhsi_sieste)
    NumberPicker sieste;

    public static SiesteDialogFragment newInstance(etat etat) {
        SiesteDialogFragment fragment = new SiesteDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ETAT, etat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_ETAT);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.dialogfragment_heures_sieste;
    }

    @Override
    protected void LoadDatainUI() {

        String[] heuresAffichees = this.configureHeuresAffichees(15);

        this.configureNumberPicker(sieste, 0, heuresAffichees, false, this.etat.getQualiteSommeil().getHeuresSieste().getIntQuinzaine());
    }

    @Override
    protected void SaveDataofUI() {
    }

    @OnClick(R.id.dhsi_boutonOK)
    public void Ok() {
        HeuresMinutes time = new HeuresMinutes();

        time.setIntQuinzaine(sieste.getValue());
        this.etat.getQualiteSommeil().setHeuresSieste(new HeuresMinutes(time.getHeures(), time.getMinutes()));

        if (activityCallback != null)
            activityCallback.onOkDialogFragmentHeuresMinutes(this.etat);

        this.dismiss();
    }

    @OnClick(R.id.dhsi_boutonCancel)
    public void Bye() {
        this.dismiss();
    }

}
