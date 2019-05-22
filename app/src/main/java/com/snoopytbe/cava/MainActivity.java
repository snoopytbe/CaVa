package com.snoopytbe.cava;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.Fragments.HumeurFragment;
import com.snoopytbe.cava.Fragments.JourneeFragment;
import com.snoopytbe.cava.Fragments.MainFragment_ViewPager;
import com.snoopytbe.cava.Fragments.SommeilFragment;
import com.snoopytbe.cava.Fragments.TraitementFragment;
import com.snoopytbe.cava.db.etatViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements JourneeFragment.JourneeFragmentCallback,
        FragmentCaVa.FragmentCaVaCallback {

    //private MainFragment_RecyclerView mainFragment;
    private MainFragment_ViewPager mainFragment;
    private etatViewModel etatViewModel;

    @BindView(R.id.toolbar_menu)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Timber.e("Départ");

        Icepick.restoreInstanceState(this, savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ConfigureDb();
        configureAndShowMainFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.e("Sauvegarde InstanceState");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_about):
                Toast.makeText(this.getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureAndShowMainFragment() {

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

    public void remplaceFragment(Fragment fragment, String nom) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, fragment, null)
                .addToBackStack(nom)
                .commit();
    }

    @Override
    public void ShowHumeurFragment(etat etat, String quand) {
        HumeurFragment humeurFragment = HumeurFragment.newInstance(etat, quand);
        remplaceFragment(humeurFragment, "Humeur");
    }

    @Override
    public void ShowSommeilFragment(etat etat) {
        SommeilFragment sommeilFragment = SommeilFragment.newInstance(etat);
        remplaceFragment(sommeilFragment, "Sommeil");
    }

    @Override
    public void ShowTraitementFragment(etat etat) {
        TraitementFragment traitementFragment = TraitementFragment.newInstance(etat);
        remplaceFragment(traitementFragment, "Traitement");
    }

    @Override
    public void sauveEtat(etat etat) {
        etatViewModel.update(etat);
    }


    public etatViewModel getEtatViewModel() {
        return etatViewModel;
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

        /*public etat copiePrecedenteHumeur(etat etat, String tagMoment) {
        etat result = etat;
        if (tagMoment == "Matin") {

            etatViewModel.getPrecedentEtat(etat.getDate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<etat>() {
                        @Override
                        public void onSuccess(etat hier) {
                            result.setHumeurMatin(hier.getHumeurSoir());
                            Timber.e("Copie réalisée");
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }

        return result;
    }*/
}
