package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SommeilFragment extends Fragment {

    private static final String ARG_PARAM1 = "Etat";
    private SommeilFragmentCallback activityCallback;
    private etat etat;

    public SommeilFragment() {
    }

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
    @BindView(R.id.tit_date)
    TextView date;

    public static SommeilFragment newInstance(etat etat) {
        SommeilFragment fragment = new SommeilFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etat);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edition_sommeil, container, false);
        ButterKnife.bind(this, view);
        LoadEtatInUI();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SommeilFragmentCallback)
            activityCallback = (SommeilFragmentCallback) context;
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
    }

    private void LoadEtatInUI() {
        date.setText(etat.DateLisible());
        ratingSommeil.setRating(etat.getQualiteSommeil().getRatingSommeil());
        heuresSommeil.setText(etat.getQualiteSommeil().getHeuresSommeil().Lisible());
        heureCoucher.setText(etat.getQualiteSommeil().getHeureCoucher().Lisible());
        heureLever.setText(etat.getQualiteSommeil().getHeureLever().Lisible());
        heuresInsomnie.setText(etat.getQualiteSommeil().getHeuresInsomnie().Lisible());
    }

    private void SaveEtatFromUI() {
        etat.getQualiteSommeil().setRatingSommeil(ratingSommeil.getRating());
    }

    @OnClick(R.id.fes_zoneQuantite)
    public void changeHeuresSommeil() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowHeuresSommeil(etat);
    }

    @OnClick(R.id.tit_retour)
    public void ok() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onRetourFragmentSommeil(etat);
    }

    public interface SommeilFragmentCallback {

        void onRetourFragmentSommeil(etat etat);

        void ShowHeuresSommeil(etat etat);
    }

}
