package com.snoopytbe.cava.ListeEtats;

public class ListeIrritabilite extends ListeEtats {
    @Override
    protected void InitialiserDonnees() {
        this.ListeNiveaux.add(new NiveauEtat("-"));
        this.ListeNiveaux.add(new NiveauEtat("Cool"));
        this.ListeNiveaux.add(new NiveauEtat("Impatient"));
        this.ListeNiveaux.add(new NiveauEtat("Part au 1/4 de tour"));
        this.ListeNiveaux.add(new NiveauEtat("Colérique"));
        this.ListeNiveaux.add(new NiveauEtat("Invivable"));
    }
}