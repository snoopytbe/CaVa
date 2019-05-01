package com.snoopytbe.cava.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

@Entity(tableName = "etat_table")
public class etat implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private long date;

    @Embedded(prefix = "am_")
    private Humeur humeurMatin;

    @Embedded(prefix = "pm_")
    private Humeur humeurApresMidi;

    @Embedded(prefix = "eve_")
    private Humeur humeurSoir;

    @Embedded
    private QualiteSommeil qualiteSommeil;

    @Embedded
    private Traitement traitement;

    public etat(long date, Traitement traitement) {
        this.date = date;
        this.traitement = new Traitement(traitement.getActuel());
        this.qualiteSommeil = new QualiteSommeil();
        this.humeurMatin = new Humeur();
        this.humeurApresMidi = new Humeur();
        this.humeurSoir = new Humeur();
    }

    @Ignore
    public etat(long date) {
        this.date = date;
        this.qualiteSommeil = new QualiteSommeil();
        this.humeurMatin = new Humeur();
        this.humeurApresMidi = new Humeur();
        this.humeurSoir = new Humeur();
        this.traitement = new Traitement();
    }

    public QualiteSommeil getQualiteSommeil() {
        return qualiteSommeil;
    }

    public void setQualiteSommeil(QualiteSommeil qualiteSommeil) {
        this.qualiteSommeil = qualiteSommeil;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String DateLisible() {
        String resultat;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.date);
        Locale locale = Locale.getDefault();
        resultat = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
        resultat += " " + calendar.get(Calendar.DAY_OF_MONTH) + " ";
        resultat += calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
        resultat += " " + calendar.get(Calendar.YEAR);
        return resultat;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Humeur getHumeurMatin() {
        return humeurMatin;
    }

    public void setHumeurMatin(Humeur humeurMatin) {
        this.humeurMatin = humeurMatin;
    }

    public Humeur getHumeurApresMidi() {
        return humeurApresMidi;
    }

    public void setHumeurApresMidi(Humeur humeurApresMidi) {
        this.humeurApresMidi = humeurApresMidi;
    }

    public Humeur getHumeurSoir() {
        return humeurSoir;
    }

    public void setHumeurSoir(Humeur humeurSoir) {
        this.humeurSoir = humeurSoir;
    }

    public Traitement getTraitement() {
        return traitement;
    }

    public void setTraitement(Traitement traitement) {
        this.traitement = traitement;
    }
}
