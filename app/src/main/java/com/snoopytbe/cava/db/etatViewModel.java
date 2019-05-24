package com.snoopytbe.cava.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.snoopytbe.cava.Classes.etat;

import java.util.List;

import io.reactivex.Maybe;

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

    public Maybe<etat> getPrecedentEtat(long paramDate) {
        return mRepository.getPrecedentEtat(paramDate);
    }

    public etat getPrecedentEtatv2(long paramDate) {
        return mRepository.getPrecedentEtatv2(paramDate);
    }
}