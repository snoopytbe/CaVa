package com.snoopytbe.cava.db;

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

    public Humeur(int humeur, int angoisse, int energie, int irritabilite, String commentaire) {
        this.humeur = humeur;
        this.angoisse = angoisse;
        this.energie = energie;
        this.irritabilite = irritabilite;
        this.commentaire = commentaire;
    }

    @Ignore
    public Humeur() {
        this.humeur = 0;
        this.angoisse = 0;
        this.energie = 0;
        this.irritabilite = 0;
        this.commentaire = "";
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
