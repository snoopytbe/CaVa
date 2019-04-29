package com.snoopytbe.cava.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;

public class Humeur implements Serializable {
    @ColumnInfo(name = "humeur")
    private int humeur;

    @ColumnInfo(name = "angoisse")
    private int angoisse;

    @ColumnInfo(name = "commentaire")
    private String commentaire;

    public Humeur(int humeur, int angoisse, String commentaire) {
        this.humeur = humeur;
        this.angoisse = angoisse;
        this.commentaire = commentaire;
    }

    @Ignore
    public Humeur() {
        this.humeur = 1;
        this.angoisse = 0;
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

}
