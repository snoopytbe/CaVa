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
import com.snoopytbe.cava.Fragments.GraphiqueFragment;
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
import icepick.State;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements JourneeFragment.JourneeFragmentCallback,
        FragmentCaVa.FragmentCaVaCallback {

    //private MainFragment_RecyclerView mainFragment;
    private MainFragment_ViewPager mainFragment;
    private GraphiqueFragment graphiqueFragment;
    @State
    int idEtat;
    @State
    String fragmentActif = "";
    private SommeilFragment sommeilFragment;
    private etatViewModel etatViewModel;
    private HumeurFragment humeurFragment;
    private TraitementFragment traitementFragment;

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

        configureAndShowFragments();
        ConfigureDb();
        //configureAndShowMainFragment();

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
                graphiqueFragment.setEtats(etats);
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

    private void configureAndShowFragments() {

        mainFragment = new MainFragment_ViewPager();
        graphiqueFragment = new GraphiqueFragment();
        sommeilFragment = new SommeilFragment();
        traitementFragment = new TraitementFragment();
        sommeilFragment = new SommeilFragment();
        humeurFragment = new HumeurFragment();

        switch (fragmentActif) {
            case "Main":
                afficheFragment(mainFragment, "Main");
                break;
            case "Traitement":
                afficheFragment(traitementFragment, "Traitement");
                break;
            case "Sommeil":
                afficheFragment(sommeilFragment, "Sommeil");
                break;
            case "Humeur":
                afficheFragment(humeurFragment, "Humeur");
                break;
            case "Graphique":
                afficheFragment(graphiqueFragment, "Graphique");
                break;
            default:
                afficheFragment(mainFragment, "Main");
        }
    }

    public void afficheFragment(Fragment fragment, String nom) {
        fragmentActif = nom;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, fragment, null)
                .addToBackStack(nom)
                .commit();
    }

    @Override
    public void ShowHumeurFragment() {
        afficheFragment(humeurFragment, "Humeur");
    }

    @Override
    public void ShowSommeilFragment() {
        afficheFragment(sommeilFragment, "Sommeil");
    }

    @Override
    public void ShowTraitementFragment() {
        afficheFragment(traitementFragment, "Traitement");
    }

    @Override
    public void ShowGraphiqueFragment() {
        afficheFragment(graphiqueFragment, "Graphique");
    }

    @Override
    public void onBackPressed() {
        afficheFragment(mainFragment, "Main");
        mainFragment.goToPage(etatViewModel.getPosActuelle());
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
