package com.snoopytbe.cava.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.snoopytbe.cava.Classes.etat;

import java.util.List;

public class etatRepository {

    private etatDao mEtatDao;
    private LiveData<List<etat>> mAllEtats;

    etatRepository(Application application) {
        etatRoomDatabase db = etatRoomDatabase.getDatabase(application);
        mEtatDao = db.etatDao();
        mAllEtats = mEtatDao.getAllEtats();
    }

    LiveData<List<etat>> getAllEtats() {
        return mAllEtats;
    }

    public void insert(etat etat) {
        new insertAsyncTask(mEtatDao).execute(etat);
    }

    public void update(etat etat) {
        new updateAsyncTask(mEtatDao).execute(etat);
    }

    private static class insertAsyncTask extends AsyncTask<etat, Void, Void> {

        private etatDao mAsyncTaskDao;

        insertAsyncTask(etatDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final etat... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<etat, Void, Void> {

        private etatDao mAsyncTaskDao;

        updateAsyncTask(etatDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final etat... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}