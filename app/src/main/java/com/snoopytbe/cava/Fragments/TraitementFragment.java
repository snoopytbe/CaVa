package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;


public class TraitementFragment extends FragmentCaVa {

    private static final String ARG_PARAM1 = "etat";
    @BindView(R.id.fet_respecte)
    CheckBox respecte;
    @BindView(R.id.fet_actuel)
    EditText actuel;
    @BindView(R.id.fet_commentaire)
    EditText commentaire;
    @BindView(R.id.sti_soustitre)
    TextView sousTitre;

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

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edition_traitement;
    }

    protected void LoadEtatInUI() {
        sousTitre.setText("Traitement");
        actuel.setText(etat.getTraitement().getActuel());
        respecte.setChecked(etat.getTraitement().getRespecte());
        commentaire.setText(etat.getTraitement().getCommentaire());
    }

    protected void SaveEtatFromUI() {
        this.etat.getTraitement().setActuel(actuel.getText().toString());
        this.etat.getTraitement().setRespecte(respecte.isChecked());
        this.etat.getTraitement().setCommentaire(commentaire.getText().toString());
    }
}
