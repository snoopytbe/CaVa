package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snoopytbe.cava.Classes.ListeEtats;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JourneeFragment extends Fragment {

    private static final String ARG_PARAM1 = "Etat";

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

    private JourneeFragmentCallback activityCallback;
    private etat etat;

    public JourneeFragment() {
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presentation_journee, container, false);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);
        LoadEtatInUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().setTitle(etat.DateLisible());
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        if (context instanceof JourneeFragmentCallback)
            activityCallback = (JourneeFragmentCallback) context;
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

    @Override
    public void onResume() {
        super.onResume();
        LoadEtatInUI();
    }

    public void notifyUpdate() {
        this.LoadEtatInUI();
    }

    private void LoadEtatInUI() {

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

    private void SaveEtatFromUI() {

    }

    @OnClick(R.id.fpj_cardSommeil)
    public void chargeSommeil() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowSommeilFragment(etat);
    }

    @OnClick(R.id.fpj_cardMatin)
    public void chargeMatin() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChargeEtatClicked(etat, "Matin");
    }

    @OnClick(R.id.fpj_cardAprem)
    public void chargeAprem() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChargeEtatClicked(etat, "Aprem");
    }

    @OnClick(R.id.fpj_cardSoir)
    public void chargeSoir() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChargeEtatClicked(etat, "Soir");
    }

    @OnClick(R.id.fpj_cardTraitement)
    public void chargeTraitement() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowTraitementFragment(etat);
    }


    public interface JourneeFragmentCallback {
        void ShowSommeilFragment(etat etat);

        void ShowTraitementFragment(etat etat);

        void onChargeEtatClicked(etat etat, String quand);

    }
}
