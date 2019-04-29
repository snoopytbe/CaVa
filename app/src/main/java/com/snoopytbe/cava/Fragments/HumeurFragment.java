package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.snoopytbe.cava.MethodesListeEtats.ListeAngoisses;
import com.snoopytbe.cava.MethodesListeEtats.ListeHumeurs;
import com.snoopytbe.cava.R;
import com.snoopytbe.cava.db.Humeur;
import com.snoopytbe.cava.db.etat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HumeurFragment extends Fragment {

    ListeAngoisses ListeAngoisses;
    private etat etat;
    private static final String ARG_PARAM1 = "etat";
    private static final String ARG_PARAM2 = "quand";
    @BindView(R.id.feh_date)
    TextView date;
    ListeHumeurs ListeHumeurs;
    @BindView(R.id.feh_momentjournee)
    TextView momentJournee;
    @BindView(R.id.feh_meteo)
    TextView meteo;
    private String quand;

    public static HumeurFragment newInstance(etat etat, String quand) {
        HumeurFragment fragment = new HumeurFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etat);
        args.putSerializable(ARG_PARAM2, quand);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_PARAM1);
            quand = (String) getArguments().getSerializable(ARG_PARAM2);
        }
    }
    @BindView(R.id.feh_angoisse)
    TextView angoisse;
    @BindView(R.id.feh_commentaire)
    EditText commentaire;
    private HumeurFragmentCallback activityCallback;

    /*@BindView(R.id.spinnerHumeur)
    Spinner SpinnerHumeur;*/

    public HumeurFragment() {
    }

    private void LoadEtatInUI() {
        //SpinnerHumeur.setSelection(etat.getHumeur());
        date.setText(etat.DateLisible());
        momentJournee.setText(quand);
        Humeur humeur = new Humeur();
        if (this.quand == "Matin") {
            humeur = etat.getHumeurMatin();
        } else if (this.quand == "Aprem") {
            humeur = etat.getHumeurApresMidi();
        } else if (this.quand == "Soir") {
            humeur = etat.getHumeurSoir();
        }
        meteo.setText("Météo de l'humeur : " + ListeHumeurs.getListeNiveaux().get(humeur.getHumeur()).getNom());
        angoisse.setText("Angoisse : " + ListeAngoisses.getListeNiveaux().get(humeur.getAngoisse()).getNom());
        commentaire.setText(humeur.getCommentaire());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edition_humeur, container, false);

        ButterKnife.bind(this, view);

        ListeHumeurs = new ListeHumeurs();
        ListeAngoisses = new ListeAngoisses();

        //SpinnerEtatsAdapter adapterHumeurs = new SpinnerEtatsAdapter((Context) activityCallback, ListeHumeurs);
        //SpinnerHumeur.setAdapter(adapterHumeurs);*/

        LoadEtatInUI();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HumeurFragmentCallback)
            activityCallback = (HumeurFragmentCallback) context;
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
        if (quand == "Matin") {
            this.etat.getHumeurMatin().setCommentaire(commentaire.getText().toString());
        } else if (quand == "Aprem") {
            this.etat.getHumeurApresMidi().setCommentaire(commentaire.getText().toString());
        } else if (quand == "Soir") {
            this.etat.getHumeurSoir().setCommentaire(commentaire.getText().toString());
        }
        //etat.setHumeur(SpinnerHumeur.getSelectedItemPosition());
    }

    @OnClick(R.id.feh_meteo)
    public void EditHumeur() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChoixEtatClicked(etat, quand, "Humeur");
    }

    @OnClick(R.id.feh_angoisse)
    public void EditAngoisse() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChoixEtatClicked(etat, quand, "Angoisse");
    }

    @OnClick(R.id.feh_OK)
    public void ok() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onOKFragmentHumeur(etat);
    }

    public interface HumeurFragmentCallback {
        void onOKFragmentHumeur(etat etat);

        void onChoixEtatClicked(etat etat, String quand, String type);
    }

}
