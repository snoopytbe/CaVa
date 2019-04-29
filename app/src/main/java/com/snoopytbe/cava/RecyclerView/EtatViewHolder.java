package com.snoopytbe.cava.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.snoopytbe.cava.R;
import com.snoopytbe.cava.db.etat;

public class EtatViewHolder extends RecyclerView.ViewHolder {
    private final TextView etatTextView;

    public EtatViewHolder(@NonNull View itemView) {
        super(itemView);
        etatTextView = itemView.findViewById(R.id.textView);
    }

    public void UpdateWithEtat(etat etat) {
        etatTextView.setText(etat.DateLisible());
    }
}
