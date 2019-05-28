package com.snoopytbe.cava.Fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.Humeur;
import com.snoopytbe.cava.Classes.ListeEtats;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.snoopytbe.cava.Classes.commun.etat_angoisse;
import static com.snoopytbe.cava.Classes.commun.etat_energie;
import static com.snoopytbe.cava.Classes.commun.etat_humeur;
import static com.snoopytbe.cava.Classes.commun.etat_irritabilite;
import static com.snoopytbe.cava.Classes.commun.moment_aprem;
import static com.snoopytbe.cava.Classes.commun.moment_matin;
import static com.snoopytbe.cava.Classes.commun.moment_soir;


public class HumeurFragment extends FragmentCaVa implements View.OnClickListener, ChoixEtatDialogFragment.ListeEtatsFragmentCallback, NewSymptomeDialogFragment.NewSymptomeDialogFragmentCallback {

    private ListeEtats.ListeAngoisses listeAngoisses;
    private ListeEtats.ListeHumeurs listeHumeurs;
    private ListeEtats.ListeEnergies listeEnergies;
    private ListeEtats.ListeIrritabilite listeIrritabilite;

    private String SymptomeASupprimer;
    private String tagEtats;
    private int idAjout;

    private ArrayList<String> SymptomesActifs;
    private ArrayList<String> SymptomesInactifs;


    private String quand;
    private Humeur humeur;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        etatViewModel.getMomentActuel().observe(this, momentFromGet -> {
            this.quand = momentFromGet;
            LoadEtatInUI();
        });
        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edition_humeur;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Timber.e("Menu Humeur");
        menu.clear();
        inflater.inflate(R.menu.menu_feh, menu);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case (R.id.menu_copy):
                copyTask copyTask = new copyTask();
                copyTask.execute();
                return true;
            case (R.id.menu_about):
                Toast.makeText(this.getContext(), "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        menu.add("Supprimer");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String SymptomeSupprime = SymptomeASupprimer; //view.getText().toString();
        if (SymptomesActifs.contains(SymptomeSupprime)) {
            SymptomesActifs.remove(SymptomeSupprime);
        } else SymptomesInactifs.remove(SymptomeSupprime);
        SaveEtatFromUI();
        LoadEtatInUI();
        return true;
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


        // TODO : clean this portion of code
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.plus);
        int margin = (int) (getResources().getDimension(R.dimen.margin_small) / getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams marginSetter = new LinearLayout.LayoutParams(currentHeight, currentHeight);
        marginSetter.setMargins(margin, margin, margin, margin);
        imageView.setLayoutParams(marginSetter);
        idAjout = View.generateViewId();
        imageView.setId(idAjout);
        imageView.setOnClickListener(this);
        llTechnique.addView(imageView);

        listesymptomes.addView(llTechnique);

    }

    private LinearLayout addLinearLayoutTechnique() {
        LinearLayout result = new LinearLayout(getContext());
        result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        result.setOrientation(LinearLayout.HORIZONTAL);
        result.setGravity(Gravity.START);
        return result;
    }

    protected void LoadEtatInUI() {

        if (!(etat == null || quand == null)) {
            Timber.e("Chargement HumeurFragment");

            listeHumeurs = new ListeEtats.ListeHumeurs();
            listeAngoisses = new ListeEtats.ListeAngoisses();
            listeIrritabilite = new ListeEtats.ListeIrritabilite();
            listeEnergies = new ListeEtats.ListeEnergies();
            humeur = etat.getHumeurFromTag(quand);

            switch (this.quand) {
                case moment_matin:
                    sousTitre.setText("Humeur du matin");
                    break;
                case moment_aprem:
                    sousTitre.setText("Humeur de l'après-midi");
                    break;
                case moment_soir:
                    sousTitre.setText("Humeur du soir");
                    break;
                default:
                    sousTitre.setText("Humeur du matin");
            }

            meteo.setText(listeHumeurs.getListeNiveaux()
                    .get(humeur.getHumeur())
                    .getNom());
            angoisse.setText(listeAngoisses.getListeNiveaux()
                    .get(humeur.getAngoisse())
                    .getNom());
            energie.setText(listeEnergies.getListeNiveaux().
                    get(humeur.getEnergie())
                    .getNom());
            irritabilite.setText(listeIrritabilite.getListeNiveaux()
                    .get(humeur.getIrritabilite())
                    .getNom());
            commentaire.setText(humeur.getCommentaire());


            afficheSymptomes(humeur);
        }
    }

