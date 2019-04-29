package com.snoopytbe.cava.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface etatDao {

    @Insert
    void insert(etat etat);

    @Update
    void update(etat etat);

    @Query("DELETE FROM etat_table")
    void deleteAll();

    @Query("SELECT * from etat_table order by date DESC")
    LiveData<List<etat>> getAllEtats();

    @Query("SELECT MAX(date) from etat_table")
    long getDernierJour();
}


