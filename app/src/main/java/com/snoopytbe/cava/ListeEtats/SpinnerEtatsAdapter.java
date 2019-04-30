package com.snoopytbe.cava.ListeEtats;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerEtatsAdapter extends ArrayAdapter<NiveauEtat> {

    public SpinnerEtatsAdapter(Context context, ListeEtats ListeEtats) {
        super(context, android.R.layout.simple_spinner_item, ListeEtats.getListeNiveaux());
        super.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

}
