package com.snoopytbe.cava.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import static com.snoopytbe.cava.Classes.commun.etat_angoisse;
import static com.snoopytbe.cava.Classes.commun.etat_energie;
import static com.snoopytbe.cava.Classes.commun.etat_humeur;
import static com.snoopytbe.cava.Classes.commun.etat_irritabilite;

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

    public Humeur(int humeur, int angoisse, int energie, int irritabilite, String commentaire, ArrayList<String> symptomesActifs, ArrayList<String> symptomesInactifs) {
        this.humeur = humeur;
        this.angoisse = angoisse;
        this.energie = energie;
        this.irritabilite = irritabilite;
        this.commentaire = commentaire;
        this.symptomesInactifs = symptomesInactifs;
        this.symptomesActifs = symptomesActifs;
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

    @Ignore
    public Humeur(ArrayList<String> symptomes) {
        this.humeur = 0;
        this.angoisse = 0;
        this.energie = 0;
        this.irritabilite = 0;
        this.commentaire = "";
        InitialiseSymptomes(symptomes);
    }

    public Humeur copie() {
        Humeur result = new Humeur(this.humeur, this.angoisse, this.energie, this.irritabilite, this.commentaire, this.symptomesActifs, this.symptomesInactifs);
        return result;
    }

    private void InitialiseSymptomes(ArrayList<String> symptomes) {
        this.symptomesInactifs = new ArrayList<>();
        this.symptomesActifs = new ArrayList<>();
        this.symptomesInactifs.addAll(symptomes);
    }

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

    public int getHumeurFromTag(String tagHumeur) {
        switch (tagHumeur) {
            case etat_humeur:
                return this.humeur;
            case etat_energie:
                return this.energie;
            case etat_angoisse:
                return this.angoisse;
            case etat_irritabilite:
                return this.irritabilite;
            default:
                return this.humeur;
        }
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

    public ArrayList<String> getAllSymptomes() {
        ArrayList<String> result = new ArrayList<>(symptomesActifs);

        for (int i = 0; i < symptomesInactifs.size(); i++) {
            if (!result.contains(symptomesInactifs.get(i)))
                result.add(symptomesInactifs.get(i));
        }

        Collections.sort(result);

        return result;
    }

    public void setSymptomesInactifs(ArrayList<String> symptomesInactifs) {
        this.symptomesInactifs = symptomesInactifs;
    }
}
