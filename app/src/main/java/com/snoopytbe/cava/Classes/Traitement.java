package com.snoopytbe.cava.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;

public class Traitement implements Serializable {
    @ColumnInfo(name = "actuel")
    private String actuel;

    @ColumnInfo(name = "respecte")
    private Boolean respecte;

    @ColumnInfo(name = "commentaire")
    private String commentaire;

    public Traitement(String actuel, Boolean respecte, String commentaire) {
        this.actuel = actuel;
        this.respecte = respecte;
        this.commentaire = commentaire;
    }

    @Ignore
    public Traitement(String actuel) {
        this.actuel = actuel;
        this.respecte = true;
        this.commentaire = "";
    }

    @Ignore
    public Traitement() {
        this.actuel = "";
        this.respecte = true;
        this.commentaire = "";
    }

    public String getActuel() {
        return actuel;
    }

    public void setActuel(String actuel) {
        this.actuel = actuel;
    }

    public Boolean getRespecte() {
        return respecte;
    }

    public void setRespecte(Boolean respecte) {
        this.respecte = respecte;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
