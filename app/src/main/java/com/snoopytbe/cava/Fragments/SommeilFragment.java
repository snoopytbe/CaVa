package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snoopytbe.cava.AbstractFragments.DialogFragmentHeuresMinutes;
import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SommeilFragment extends FragmentCaVa implements DialogFragmentHeuresMinutes.DialogHeuresMinutesCallback {

    private static final String ARG_PARAM1 = "Etat";

    @BindView(R.id.fes_ratingSommeil)
    RatingBar ratingSommeil;
    @BindView(R.id.fes_HeuresSommeil)
    TextView heuresSommeil;
    @BindView(R.id.fes_HeureCoucher)
    TextView heureCoucher;
    @BindView(R.id.fes_HeureLever)
    TextView heureLever;
    @BindView(R.id.fes_HeuresInsomnie)
    TextView heuresInsomnie;
    @BindView(R.id.fes_HeuresSieste)
    TextView heuresSieste;
    @BindView(R.id.fes_commentaire)
    EditText commentaire;

    public SommeilFragment() {
    }

    public static SommeilFragment newInstance(etat etat) {
        SommeilFragment fragment = new SommeilFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edition_sommeil;
    }

    protected void LoadEtatInUI() {
        ratingSommeil.setRating(etat.getQualiteSommeil().getRatingSommeil());
        heuresSommeil.setText(etat.getQualiteSommeil().getHeuresSommeil().Lisible());
        heureCoucher.setText(etat.getQualiteSommeil().getHeureCoucher().Lisible());
        heureLever.setText(etat.getQualiteSommeil().getHeureLever().Lisible());
        heuresInsomnie.setText(etat.getQualiteSommeil().getHeuresInsomnie().Lisible());
        heuresSieste.setText(etat.getQualiteSommeil().getHeuresSieste().Lisible());
        commentaire.setText(etat.getQualiteSommeil().getCommentaire());
    }

    protected void SaveEtatFromUI() {
        etat.getQualiteSommeil().setRatingSommeil(ratingSommeil.getRating());
        etat.getQualiteSommeil().setCommentaire(commentaire.getText().toString());
    }

    @OnClick(R.id.fes_zoneQuantite)
    public void changeHeuresSommeil() {
        SommeilDialogFragment sommeilDialogFragment = SommeilDialogFragment.newInstance(etat);
        sommeilDialogFragment.setTargetFragment(this, 0);
        sommeilDialogFragment.show(getFragmentManager(), "EditionHeure");
    }

    @OnClick(R.id.fes_zoneSieste)
    public void changeHeuresSieste() {
        SiesteDialogFragment siesteDialogFragment = SiesteDialogFragment.newInstance(etat);
        siesteDialogFragment.setTargetFragment(this, 0);
        siesteDialogFragment.show(getFragmentManager(), "EditionHeure");
    }

    @Override
    public void onOkDialogFragmentHeuresMinutes() {
        LoadEtatInUI();
    }
}
