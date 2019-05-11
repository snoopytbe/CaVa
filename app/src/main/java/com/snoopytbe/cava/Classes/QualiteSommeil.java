package com.snoopytbe.cava.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;

public class QualiteSommeil implements Serializable {
    @ColumnInfo(name = "ratingSommeil")
    private float ratingSommeil;

    @Embedded(prefix = "sommeil_")
    private HeuresMinutes heuresSommeil;

    @Embedded(prefix = "coucher_")
    private HeuresMinutes heureCoucher;

    @Embedded(prefix = "lever_")
    private HeuresMinutes heureLever;

    @Embedded(prefix = "insomnie_")
    private HeuresMinutes heuresInsomnie;

    @Embedded(prefix = "sieste_")
    private HeuresMinutes heuresSieste;

    @ColumnInfo(name = "commentaireSommeil")
    private String commentaire;

    public QualiteSommeil(float ratingSommeil, HeuresMinutes heureCoucher, HeuresMinutes heureLever, HeuresMinutes heuresInsomnie, HeuresMinutes heuresSieste, String commentaire) {
        this.ratingSommeil = ratingSommeil;
        this.heureCoucher = heureCoucher;
        this.heureLever = heureLever;
        this.heuresInsomnie = heuresInsomnie;
        this.heuresSieste = heuresSieste;
        this.commentaire = commentaire;
        this.heuresSommeil = new HeuresMinutes();
        calculeHeuresSommeil();
    }

    @Ignore
    public QualiteSommeil() {
        this.ratingSommeil = 5;
        this.heureCoucher = new HeuresMinutes("23h");
        this.heureLever = new HeuresMinutes("7h");
        this.heuresInsomnie = new HeuresMinutes("0h");
        this.heuresSieste = new HeuresMinutes("0h");
        this.commentaire = "";
        this.heuresSommeil = new HeuresMinutes();
        calculeHeuresSommeil();
    }

    public float getRatingSommeil() {
        return ratingSommeil;
    }

    public void setRatingSommeil(float ratingSommeil) {
        this.ratingSommeil = ratingSommeil;
    }

    private void calculeHeuresSommeil() {
        this.heuresSommeil.setMinutes(this.heureLever.getMinutes());
        this.heuresSommeil.setHeures(this.heureLever.getHeures());
        this.heuresSommeil.sub(this.heureCoucher);
        this.heuresSommeil.sub(this.heuresInsomnie);
    }

    public HeuresMinutes getHeuresSommeil() {
        return heuresSommeil;
    }

    public void setHeuresSommeil(HeuresMinutes heuresSommeil) {
        this.heuresSommeil = heuresSommeil;
    }

    public HeuresMinutes getHeureCoucher() {
        return heureCoucher;
    }

    public void setHeureCoucher(HeuresMinutes heureCoucher) {
        this.heureCoucher = heureCoucher;
        calculeHeuresSommeil();
    }

    public HeuresMinutes getHeureLever() {
        return heureLever;
    }

    public void setHeureLever(HeuresMinutes heureLever) {
        this.heureLever = heureLever;
        calculeHeuresSommeil();
    }

    public HeuresMinutes getHeuresInsomnie() {
        return heuresInsomnie;
    }

    public void setHeuresInsomnie(HeuresMinutes heuresInsomnie) {
        this.heuresInsomnie = heuresInsomnie;
        calculeHeuresSommeil();
    }

    public HeuresMinutes getHeuresSieste() {
        return heuresSieste;
    }

    public void setHeuresSieste(HeuresMinutes heuresSieste) {
        this.heuresSieste = heuresSieste;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
