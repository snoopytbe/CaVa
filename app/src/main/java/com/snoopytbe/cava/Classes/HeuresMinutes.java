package com.snoopytbe.cava.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;

public class HeuresMinutes implements Serializable {
    @ColumnInfo(name = "heures")
    private int heures;

    @ColumnInfo(name = "minutes")
    private int minutes;

    public HeuresMinutes(int heures, int minutes) {
        this.heures = heures;
        this.minutes = minutes;
        Corrige();
    }

    @Ignore
    public HeuresMinutes() {
        this.heures = 12;
        this.minutes = 0;
    }

    @Ignore
    public HeuresMinutes(String horaire) {
        horaire = horaire.toLowerCase();
        int posH = horaire.indexOf("h");
        this.heures = Integer.valueOf(horaire.substring(0, posH));
        if (posH + 1 >= horaire.length()) {
            this.minutes = 0;
        } else {
            this.minutes = Integer.valueOf(horaire.substring(posH + 1));
        }
        Corrige();
    }

    public String Lisible() {

        String resultat;
        try {
            if (this.heures < 10) {
                resultat = "0" + this.heures;
            } else {
                resultat = "" + this.heures;
            }
            resultat += "h";
            if (this.minutes < 10) {
                resultat += "0" + this.minutes;
            } else {
                resultat += this.minutes;
            }
        } catch (Exception e) {
            this.heures = 12;
            this.minutes = 0;
            resultat = "12h";
        }
        return resultat;
    }

    public int getIntQuinzaine() {
        return this.heures * 4 + this.minutes / 15;
    }

    public void setIntQuinzaine(int heuresminutes) {
        this.heures = (int) Math.floor((double) heuresminutes / 4);
        //Log.e("rien", "setIntQuinzaine: heures : "+ this.heures );
        this.minutes = (heuresminutes - 4 * this.heures) * 15;
        //Log.e("rien", "setIntQuinzaine: minutes : "+ this.minutes );
    }

    private void Corrige() {
        while (this.minutes > 59) {
            this.heures += 1;
            this.minutes -= 60;
        }
        while (this.minutes < 0) {
            this.heures -= 1;
            this.minutes += 60;
        }
        while (this.heures > 23) {
            this.heures -= 24;
        }
        while (this.heures < 0) {
            this.heures += 24;
        }
    }

    public void add(HeuresMinutes timeAdded) {
        this.minutes += timeAdded.getMinutes();
        this.heures += timeAdded.getHeures();
        Corrige();
    }

    public void sub(HeuresMinutes timeSubstracted) {
        this.minutes -= timeSubstracted.getMinutes();
        this.heures -= timeSubstracted.getHeures();
        Corrige();
    }

    public int getHeures() {
        return heures;
    }

    public void setHeures(int heures) {
        this.heures = heures;
        Corrige();
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        Corrige();
    }
}
