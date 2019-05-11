package com.snoopytbe.cava.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snoopytbe.cava.Classes.Humeur;
import com.snoopytbe.cava.Classes.ListeEtats;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HumeurFragment extends Fragment implements View.OnClickListener {

    private ListeEtats.ListeAngoisses listeAngoisses;
    private ListeEtats.ListeHumeurs listeHumeurs;
    private ListeEtats.ListeEnergies listeEnergies;
    private ListeEtats.ListeIrritabilite listeIrritabilite;

    private etat etat;

    private ArrayList<String> SymptomesActifs;
    private ArrayList<String> SymptomesInactifs;

    private static final String ARG_PARAM1 = "etat";
    private static final String ARG_PARAM2 = "quand";

    private HumeurFragmentCallback activityCallback;

    private String quand;
    @BindView(R.id.tit_date)
    TextView date;
    @BindView(R.id.sti_soustitre)
    TextView sousTitre;
    @BindView(R.id.feh_meteo)
    TextView meteo;
    @BindView(R.id.feh_angoisse)
    TextView angoisse;
    @BindView(R.id.feh_energie)
    TextView energie;
    @BindView(R.id.feh_irritabilite)
    TextView irritabilite;
    @BindView(R.id.feh_commentaire)
    EditText commentaire;
    @BindView(R.id.feh_listesymptomes)
    LinearLayout listesymptomes;


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

    public HumeurFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edition_humeur, container, false);

        ButterKnife.bind(this, view);

        listeHumeurs = new ListeEtats.ListeHumeurs();
        listeAngoisses = new ListeEtats.ListeAngoisses();
        listeIrritabilite = new ListeEtats.ListeIrritabilite();
        listeEnergies = new ListeEtats.ListeEnergies();

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

    private void LoadEtatInUI() {

        Log.e("Test", "Départ config");


        date.setText(etat.DateLisible());

        Humeur humeur = new Humeur();
        if (this.quand == "Matin") {
            humeur = etat.getHumeurMatin();
            sousTitre.setText("Humeur du matin");
        } else if (this.quand == "Aprem") {
            humeur = etat.getHumeurApresMidi();
            sousTitre.setText("Humeur de l'après-midi");
        } else if (this.quand == "Soir") {
            humeur = etat.getHumeurSoir();
            sousTitre.setText("Humeur du soir");
        }
        meteo.setText(listeHumeurs.getListeNiveaux().get(humeur.getHumeur()).getNom());
        angoisse.setText(listeAngoisses.getListeNiveaux().get(humeur.getAngoisse()).getNom());
        energie.setText(listeEnergies.getListeNiveaux().get(humeur.getEnergie()).getNom());
        irritabilite.setText(listeIrritabilite.getListeNiveaux().get(humeur.getIrritabilite()).getNom());
        commentaire.setText(humeur.getCommentaire());
        Log.e("Test", "Milieu");

        try {
            SymptomesActifs = new ArrayList<>(humeur.getSymptomesActifs());
            SymptomesInactifs = new ArrayList<>(humeur.getSymptomesInactifs());
        } catch (Exception e) {
            SymptomesActifs = new ArrayList<>();
            SymptomesInactifs = new ArrayList<>();
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int maxWidth = displayMetrics.widthPixels - 300; // (R.dimen.margin_small * 4 + R.dimen.largeur_image_humeur);
        int currentWidth = 0;
        Log.e("Test", "MaxWidth " + maxWidth);

        LinearLayout.LayoutParams marginSetter = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marginSetter.setMargins(8, 8, 8, 8);

        LinearLayout llTechnique = new LinearLayout(getContext());
        llTechnique.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        llTechnique.setOrientation(LinearLayout.HORIZONTAL);
        llTechnique.setGravity(Gravity.START);

        TextView newTextView = new TextView(getContext());

        for (int i = 0; i < SymptomesActifs.size(); i++) {
            //Log.e("Test", "début " + i);
            newTextView = new TextView(getContext());
            newTextView.setTextAppearance(R.style.CaVa_SymptomeActif);
            newTextView.setLayoutParams(marginSetter);
            newTextView.setText(SymptomesActifs.get(i));
            newTextView.setBackgroundResource(R.drawable.background_symptome_active);
            newTextView.setOnClickListener(this);
            newTextView.measure(0, 0);
            currentWidth += newTextView.getMeasuredWidth();
            //Log.e("Test", "Width " + currentWidth);
            if (currentWidth > maxWidth) {
                //Log.e("Test", "Nouvelle ligne");
                listesymptomes.addView(llTechnique);
                llTechnique = new LinearLayout(getContext());
                llTechnique.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llTechnique.setOrientation(LinearLayout.HORIZONTAL);
                llTechnique.setGravity(Gravity.LEFT);
                llTechnique.addView(newTextView);
                currentWidth = newTextView.getMeasuredWidth();
            } else {
                //Log.e("Test", "Ligne actuelle");
                llTechnique.addView(newTextView);
            }
        }

        for (int i = 0; i < SymptomesInactifs.size(); i++) {
            //Log.e("Test", "début " + i + " " + SymptomesInactifs.get(i));
            newTextView = new TextView(getContext());
            newTextView.setTextAppearance(R.style.CaVa_SymptomeInactif);
            newTextView.setLayoutParams(marginSetter);
            newTextView.setText(SymptomesInactifs.get(i));
            newTextView.setBackgroundResource(R.drawable.background_symptome_deactive);
            newTextView.setOnClickListener(this);
            newTextView.measure(0, 0);
            currentWidth += newTextView.getMeasuredWidth();
            //Log.e("Test", "Width " + currentWidth);
            if (currentWidth > maxWidth) {
                //Log.e("Test", "Nouvelle ligne");
                listesymptomes.addView(llTechnique);
                llTechnique = new LinearLayout(getContext());
                llTechnique.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llTechnique.setOrientation(LinearLayout.HORIZONTAL);
                llTechnique.setGravity(Gravity.START);
                llTechnique.addView(newTextView);
                currentWidth = newTextView.getMeasuredWidth();
            } else {
                //Log.e("Test", "Ligne actuelle");
                llTechnique.addView(newTextView);
            }
        }

        listesymptomes.addView(llTechnique);

    }

    private void SaveEtatFromUI() {
        if (quand == "Matin") {
            this.etat.getHumeurMatin().setCommentaire(commentaire.getText().toString());
            this.etat.getHumeurMatin().setSymptomesActifs(this.SymptomesActifs);
            this.etat.getHumeurMatin().setSymptomesInactifs(this.SymptomesInactifs);

        } else if (quand == "Aprem") {
            this.etat.getHumeurApresMidi().setCommentaire(commentaire.getText().toString());
            this.etat.getHumeurApresMidi().setSymptomesActifs(this.SymptomesActifs);
            this.etat.getHumeurApresMidi().setSymptomesInactifs(this.SymptomesInactifs);
        } else if (quand == "Soir") {
            this.etat.getHumeurSoir().setCommentaire(commentaire.getText().toString());
            this.etat.getHumeurSoir().setSymptomesActifs(this.SymptomesActifs);
            this.etat.getHumeurSoir().setSymptomesInactifs(this.SymptomesInactifs);
        }
        //etat.setHumeur(SpinnerHumeur.getSelectedItemPosition());
    }

    @Override
    public void onClick(View v) {
        TextView self = (TextView) v;
        String symptomeClique = self.getText().toString();
        Log.e("Test", "Click sur " + symptomeClique);
        if (SymptomesInactifs.indexOf(symptomeClique) >= 0) {
            SymptomesInactifs.remove(symptomeClique);
            SymptomesActifs.add(symptomeClique);
            self.setTextAppearance(R.style.CaVa_SymptomeActif);
            self.setBackgroundResource(R.drawable.background_symptome_active);
        } else {
            SymptomesActifs.remove(symptomeClique);
            SymptomesInactifs.add(symptomeClique);
            self.setTextAppearance(R.style.CaVa_SymptomeInactif);
            self.setBackgroundResource(R.drawable.background_symptome_deactive);
        }
    }

    @OnClick(R.id.feh_zonemeteo)
    public void EditHumeur() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChoixEtatClicked(etat, quand, "Humeur");
    }

    @OnClick(R.id.feh_zoneangoisse)
    public void EditAngoisse() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChoixEtatClicked(etat, quand, "Angoisse");
    }

    @OnClick(R.id.feh_zoneenergie)
    public void EditEnergie() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChoixEtatClicked(etat, quand, "Energie");
    }

    @OnClick(R.id.feh_zoneirritabilite)
    public void EditIrritabilite() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChoixEtatClicked(etat, quand, "Irritabilite");
    }


    @OnClick(R.id.tit_retour)
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
