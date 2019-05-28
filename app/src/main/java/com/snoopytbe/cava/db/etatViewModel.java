package com.snoopytbe.cava.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.snoopytbe.cava.Classes.etat;

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

    // Permet d'accéder à l'état en cours de visualisation/édition
    private final MutableLiveData<etat> etatActuel = new MutableLiveData<>();
    // Permet d'accéder au moment de la journée de l'humeur en cours de visualisation/édition
    private final MutableLiveData<String> momentActuel = new MutableLiveData<>();
    // Permet d'accéder à la page actuelle du ViewPager des etats
    private int posActuelle = 0;

    public etat getPrecedentEtat(long paramDate) {
        return mRepository.getPrecedentEtat(paramDate);
    }

    public LiveData<etat> getEtatActuel() {
        return etatActuel;
    }

    public void setEtatActuel(etat etat) {
        etatActuel.setValue(etat);
    }

    public LiveData<String> getMomentActuel() {
        return momentActuel;
    }

    public void setMomentActuel(String moment) {
        momentActuel.setValue(moment);
    }

    public int getPosActuelle() {
        return posActuelle;
    }

    public void setPosActuelle(int position) {
        posActuelle = position;
    }


}