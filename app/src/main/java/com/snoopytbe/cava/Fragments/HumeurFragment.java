package com.snoopytbe.cava.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String SymptomeASupprimer;

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

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);

        listeHumeurs = new ListeEtats.ListeHumeurs();
        listeAngoisses = new ListeEtats.ListeAngoisses();
        listeIrritabilite = new ListeEtats.ListeIrritabilite();
        listeEnergies = new ListeEtats.ListeEnergies();

        LoadEtatInUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_feh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_copy):
                Log.e("Test", "onOptionsItemSelected: Humeur");
                Toast.makeText(this.getContext(), "Copy", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.menu_about):
                Log.e("Test", "onOptionsItemSelected: Humeur");
                Toast.makeText(this.getContext(), "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private TextView getSymptomeTextView(String texte, Boolean actifTrueInactifFalse) {

        TextView result = new TextView(getContext());

        int margin = (int) (getResources().getDimension(R.dimen.margin_small) / getResources().getDisplayMetrics().density);

        LinearLayout.LayoutParams marginSetter = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marginSetter.setMargins(margin, margin, margin, margin);
        result.setLayoutParams(marginSetter);

        result.setText(texte);

        //Log.e("Test",   texte + " " + actifTrueInactifFalse);

        result.setBackgroundResource(actifTrueInactifFalse ? R.drawable.background_symptome_active : R.drawable.background_symptome_deactive);
        result.setTextAppearance(actifTrueInactifFalse ? R.style.CaVa_SymptomeActif : R.style.CaVa_SymptomeInactif);

        result.setOnClickListener(this);

        registerForContextMenu(result);

        result.measure(0, 0);

        return result;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        SymptomeASupprimer = ((TextView) v).getText().toString();
        Log.e("Test", "onCreateContextMenu: " + SymptomeASupprimer);
        menu.add("Supprimer");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //TextView view = (TextView) ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).targetView;
        String SymptomeSupprime = SymptomeASupprimer; //view.getText().toString();
        Log.e("Test", "onContextItemSelected: " + SymptomeSupprime);
        if (SymptomesActifs.contains(SymptomeSupprime)) {
            SymptomesActifs.remove(SymptomeSupprime);
        } else SymptomesInactifs.remove(SymptomeSupprime);
        SaveEtatFromUI();
        LoadEtatInUI();
        return true;
    }


    private LinearLayout addLinearLayoutTechnique() {
        LinearLayout result = new LinearLayout(getContext());
        result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        result.setOrientation(LinearLayout.HORIZONTAL);
        result.setGravity(Gravity.START);
        return result;
    }

    private void afficheSymptomes(Humeur humeur) {

        // Récupération des symptomes
        try {
            SymptomesActifs = new ArrayList<>(humeur.getSymptomesActifs());
            SymptomesInactifs = new ArrayList<>(humeur.getSymptomesInactifs());
        } catch (Exception e) {
            SymptomesActifs = new ArrayList<>();
            SymptomesInactifs = new ArrayList<>();
        }

        listesymptomes.removeAllViews();

        // Récupération de la taille de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        // Calcul de la largeur maximale
        int maxWidth = displayMetrics.widthPixels - (int) (getResources().getDimension(R.dimen.margin_small) * 2 + getResources().getDimension(R.dimen.margin_large) * 2 + getResources().getDimension(R.dimen.largeur_image_humeur));
        //Log.e("Test", "MaxWidth " + maxWidth);


        LinearLayout llTechnique = addLinearLayoutTechnique();
        TextView newTextView;
        int currentWidth = 0;
        int currentHeight = 100;

        for (int i = 0; i < SymptomesActifs.size() + SymptomesInactifs.size(); i++) {
            //Log.e("Test", "début " + i);
            newTextView = getSymptomeTextView(i < SymptomesActifs.size() ? SymptomesActifs.get(i) : SymptomesInactifs.get(i - SymptomesActifs.size()), i < SymptomesActifs.size());
            currentWidth += newTextView.getMeasuredWidth();
            currentHeight = newTextView.getMeasuredHeight();

            //Log.e("Test", "Width " + currentWidth);
            if (currentWidth > maxWidth) {
                //Log.e("Test", "Nouvelle ligne");
                listesymptomes.addView(llTechnique);
                llTechnique = addLinearLayoutTechnique();
                llTechnique.addView(newTextView);
                currentWidth = newTextView.getMeasuredWidth();
            } else {
                //Log.e("Test", "Ligne actuelle");
                llTechnique.addView(newTextView);
            }
        }

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.plus);
        int margin = (int) (getResources().getDimension(R.dimen.margin_small) / getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams marginSetter = new LinearLayout.LayoutParams(currentHeight, currentHeight);
        marginSetter.setMargins(margin, margin, margin, margin);
        imageView.setLayoutParams(marginSetter);
        imageView.setTag("IMAGE");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveEtatFromUI();
                if (activityCallback != null)
                    activityCallback.onNewSymptomeClicked(etat, quand);
            }
        });
        llTechnique.addView(imageView);

        listesymptomes.addView(llTechnique);

    }

    private void LoadEtatInUI() {

        Log.e("Test", "Départ config");

        getActivity().setTitle(etat.DateLisible());

        date.setText(etat.DateLisible());

        switch (this.quand) {
            case "Matin":
                sousTitre.setText("Humeur du matin");
                break;
            case "Aprem":
                sousTitre.setText("Humeur de l'après-midi");
                break;
            case "Soir":
                sousTitre.setText("Humeur du soir");
                break;
            default:
                sousTitre.setText("Humeur du matin");
        }

        meteo.setText(listeHumeurs.getListeNiveaux()
                .get(etat.getHumeurFromTag(this.quand).getHumeur())
                .getNom());
        angoisse.setText(listeAngoisses.getListeNiveaux()
                .get(etat.getHumeurFromTag(this.quand).getAngoisse())
                .getNom());
        energie.setText(listeEnergies.getListeNiveaux().
                get(etat.getHumeurFromTag(this.quand).getEnergie())
                .getNom());
        irritabilite.setText(listeIrritabilite.getListeNiveaux()
                .get(etat.getHumeurFromTag(this.quand).getIrritabilite())
                .getNom());
        commentaire.setText(etat.getHumeurFromTag(this.quand).getCommentaire());
        Log.e("Test", "Milieu");

        afficheSymptomes(etat.getHumeurFromTag(this.quand));

    }

    private void SaveEtatFromUI() {
        this.etat.getHumeurFromTag(quand).setCommentaire(commentaire.getText().toString());
        this.etat.getHumeurFromTag(quand).setSymptomesActifs(this.SymptomesActifs);
        this.etat.getHumeurFromTag(quand).setSymptomesInactifs(this.SymptomesInactifs);
    }

    @Override
    public void onClick(View v) {
        //if (v.getTag() == "IMAGE") {
        //
        //} else {
        TextView self = (TextView) v;
        String symptomeClique = self.getText().toString();
        //Log.e("Test", "Click sur " + symptomeClique);
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
        //}
    }


    @OnClick({R.id.feh_zonemeteo, R.id.feh_zoneangoisse, R.id.feh_zoneenergie, R.id.feh_zoneirritabilite})
    public void ClickChoixEtat(View v) {
        SaveEtatFromUI();

        if (activityCallback != null) {
            String type = "";
            switch (v.getId()) {
                case R.id.feh_zonemeteo:
                    type = "Humeur";
                    break;
                case R.id.feh_zoneangoisse:
                    type = "Angoisse";
                    break;
                case R.id.feh_zoneenergie:
                    type = "Energie";
                    break;
                case R.id.feh_zoneirritabilite:
                    type = "Irritabilite";
                    break;
            }
            activityCallback.onChoixEtatClicked(etat, quand, type);
        }
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

        void onNewSymptomeClicked(etat etat, String quand);
    }

}
