package com.snoopytbe.cava.ListeEtats;

public class ListeHumeurs extends ListeEtats {
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
