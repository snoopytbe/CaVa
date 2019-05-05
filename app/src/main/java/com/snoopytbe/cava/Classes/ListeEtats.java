package com.snoopytbe.cava.Classes;

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

    public static class ListeEnergies extends ListeEtats {
        @Override
        protected void InitialiserDonnees() {
            this.ListeNiveaux.add(new NiveauEtat("-"));
            this.ListeNiveaux.add(new NiveauEtat("Aucune énergie"));
            this.ListeNiveaux.add(new NiveauEtat("Très fatigué"));
            this.ListeNiveaux.add(new NiveauEtat("Fatigué"));
            this.ListeNiveaux.add(new NiveauEtat("En forme"));
            this.ListeNiveaux.add(new NiveauEtat("En pleine forme"));
        }
    }

    public static class ListeAngoisses extends ListeEtats {

        @Override
        protected void InitialiserDonnees() {
            this.ListeNiveaux.add(new NiveauEtat("-"));
            this.ListeNiveaux.add(new NiveauEtat("Pas angoissé"));
            this.ListeNiveaux.add(new NiveauEtat("Légèrement angoissé"));
            this.ListeNiveaux.add(new NiveauEtat("Moyennement angoissé"));
            this.ListeNiveaux.add(new NiveauEtat("Fortement angoissé"));
            this.ListeNiveaux.add(new NiveauEtat("Paniqué"));
        }
    }

    public static class ListeHumeurs extends ListeEtats {
        @Override
        protected void InitialiserDonnees() {
            this.ListeNiveaux.add(new NiveauEtat("-"));
            this.ListeNiveaux.add(new NiveauEtat("Brouillard noir"));
            this.ListeNiveaux.add(new NiveauEtat("Très nuageux"));
            this.ListeNiveaux.add(new NiveauEtat("Nuageux"));
            this.ListeNiveaux.add(new NiveauEtat("Soleil"));
            this.ListeNiveaux.add(new NiveauEtat("Grand soleil"));
        }
    }

    public static class ListeIrritabilite extends ListeEtats {
        @Override
        protected void InitialiserDonnees() {
            this.ListeNiveaux.add(new NiveauEtat("-"));
            this.ListeNiveaux.add(new NiveauEtat("Cool"));
            this.ListeNiveaux.add(new NiveauEtat("Impatient"));
            this.ListeNiveaux.add(new NiveauEtat("Part au 1/4 de tour"));
            this.ListeNiveaux.add(new NiveauEtat("Colérique"));
            this.ListeNiveaux.add(new NiveauEtat("Violent"));
        }
    }
}
