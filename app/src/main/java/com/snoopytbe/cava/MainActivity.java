package com.snoopytbe.cava;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.snoopytbe.cava.Classes.ListeEtats;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.Fragments.HumeurFragment;
import com.snoopytbe.cava.Fragments.JourneeFragment;
import com.snoopytbe.cava.Fragments.ListeEtatsFragment;
import com.snoopytbe.cava.Fragments.MainFragment_ViewPager;
import com.snoopytbe.cava.Fragments.SommeilDialogFragment;
import com.snoopytbe.cava.Fragments.SommeilFragment;
import com.snoopytbe.cava.Fragments.TraitementFragment;
import com.snoopytbe.cava.db.etatViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import icepick.Icepick;

public class MainActivity extends AppCompatActivity
        implements HumeurFragment.HumeurFragmentCallback,
        SommeilFragment.SommeilFragmentCallback,
        ListeEtatsFragment.ListeEtatsFragmentCallback,
        JourneeFragment.JourneeFragmentCallback,
        TraitementFragment.TraitementFragmentCallback,
        SommeilDialogFragment.DialogHeuresMinutesCallback {

    //private MainFragment_RecyclerView mainFragment;
    private MainFragment_ViewPager mainFragment;
    private etatViewModel etatViewModel;

    private String currentFragment;
    private etat etatActuel;
    private String tagMoment;
    private String tagEtats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

        //importDB();
        Log.e("Test", "Top départ");
        setContentView(R.layout.activity_main);

        if (etatViewModel == null) {
            Log.e("Test", "ConfigureDb");
            this.ConfigureDb();
        }
        //if (currentFragment==null) {
        this.configureAndShowMainFragment();

        /*} else switch (currentFragment) {
            case "Main":
                Log.e("Test", "Affichage configureAndShowMainFragment");
                this.configureAndShowMainFragment();
                break;
            case "Journee":
                Log.e("Test", "Affichage ShowJourneeFragment");
                this.ShowJourneeFragment(this.etatActuel);
                break;
            case "Sommeil":
                this.ShowSommeilFragment(this.etatActuel);
                break;
            case "Traitement":
                this.ShowTraitementFragment(this.etatActuel);
                break;
            case "Humeur":
                this.ShowHumeurFragment(this.etatActuel, this.tagMoment);
                break;
            default:
                this.ShowMainFragment();
                break;
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("Test", "Sauvegarde InstanceState");
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
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

        currentFragment = "Main";
        //mainFragment = (MainFragment_RecyclerView) getSupportFragmentManager().findFragmentById(R.id.activity_main_layout);
        mainFragment = (MainFragment_ViewPager) getSupportFragmentManager().findFragmentById(R.id.activity_main_layout);
        Log.e("Test", "configureAndShowMainFragment: ");
        if (mainFragment == null) {
            //mainFragment = new MainFragment_RecyclerView();
            mainFragment = new MainFragment_ViewPager();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main_layout, mainFragment)
                    .addToBackStack("Main")
                    .commit();
        }
    }

    public void ShowMainFragment() {
        //if (currentFragment != "Main") {
        //    currentFragment = "Main";
        etatActuel = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, mainFragment)
                .addToBackStack("Main")
                .commit();
        //}
    }

    public void ShowJourneeFragment(etat etat) {
        ShowMainFragment();
        /*Log.e("Test", "ShowJourneeFragment : " + etat.getTraitement().getCommentaire());
        currentFragment = "Journee";
        this.etatActuel = etat;
        JourneeFragment journeeFragment = JourneeFragment.newInstance(etat);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, journeeFragment, null)
                .addToBackStack("Journee")
                .commit();*/
    }

    public void ShowHumeurFragment(etat etat, String quand) {
        currentFragment = "Humeur";
        this.etatActuel = etat;
        HumeurFragment humeurFragment = HumeurFragment.newInstance(etat, quand);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, humeurFragment, null)
                .addToBackStack("Humeur")
                .commit();
    }

    @Override
    public void ShowSommeilFragment(etat etat) {
        currentFragment = "Sommeil";
        this.etatActuel = etat;
        SommeilFragment sommeilFragment = SommeilFragment.newInstance(etat);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, sommeilFragment)
                .addToBackStack("Sommeil")
                .commit();
    }

    @Override
    public void ShowHeuresSommeil(etat etat) {
        this.etatActuel = etat;
        SommeilDialogFragment sommeilDialogFragment = SommeilDialogFragment.newInstance(etat);
        sommeilDialogFragment.show(getSupportFragmentManager(), "EditionHeure");
    }


    @Override
    public void ShowTraitementFragment(etat etat) {
        currentFragment = "Traitement";
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
        Log.e("Test", "onOKFragmentTraitement : " + etat.getTraitement().getCommentaire());
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowJourneeFragment(this.etatActuel);
    }

    @Override
    public void onOkDialogFragmentHeuresMinutes(etat etat) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowSommeilFragment(this.etatActuel);
    }

    @Override
    public void onChoixEtatClicked(etat etat, String tagMoment, String tagEtats) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        this.tagEtats = tagEtats;
        this.tagMoment = tagMoment;

        ListeEtats listeEtats = new ListeEtats.ListeAngoisses();

        if (tagEtats == "Angoisse") {
            listeEtats = new ListeEtats.ListeAngoisses();
        } else if (tagEtats == "Humeur") {
            listeEtats = new ListeEtats.ListeHumeurs();
        } else if (tagEtats == "Energie") {
            listeEtats = new ListeEtats.ListeEnergies();
        } else if (tagEtats == "Irritabilite") {
            listeEtats = new ListeEtats.ListeIrritabilite();
        }

        ListeEtatsFragment listeEtatsFragment = ListeEtatsFragment.newInstance(listeEtats);
        listeEtatsFragment.show(getSupportFragmentManager(), "etatPicker");
    }

    @Override
    public void onOKFragmentJournee(etat etat) {
        Log.e("Test", "onOKFragmentJournee : " + etat.getTraitement().getCommentaire());
        this.etatActuel = etat;
        etatViewModel.update(etat);
        this.ShowMainFragment();
    }

    @Override
    public void onRetourFragmentSommeil(etat etat) {
        this.etatActuel = etat;
        etatViewModel.update(etat);
        ShowJourneeFragment(etat);
    }

    @Override
    public void onChargeEtatClicked(etat etat, String quand) {
        this.tagMoment = quand;
        ShowHumeurFragment(etat, quand);
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

    public void exportDB() {
        try {
            // Database to backup
            File dbFile = new File(this.getDatabasePath("etat_database").getAbsolutePath());
            FileInputStream input = new FileInputStream(dbFile);

            // Where to backup
            String outFileName = "//sdcard" + File.separator + "SuiviEtatBackupDBs";
            File folder = new File(outFileName);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            outFileName += File.separator + "etat_database.db";
            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

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

            Toast.makeText(this.getApplicationContext(), "Backup successfull", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("exportDB:", e.getMessage());
        }
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
