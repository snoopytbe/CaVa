package com.snoopytbe.cava.ListeEtats;

import java.io.Serializable;

public class NiveauEtat implements Serializable {
    private String nom;
    private String icone;

    public NiveauEtat(String nom) {
        this.nom = nom;
        this.icone = "";
    }

    public NiveauEtat(String nom, String icone) {
        this.nom = nom;
        this.icone = icone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    @Override
    public String toString() {
        return nom;
    }
}

