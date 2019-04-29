package com.snoopytbe.cava.MethodesListeEtats;

public class ListeAngoisses extends ListeEtats {

    @Override
    protected void InitialiserDonnees() {
        this.ListeNiveaux.add(new NiveauEtat("Pas angoissé"));
        this.ListeNiveaux.add(new NiveauEtat("Angoisse légère"));
        this.ListeNiveaux.add(new NiveauEtat("Angoisse Moyenne"));
        this.ListeNiveaux.add(new NiveauEtat("Angoisse forte"));
    }
}
