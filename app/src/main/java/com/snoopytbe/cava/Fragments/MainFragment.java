package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.R;
import com.snoopytbe.cava.RecyclerView.EtatListAdapter;
import com.snoopytbe.cava.db.etat;
import com.snoopytbe.cava.utils.ItemClickSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private EtatListAdapter adapter;
    public View myView;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (myView == null) {
        myView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, myView);
        ConfigureRecyclerView(myView);
        ConfigureOnClickRecyclerView();
        }
        return myView;
    }

    private void ConfigureRecyclerView(View view) {
        adapter = new EtatListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setEtats(List<etat> etats) {
        adapter.setEtats(etats);
    }

    private void ConfigureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.recyclerview_item_etat)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        etat etat = adapter.getEtat(position);
                        ((MainActivity) getActivity()).ShowJourneeFragment(etat);
                    }
                });
    }

    //@OnClick(R.id.buttonSave)
    public void exportDB() {
        try {
            // Database to backup
            File dbFile = new File(((MainActivity) getActivity()).getDatabasePath("etat_database").getAbsolutePath());
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

            Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Backup successfull", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("exportDB:", e.getMessage());
        }
    }

}
