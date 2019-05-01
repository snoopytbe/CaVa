package com.snoopytbe.cava.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.Converters.Converters;

import java.util.Calendar;

@Database(entities = {etat.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class etatRoomDatabase extends RoomDatabase {

    private static volatile etatRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new PopulateDbAsync(INSTANCE).execute();
            new UpgradeDbAsync(INSTANCE).execute();
        }


    };


    static etatRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (etatRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            etatRoomDatabase.class, "etat_database")
                            .setJournalMode(JournalMode.TRUNCATE)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract etatDao etatDao();

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final etatDao mDao;

        PopulateDbAsync(etatRoomDatabase db) {
            mDao = db.etatDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();

            Calendar calendar;
            calendar = Calendar.getInstance();
            etat etat = new etat(calendar.getTimeInMillis());
            mDao.insert(etat);
            return null;
        }
    }


    /**
     * Upgrades the database in the background with missing days.
     */
    private static class UpgradeDbAsync extends AsyncTask<Void, Void, Void> {

        private final etatDao mDao;

        UpgradeDbAsync(etatRoomDatabase db) {
            mDao = db.etatDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            // Last day in the database
            etat etatDernierJour = mDao.getEtatDernierJour();
            long dernierJour = mDao.getDernierJour();

            Calendar lastDayDB = Calendar.getInstance();
            lastDayDB.setTimeInMillis(dernierJour);
            lastDayDB.set(Calendar.HOUR, 0);
            lastDayDB.set(Calendar.MINUTE, 0);
            lastDayDB.set(Calendar.SECOND, 0);
            lastDayDB.set(Calendar.MILLISECOND, 0);

            // Today
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            // Ajout des jours manquants
            while (lastDayDB.compareTo(today) < 0) {
                lastDayDB.add(Calendar.DAY_OF_MONTH, 1);
                etat etat = new etat(lastDayDB.getTimeInMillis(), etatDernierJour.getTraitement());
                mDao.insert(etat);
            }

            return null;
        }
    }
}