    protected void SaveEtatFromUI() {
        this.humeur.setCommentaire(commentaire.getText().toString());
        this.humeur.setSymptomesActifs(this.SymptomesActifs);
        this.humeur.setSymptomesInactifs(this.SymptomesInactifs);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == idAjout) {
            NewSymptomeDialogFragment newSymptomeDialogFragment = new NewSymptomeDialogFragment();
            newSymptomeDialogFragment.setTargetFragment(this, 0);
            newSymptomeDialogFragment.show(getFragmentManager(), "NewSymptome");

        } else {

            TextView self = (TextView) v;
            String symptomeClique = self.getText().toString();

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
    }

    @OnClick({R.id.feh_zonemeteo, R.id.feh_zoneangoisse, R.id.feh_zoneenergie, R.id.feh_zoneirritabilite})
    public void onChoixEtatClicked(View v) {

        SaveEtatFromUI();

        ListeEtats listeEtats;

        switch (v.getId()) {
            case R.id.feh_zonemeteo:
                this.tagEtats = etat_humeur;
                listeEtats = new ListeEtats.ListeHumeurs();
                break;
            case R.id.feh_zoneangoisse:
                this.tagEtats = etat_angoisse;
                listeEtats = new ListeEtats.ListeAngoisses();
                break;
            case R.id.feh_zoneenergie:
                this.tagEtats = etat_energie;
                listeEtats = new ListeEtats.ListeEnergies();
                break;
            case R.id.feh_zoneirritabilite:
                this.tagEtats = etat_irritabilite;
                listeEtats = new ListeEtats.ListeIrritabilite();
                break;
            default:
                listeEtats = new ListeEtats.ListeHumeurs();
        }

        ChoixEtatDialogFragment choixEtatDialogFragment = ChoixEtatDialogFragment.newInstance(listeEtats);
        choixEtatDialogFragment.setTargetFragment(this, 0);
        choixEtatDialogFragment.show(getFragmentManager(), "etatPicker");
    }

    @Override
    public void onEtatClicked(int numero) {

        if (this.tagEtats == etat_angoisse) {
            this.humeur.setAngoisse(numero);
        } else if (this.tagEtats == etat_humeur) {
            this.humeur.setHumeur(numero);
        } else if (this.tagEtats == etat_energie) {
            this.humeur.setEnergie(numero);
        } else if (this.tagEtats == etat_irritabilite) {
            this.humeur.setIrritabilite(numero);
        }

        LoadEtatInUI();
    }

    @Override
    public void onOkNewSymptomeDialogFragment(String newSymptome) {
        SaveEtatFromUI();
        if (!newSymptome.isEmpty()) {
            if (!SymptomesInactifs.contains(newSymptome) && !SymptomesActifs.contains(newSymptome)) {
                humeur.getSymptomesActifs().add(newSymptome);
            } else {
                Toast.makeText(this.getContext(), "Le symptome existe déjà", Toast.LENGTH_LONG).show();
            }
        }
        LoadEtatInUI();
    }

    private class copyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            switch (quand) {
                case moment_matin:
                    etat hier = etatViewModel.getPrecedentEtat(etat.getDate());
                    etat.setHumeurMatin(hier.getHumeurSoir().copie());
                    break;
                case moment_aprem:
                    etat.setHumeurApresMidi(etat.getHumeurMatin().copie());
                    break;
                case moment_soir:
                    etat.setHumeurSoir(etat.getHumeurApresMidi().copie());
                    break;
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LoadEtatInUI();
        }
    }

}
