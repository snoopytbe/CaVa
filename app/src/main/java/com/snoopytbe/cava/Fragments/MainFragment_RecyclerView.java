package com.snoopytbe.cava.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.MainActivity;
import com.snoopytbe.cava.R;
import com.snoopytbe.cava.RecyclerView.EtatListAdapter;
import com.snoopytbe.cava.utils.ItemClickSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment_RecyclerView extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private EtatListAdapter adapter;
    public View myView;

    public MainFragment_RecyclerView() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (myView == null) {
            myView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
            ButterKnife.bind(this, myView);
            ConfigureRecyclerView();
            ConfigureOnClickRecyclerView();
        }
        return myView;
    }

    private void ConfigureRecyclerView() {
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
}
