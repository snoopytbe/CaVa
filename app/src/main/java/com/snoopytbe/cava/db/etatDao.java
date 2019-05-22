package com.snoopytbe.cava.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.snoopytbe.cava.Classes.etat;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface etatDao {

    @Insert
    void insert(etat etat);

    @Update
    void update(etat etat);

    @Delete
    void delete(etat etat);

    @Query("DELETE FROM etat_table")
    void deleteAll();

    @Query("SELECT * from etat_table order by date DESC")
    LiveData<List<etat>> getAllEtats();

    @Query("SELECT MAX(date) from etat_table")
    long getDernierJour();

    @Query("SELECT * from etat_table where date = (SELECT MAX(date) from etat_table)")
    etat getEtatDernierJour();

    @Query("SELECT * from etat_table where date = (SELECT MAX(date) from etat_table WHERE date < :paramDate)")
    Maybe<etat> getPrecedentEtat(long paramDate);

    @Query("SELECT * from etat_table where date = (SELECT MAX(date) from etat_table WHERE date < :paramDate)")
    etat getPrecedentEtatv2(long paramDate);
}


