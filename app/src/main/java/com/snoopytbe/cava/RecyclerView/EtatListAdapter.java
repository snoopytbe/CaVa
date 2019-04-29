package com.snoopytbe.cava.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoopytbe.cava.R;
import com.snoopytbe.cava.db.etat;

import java.util.ArrayList;
import java.util.List;

public class EtatListAdapter extends RecyclerView.Adapter<EtatViewHolder> {

    private final LayoutInflater mInflater;
    private List<etat> mEtats = new ArrayList<etat>();

    public EtatListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EtatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_etat, parent, false);
        return new EtatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EtatViewHolder holder, int position) {
        holder.UpdateWithEtat(mEtats.get(position));
    }

    public void setEtats(List<etat> etats) {
        mEtats = etats;
        notifyDataSetChanged();
    }

    public etat getEtat(int position) {
        return mEtats.get(position);
    }

    @Override
    public int getItemCount() {
        return mEtats.size();
    }


}
