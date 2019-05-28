package com.snoopytbe.cava.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.ListeEtats;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.snoopytbe.cava.Classes.commun.moment_aprem;
import static com.snoopytbe.cava.Classes.commun.moment_matin;
import static com.snoopytbe.cava.Classes.commun.moment_soir;

public class JourneeFragment extends FragmentCaVa {


    @BindView(R.id.fpj_textViewSommeil)
    TextView sommeil;
    @BindView(R.id.fpj_textViewTraitement)
    TextView traitement;
    @BindView(R.id.fpj_textViewMatin)
    TextView matin;
    @BindView(R.id.fpj_textViewAprem)
    TextView aprem;
    @BindView(R.id.fpj_textViewSoir)
    TextView soir;

    private static final String ARG_PARAM1 = "Etat";

    public static JourneeFragment newInstance(etat etat) {
        JourneeFragment fragment = new JourneeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etat);
        fragment.setArguments(args);
        Log.e("test", "newInstance: " + etat.DateLisible());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    protected JourneeFragmentCallback activityCallback2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(this.getFragmentLayout(), container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        etatViewModel = ViewModelProviders.of(getActivity()).get(com.snoopytbe.cava.db.etatViewModel.class);

        LoadEtatInUI();
        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_presentation_journee;
    }

    public void notifyUpdate() {
        this.LoadEtatInUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().setTitle(etat.DateLisible());
        super.onCreateOptionsMenu(menu, inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    protected void LoadEtatInUI() {

        if (!(etat == null)) {
            Timber.e("LoadEtatInUI " + etat.DateLisible());

            //getActivity().setTitle(etat.DateLisible());
            //setHasOptionsMenu(false);
            //setHasOptionsMenu(true);

            String myText;

            myText = "Dormi " + etat.getQualiteSommeil().getHeuresSommeil().Lisible() + "\n";
            myText += "Qualité : " + (int) etat.getQualiteSommeil().getRatingSommeil() + "/5";
            sommeil.setText(myText);

            if (etat.getTraitement().getRespecte()) {
                myText = "Traitement habituel pris";
            } else {
                myText = "Traitement habituel modifié";
            }
            traitement.setText(myText);

            ListeEtats.ListeAngoisses listeAngoisses = new ListeEtats.ListeAngoisses();
            ListeEtats.ListeHumeurs listeHumeurs = new ListeEtats.ListeHumeurs();
            ListeEtats.ListeEnergies listeEnergies = new ListeEtats.ListeEnergies();
            ListeEtats.ListeIrritabilite listeIrritabilite = new ListeEtats.ListeIrritabilite();

            myText = "Humeur : " + listeHumeurs.getListeNiveaux().get(etat.getHumeurMatin().getHumeur()).getNom() + "\n";
            myText += "Angoisse : " + listeAngoisses.getListeNiveaux().get(etat.getHumeurMatin().getAngoisse()).getNom() + "\n";
            myText += "Energie : " + listeEnergies.getListeNiveaux().get(etat.getHumeurMatin().getEnergie()).getNom() + "\n";
            myText += "Irritabilité : " + listeIrritabilite.getListeNiveaux().get(etat.getHumeurMatin().getIrritabilite()).getNom();
            matin.setText(myText);

            myText = "Humeur : " + listeHumeurs.getListeNiveaux().get(etat.getHumeurApresMidi().getHumeur()).getNom() + "\n";
            myText += "Angoisse : " + listeAngoisses.getListeNiveaux().get(etat.getHumeurApresMidi().getAngoisse()).getNom() + "\n";
            myText += "Energie : " + listeEnergies.getListeNiveaux().get(etat.getHumeurApresMidi().getEnergie()).getNom() + "\n";
            myText += "Irritabilité : " + listeIrritabilite.getListeNiveaux().get(etat.getHumeurApresMidi().getIrritabilite()).getNom();
            aprem.setText(myText);

            myText = "Humeur : " + listeHumeurs.getListeNiveaux().get(etat.getHumeurSoir().getHumeur()).getNom() + "\n";
            myText += "Angoisse : " + listeAngoisses.getListeNiveaux().get(etat.getHumeurSoir().getAngoisse()).getNom() + "\n";
            myText += "Energie : " + listeEnergies.getListeNiveaux().get(etat.getHumeurSoir().getEnergie()).getNom() + "\n";
            myText += "Irritabilité : " + listeIrritabilite.getListeNiveaux().get(etat.getHumeurSoir().getIrritabilite()).getNom();
            soir.setText(myText);
        }
    }

    protected void SaveEtatFromUI() {
    }

    @OnClick(R.id.fpj_cardSommeil)
    public void chargeSommeil() {
        etatViewModel.setEtatActuel(etat);
        SaveEtatFromUI();
        if (activityCallback2 != null)
            activityCallback2.ShowSommeilFragment();
    }

    @OnClick({R.id.fpj_cardMatin, R.id.fpj_cardAprem, R.id.fpj_cardSoir})
    public void chargeHumeur(View view) {
        etatViewModel.setEtatActuel(etat);
        SaveEtatFromUI();
        switch (view.getId()) {
            case R.id.fpj_cardMatin:
                etatViewModel.setMomentActuel(moment_matin);
                break;
            case R.id.fpj_cardAprem:
                etatViewModel.setMomentActuel(moment_aprem);
                break;
            case R.id.fpj_cardSoir:
                etatViewModel.setMomentActuel(moment_soir);
                break;
        }
        if (activityCallback2 != null)
            activityCallback2.ShowHumeurFragment();
    }

    @OnClick(R.id.fpj_cardTraitement)
    public void chargeTraitement() {
        etatViewModel.setEtatActuel(etat);
        SaveEtatFromUI();
        if (activityCallback2 != null)
            activityCallback2.ShowTraitementFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof JourneeFragmentCallback)
            activityCallback2 = (JourneeFragmentCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback2 = null;
    }

    public interface JourneeFragmentCallback {
        void ShowSommeilFragment();

        void ShowTraitementFragment();

        void ShowHumeurFragment();

    }
}
