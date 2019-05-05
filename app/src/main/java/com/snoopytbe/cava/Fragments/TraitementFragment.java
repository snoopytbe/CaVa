package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TraitementFragment extends Fragment {

    private etat etat;
    private static final String ARG_PARAM1 = "etat";
    @BindView(R.id.tit_date)
    TextView date;
    @BindView(R.id.fet_respecte)
    CheckBox respecte;
    @BindView(R.id.fet_actuel)
    EditText actuel;
    @BindView(R.id.fet_commentaire)
    EditText commentaire;
    @BindView(R.id.sti_soustitre)
    TextView sousTitre;
    private TraitementFragmentCallback activityCallback;


    public static TraitementFragment newInstance(etat etat) {
        TraitementFragment fragment = new TraitementFragment();
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

    public TraitementFragment() {
    }

    private void LoadEtatInUI() {
        date.setText(etat.DateLisible());
        sousTitre.setText("Traitement");
        actuel.setText(etat.getTraitement().getActuel());
        respecte.setChecked(etat.getTraitement().getRespecte());
        commentaire.setText(etat.getTraitement().getCommentaire());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edition_traitement, container, false);
        ButterKnife.bind(this, view);
        LoadEtatInUI();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TraitementFragmentCallback)
            activityCallback = (TraitementFragmentCallback) context;
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

    private void SaveEtatFromUI() {
        etat.getTraitement().setActuel(actuel.getText().toString());
        etat.getTraitement().setRespecte(respecte.isChecked());
        etat.getTraitement().setCommentaire(commentaire.getText().toString());
    }

    @OnClick(R.id.tit_retour)
    public void ok() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onOKFragmentTraitement(etat);
    }

    public interface TraitementFragmentCallback {
        void onOKFragmentTraitement(etat etat);
    }

}
