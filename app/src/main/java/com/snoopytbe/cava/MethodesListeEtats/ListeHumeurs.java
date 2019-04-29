package com.snoopytbe.cava.MethodesListeEtats;

public class ListeHumeurs extends ListeEtats {
    @Override
    protected void InitialiserDonnees() {
        this.ListeNiveaux.add(new NiveauEtat("Grand soleil"));
        this.ListeNiveaux.add(new NiveauEtat("Soleil"));
        this.ListeNiveaux.add(new NiveauEtat("Brumeux"));
        this.ListeNiveaux.add(new NiveauEtat("Nuageux"));
        this.ListeNiveaux.add(new NiveauEtat("Très nuageux"));
        this.ListeNiveaux.add(new NiveauEtat("Brouillard noir"));
    }
}
