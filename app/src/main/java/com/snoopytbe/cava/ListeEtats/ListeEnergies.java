package com.snoopytbe.cava.ListeEtats;

public class ListeEnergies extends ListeEtats {
    @Override
    protected void InitialiserDonnees() {
        this.ListeNiveaux.add(new NiveauEtat("-"));
        this.ListeNiveaux.add(new NiveauEtat("Au lit"));
        this.ListeNiveaux.add(new NiveauEtat("Très fatigué"));
        this.ListeNiveaux.add(new NiveauEtat("Fatigué"));
        this.ListeNiveaux.add(new NiveauEtat("En forme"));
        this.ListeNiveaux.add(new NiveauEtat("En pleine forme"));
    }
}