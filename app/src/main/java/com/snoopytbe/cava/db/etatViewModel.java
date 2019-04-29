package com.snoopytbe.cava.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class etatViewModel extends AndroidViewModel {

    private etatRepository mRepository;

    private LiveData<List<etat>> mAllEtats;

    public etatViewModel(Application application) {
        super(application);
        mRepository = new etatRepository(application);
        mAllEtats = mRepository.getAllEtats();
    }

    public LiveData<List<etat>> getAllEtats() {
        return mAllEtats;
    }

    public void insert(etat etat) {
        mRepository.insert(etat);
    }

    public void update(etat etat) {
        mRepository.update(etat);
    }
}