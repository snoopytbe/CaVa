package com.snoopytbe.cava.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;
import java.util.ArrayList;

public class Humeur implements Serializable {
    @ColumnInfo(name = "humeur")
    private int humeur;

    @ColumnInfo(name = "angoisse")
    private int angoisse;

    @ColumnInfo(name = "energie")
    private int energie;

    @ColumnInfo(name = "irritabilite")
    private int irritabilite;

    @ColumnInfo(name = "commentaire")
    private String commentaire;

    @ColumnInfo(name = "symptomesActifs")
    private ArrayList<String> symptomesActifs;

    @ColumnInfo(name = "symptomesInactifs")
    private ArrayList<String> symptomesInactifs;

    private void InitialiseSymptomes() {
        this.symptomesInactifs = new ArrayList<>();
        this.symptomesActifs = new ArrayList<>();
        this.symptomesInactifs.add("Pensées rapides");
        this.symptomesInactifs.add("Envie de bcp parler");
        this.symptomesInactifs.add("Pas envie de se coucher");
        this.symptomesInactifs.add("Disperse");
        this.symptomesInactifs.add("Obsessions");
        this.symptomesInactifs.add("Ennui");
        this.symptomesInactifs.add("Cynisme");
        this.symptomesInactifs.add("Pleurs");
        this.symptomesInactifs.add("Envie de s'isoler");
        this.symptomesInactifs.add("Sentiment d'échec");
        this.symptomesInactifs.add("Envie de tout plaquer");
        this.symptomesInactifs.add("Idées suicidaires");
    }

    public Humeur(int humeur, int angoisse, int energie, int irritabilite, String commentaire) {
        this.humeur = humeur;
        this.angoisse = angoisse;
        this.energie = energie;
        this.irritabilite = irritabilite;
        this.commentaire = commentaire;
        InitialiseSymptomes();
    }

    @Ignore
    public Humeur() {
        this.humeur = 0;
        this.angoisse = 0;
        this.energie = 0;
        this.irritabilite = 0;
        this.commentaire = "";
        InitialiseSymptomes();
    }

    public int getHumeur() {
        return humeur;
    }

    public void setHumeur(int humeur) {
        this.humeur = humeur;
    }

    public int getAngoisse() {
        return angoisse;
    }

    public void setAngoisse(int angoisse) {
        this.angoisse = angoisse;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getEnergie() {
        return energie;
    }

    public void setEnergie(int energie) {
        this.energie = energie;
    }

    public int getIrritabilite() {
        return irritabilite;
    }

    public void setIrritabilite(int irritabilite) {
        this.irritabilite = irritabilite;
    }

    public ArrayList<String> getSymptomesActifs() {
        return symptomesActifs;
    }

    public void setSymptomesActifs(ArrayList<String> symptomesActifs) {
        this.symptomesActifs = symptomesActifs;
    }

    public ArrayList<String> getSymptomesInactifs() {
        return symptomesInactifs;
    }

    public void setSymptomesInactifs(ArrayList<String> symptomesInactifs) {
        this.symptomesInactifs = symptomesInactifs;
    }
}
