package com.snoopytbe.cava.ListeEtats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ListeEtats implements Serializable {

    protected ArrayList<NiveauEtat> ListeNiveaux;

    public ListeEtats() {
        this.ListeNiveaux = new ArrayList<>();
        this.InitialiserDonnees();
    }

    protected abstract void InitialiserDonnees();

    final public ArrayList<NiveauEtat> getListeNiveaux() {
        return ListeNiveaux;
    }

    final public List<String> getStringListeNiveaux() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < this.ListeNiveaux.size(); i += 1) {
            result.add(this.ListeNiveaux.get(i).getNom());
        }
        return result;
    }

    final public void setListeNiveaux(ArrayList<NiveauEtat> ListeNiveaux) {
        this.ListeNiveaux = ListeNiveaux;
    }
}
