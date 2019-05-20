package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.MainActivity;
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
    @BindView(R.id.fes_HeuresSieste)
    TextView heuresSieste;
    @BindView(R.id.fes_commentaire)
    EditText commentaire;

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
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
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
            case (R.id.menu_about):
                Toast.makeText(this.getContext(), "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        activityCallback.sauveEtat(etat);
    }

    private void LoadEtatInUI() {
        ratingSommeil.setRating(etat.getQualiteSommeil().getRatingSommeil());
        heuresSommeil.setText(etat.getQualiteSommeil().getHeuresSommeil().Lisible());
        heureCoucher.setText(etat.getQualiteSommeil().getHeureCoucher().Lisible());
        heureLever.setText(etat.getQualiteSommeil().getHeureLever().Lisible());
        heuresInsomnie.setText(etat.getQualiteSommeil().getHeuresInsomnie().Lisible());
        heuresSieste.setText(etat.getQualiteSommeil().getHeuresSieste().Lisible());
        commentaire.setText(etat.getQualiteSommeil().getCommentaire());
    }

    private void SaveEtatFromUI() {
        etat.getQualiteSommeil().setRatingSommeil(ratingSommeil.getRating());
        etat.getQualiteSommeil().setCommentaire(commentaire.getText().toString());
    }

    @OnClick(R.id.fes_zoneQuantite)
    public void changeHeuresSommeil() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowHeuresSommeil(etat);
    }

    @OnClick(R.id.fes_zoneSieste)
    public void changeHeuresSieste() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowHeuresSieste(etat);
    }


    public interface SommeilFragmentCallback {

        void ShowHeuresSommeil(etat etat);

        void ShowHeuresSieste(etat etat);

        void sauveEtat(etat etat);
    }

}
