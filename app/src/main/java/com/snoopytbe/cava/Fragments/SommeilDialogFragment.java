package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;

import com.snoopytbe.cava.AbstractFragments.DialogFragmentHeuresMinutes;
import com.snoopytbe.cava.Classes.HeuresMinutes;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SommeilDialogFragment extends DialogFragmentHeuresMinutes {

    @BindView(R.id.dhs_coucher)
    NumberPicker coucher;
    @BindView(R.id.dhs_lever)
    NumberPicker lever;
    @BindView(R.id.dhs_insomnie)
    NumberPicker insomnie;

    protected static String ARG_ETAT = "Etat";
    protected etat etat;

    public static SommeilDialogFragment newInstance(etat etat) {
        SommeilDialogFragment fragment = new SommeilDialogFragment();
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
        return R.layout.dialogfragment_heures_sommeil;
    }

    @Override
    protected void LoadDatainUI() {

        String[] heuresAffichees = this.configureHeuresAffichees(15);

        this.configureNumberPicker(coucher, 0, heuresAffichees, true, this.etat.getQualiteSommeil().getHeureCoucher().getIntQuinzaine());

        this.configureNumberPicker(lever, 0, heuresAffichees, true, this.etat.getQualiteSommeil().getHeureLever().getIntQuinzaine());

        this.configureNumberPicker(insomnie, 0, heuresAffichees, false, this.etat.getQualiteSommeil().getHeuresInsomnie().getIntQuinzaine());

    }

    @Override
    protected void SaveDataofUI() {
    }

    @OnClick(R.id.dhs_boutonOK)
    public void Ok() {
        HeuresMinutes time = new HeuresMinutes();
        time.setIntQuinzaine(lever.getValue());
        this.etat.getQualiteSommeil().setHeureLever(new HeuresMinutes(time.getHeures(), time.getMinutes()));
        Log.e("Test", "@OnClick(R.id.dhs_boutonOK) : " + etat.getQualiteSommeil().getHeureLever().Lisible());
        time.setIntQuinzaine(coucher.getValue());
        this.etat.getQualiteSommeil().setHeureCoucher(new HeuresMinutes(time.getHeures(), time.getMinutes()));
        Log.e("Test", "@OnClick(R.id.dhs_boutonOK) : " + etat.getQualiteSommeil().getHeureLever().Lisible());
        time.setIntQuinzaine(insomnie.getValue());
        this.etat.getQualiteSommeil().setHeuresInsomnie(new HeuresMinutes(time.getHeures(), time.getMinutes()));
        Log.e("Test", "@OnClick(R.id.dhs_boutonOK) : " + etat.getQualiteSommeil().getHeureLever().Lisible());

        if (activityCallback != null)
            activityCallback.onOkDialogFragmentHeuresMinutes(this.etat);

        this.dismiss();
    }

    @OnClick(R.id.dhs_boutonCancel)
    public void Bye() {
        this.dismiss();
    }

}
