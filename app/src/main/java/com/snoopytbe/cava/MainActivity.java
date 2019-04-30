package com.snoopytbe.cava;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.snoopytbe.cava.Fragments.HumeurFragment;
import com.snoopytbe.cava.Fragments.JourneeFragment;
import com.snoopytbe.cava.Fragments.ListeEtatsFragment;
import com.snoopytbe.cava.Fragments.MainFragment;
import com.snoopytbe.cava.Fragments.SommeilFragment;
import com.snoopytbe.cava.Fragments.TimeFragment;
import com.snoopytbe.cava.Fragments.TraitementFragment;
import com.snoopytbe.cava.ListeEtats.ListeAngoisses;
import com.snoopytbe.cava.ListeEtats.ListeEnergies;
import com.snoopytbe.cava.ListeEtats.ListeEtats;
import com.snoopytbe.cava.ListeEtats.ListeHumeurs;
import com.snoopytbe.cava.ListeEtats.ListeIrritabilite;
import com.snoopytbe.cava.db.HeuresMinutes;
import com.snoopytbe.cava.db.etat;
import com.snoopytbe.cava.db.etatViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements HumeurFragment.HumeurFragmentCallback,
        SommeilFragment.SommeilFragmentCallback,
        TimePickerDialog.OnTimeSetListener,
        ListeEtatsFragment.ListeEtatsFragmentCallback,
        JourneeFragment.JourneeFragmentCallback,
        TraitementFragment.TraitementFragmentCallback {

    private MainFragment mainFragment;
    private etatViewModel etatViewModel;
    private etat etatActuel;
    private String tagTimeset;
    private String tagEtats;
    private String tagMoment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //importDB();
        setContentView(R.layout.activity_main);
        this.configureAndShowMainFragment();
        this.ConfigureDb();
    }


    private void ConfigureDb() {
        etatViewModel = ViewModelProviders.of(this).get(etatViewModel.class);
        etatViewModel.getAllEtats().observe(this, new Observer<List<etat>>() {
            @Override
            public void onChanged(@Nullable final List<etat> etats) {
                mainFragment.setEtats(etats);
            }
        });
    }

    private void configureAndShowMainFragment() {

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main_layout, mainFragment)
                    .addToBackStack("Main")
                    .commit();
        }
    }

    public void ShowMainFragment() {
        etatActuel = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, mainFragment)
                .addToBackStack("Main")
                .commit();
    }

    public void ShowHumeurFragment(etat etat, String quand) {
        this.etatActuel = etat;
        HumeurFragment humeurFragment = HumeurFragment.newInstance(etat, quand);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, humeurFragment, null)
                .addToBackStack("Humeur")
                .commit();
    }

    public void ShowJourneeFragment(etat etat) {
        this.etatActuel = etat;
        JourneeFragment journeeFragment = JourneeFragment.newInstance(etat);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, journeeFragment, null)
                .addToBackStack("Journee")
                .commit();
    }


    @Override
    public void ShowSommeilFragment(etat etat) {
        this.etatActuel = etat;
        SommeilFragment sommeilFragment = SommeilFragment.newInstance(etat);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, sommeilFragment)
                .addToBackStack("Sommeil")
                .commit();
    }

    @Override
    public void ShowTraitementFragment(etat etat) {
        this.etatActuel = etat;
        TraitementFragment traitementFragment = TraitementFragment.newInstance(etat);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, traitementFragment)
                .addToBackStack("Traitement")
                .commit();
    }

    @Override
    public void onOKFragmentHumeur(etat etat) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowJourneeFragment(this.etatActuel);
    }

    @Override
    public void onOKFragmentTraitement(etat etat) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowJourneeFragment(this.etatActuel);
    }

    @Override
    public void onChoixEtatClicked(etat etat, String tagMoment, String tagEtats) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        this.tagEtats = tagEtats;
        this.tagMoment = tagMoment;

        ListeEtats listeEtats = new ListeAngoisses();

        if (tagEtats == "Angoisse") {
            listeEtats = new ListeAngoisses();
        } else if (tagEtats == "Humeur") {
            listeEtats = new ListeHumeurs();
        } else if (tagEtats == "Energie") {
            listeEtats = new ListeEnergies();
        } else if (tagEtats == "Irritabilite") {
            listeEtats = new ListeIrritabilite();
        }

        ListeEtatsFragment listeEtatsFragment = ListeEtatsFragment.newInstance(listeEtats);
        listeEtatsFragment.show(getSupportFragmentManager(), "etatPicker");
    }

    @Override
    public void onOKFragmentJournee(etat etat) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowMainFragment();
    }

    @Override
    public void onOkFragmentSommeil(etat etat) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowJourneeFragment(etat);
    }

    @Override
    public void onHeuresSommeilClicked(etat etat, String tagTimeset) {
        this.etatActuel = etat;
        this.tagTimeset = tagTimeset;
        Log.e("test", "onHeuresSommeilClicked: " + tagTimeset);
        HeuresMinutes heuresMinutes = new HeuresMinutes();
        if (tagTimeset == "Lever") {
            heuresMinutes = etat.getQualiteSommeil().getHeureLever();
        } else if (tagTimeset == "Coucher") {
            heuresMinutes = etat.getQualiteSommeil().getHeureCoucher();
        } else if (tagTimeset == "Insomnie") {
            heuresMinutes = etat.getQualiteSommeil().getHeuresInsomnie();
        }
        TimeFragment timeFragment = TimeFragment.newInstance(heuresMinutes);
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onChargeEtatClicked(etat etat, String quand) {
        this.tagMoment = quand;
        ShowHumeurFragment(etat, quand);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.e("test", "onTimeSet: " + tagTimeset);
        if (this.tagTimeset == "Lever") {
            this.etatActuel.getQualiteSommeil().setHeureLever(new HeuresMinutes(hourOfDay, minute));
        } else if (this.tagTimeset == "Coucher") {
            this.etatActuel.getQualiteSommeil().setHeureCoucher(new HeuresMinutes(hourOfDay, minute));
        } else if (this.tagTimeset == "Insomnie") {
            this.etatActuel.getQualiteSommeil().setHeuresInsomnie(new HeuresMinutes(hourOfDay, minute));
        }
        this.ShowSommeilFragment(this.etatActuel);
    }

    @Override
    public void onEtatClicked(int numero) {

        if (this.tagEtats == "Angoisse") {
            if (this.tagMoment == "Matin") {
                this.etatActuel.getHumeurMatin().setAngoisse(numero);
            } else if (this.tagMoment == "Aprem") {
                this.etatActuel.getHumeurApresMidi().setAngoisse(numero);
            } else if (this.tagMoment == "Soir") {
                this.etatActuel.getHumeurSoir().setAngoisse(numero);
            }
        } else if (this.tagEtats == "Humeur") {
            if (this.tagMoment == "Matin") {
                this.etatActuel.getHumeurMatin().setHumeur(numero);
            } else if (this.tagMoment == "Aprem") {
                this.etatActuel.getHumeurApresMidi().setHumeur(numero);
            } else if (this.tagMoment == "Soir") {
                this.etatActuel.getHumeurSoir().setHumeur(numero);
            }
        } else if (this.tagEtats == "Energie") {
            if (this.tagMoment == "Matin") {
                this.etatActuel.getHumeurMatin().setEnergie(numero);
            } else if (this.tagMoment == "Aprem") {
                this.etatActuel.getHumeurApresMidi().setEnergie(numero);
            } else if (this.tagMoment == "Soir") {
                this.etatActuel.getHumeurSoir().setEnergie(numero);
            }
        } else if (this.tagEtats == "Irritabilite") {
            if (this.tagMoment == "Matin") {
                this.etatActuel.getHumeurMatin().setIrritabilite(numero);
            } else if (this.tagMoment == "Aprem") {
                this.etatActuel.getHumeurApresMidi().setIrritabilite(numero);
            } else if (this.tagMoment == "Soir") {
                this.etatActuel.getHumeurSoir().setIrritabilite(numero);
            }
        }
        ShowHumeurFragment(this.etatActuel, this.tagMoment);
    }

    private void importDB() {
        try {
            // File to import
            File backup = new File("//sdcard" + File.separator + "SuiviEtatBackupDBs" + File.separator + "etat_database.db");
            FileInputStream input = new FileInputStream(backup);

            //Where to import
            File dbFile = new File(this.getDatabasePath("etat_database").getAbsolutePath());
            OutputStream output = new FileOutputStream(dbFile);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            input.close();

            Toast.makeText(this.getApplicationContext(), "Import successfull", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("importDB:", e.getMessage());
        }
    }

}
